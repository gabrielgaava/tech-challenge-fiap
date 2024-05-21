package com.fiap.techchallenge.domain.service;

import com.fiap.techchallenge.adapters.in.rest.dto.CreateOrderDTO;
import com.fiap.techchallenge.adapters.in.rest.dto.OrderProductDTO;
import com.fiap.techchallenge.adapters.out.rest.exception.PaymentErrorException;
import com.fiap.techchallenge.adapters.out.rest.service.MercadoPagoService;
import com.fiap.techchallenge.domain.entity.*;
import com.fiap.techchallenge.domain.enums.OrderStatus;
import com.fiap.techchallenge.domain.exception.EntityNotFoundException;
import com.fiap.techchallenge.domain.exception.MercadoPagoUnavailableException;
import com.fiap.techchallenge.domain.exception.OrderAlreadyWithStatusException;
import com.fiap.techchallenge.domain.exception.OrderNotReadyException;
import com.fiap.techchallenge.domain.repository.IOrderRepository;
import com.fiap.techchallenge.domain.repository.IPaymentRepository;
import com.fiap.techchallenge.domain.repository.IProductRepository;
import com.fiap.techchallenge.domain.usecase.IOrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.fiap.techchallenge.domain.enums.OrderStatus.PAID;
import static com.fiap.techchallenge.domain.enums.OrderStatus.RECEIVED;
import static java.math.RoundingMode.HALF_EVEN;

@Service
public class OrderService implements IOrderUseCase {

    @Autowired
    @Qualifier("PGOrderRepository")
    IOrderRepository IOrderRepository;

    @Autowired
    @Qualifier("PGProductRepository")
    IProductRepository IProductRepository;

    @Autowired
    @Qualifier("PGPaymentRepository")
    IPaymentRepository IPaymentRepository;

    @Autowired
    MercadoPagoService mercadoPagoService;

    /**
     * Gets ALL orders stored at the database
     * @param filters: database filter queries
     * @return: the filtered list of orders
     */
    @Override
    public List<Order> getOrders(OrderFilters filters) {
        var orders = IOrderRepository.getAll(filters);

        for(Order order : orders) {
            var waitTime = calculateWaitTime(order);
            order.setWaitingTimeInSeconds(waitTime);
        }

        return orders;
    }

    /**
     * Get all the data from an order, with products and history
     * @param id: the oder ID
     * @return: The order found in database
     * @throws EntityNotFoundException
     */
    @Override
    public Order getOrder(UUID id) throws EntityNotFoundException {
        var order = IOrderRepository.getByIdWithProducts(id);

        if(order == null) throw new EntityNotFoundException("Order", id);

        var history = IOrderRepository.getOrderHistoryByOrderId(id);

        order.setWaitingTimeInSeconds(calculateWaitTime(order));
        order.setHistory(history);

        return order;
    }

    /**
     * Create a new order with RECEIVED status
     * @param dto The body request of api
     * @return the created order object or null, in case of error
     */
    @Override
    public Order createOrder(CreateOrderDTO dto) {

        // The order must have at least one product to be created
        if(dto.getProducts() == null || dto.getProducts().isEmpty())
            return null;

        UUID costumerId = dto.getCostumerId() != null
                ? UUID.fromString(dto.getCostumerId())
                : null;

        List<ProductAndQuantity> orderProducts = new ArrayList<>();

        // Retrieve all Products from database from ID's list
        for(OrderProductDTO item : dto.getProducts()) {
            var product = IProductRepository.getById(UUID.fromString(item.getId()));
            if(product != null) {
                orderProducts.add(new ProductAndQuantity(product, item.getQuantity()));
            }
        }

        // Sum all products and multiply its quantities to generate the order amount
        BigDecimal orderAmount = orderProducts
                .stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, HALF_EVEN);

        var order = Order.builder()
            .id(UUID.randomUUID())
            .costumerId(costumerId)
            .amount(orderAmount)
            .status(RECEIVED)
            .createdAt(LocalDateTime.now())
            .products(orderProducts)
            .build();

        if(IOrderRepository.create(order) == 1)
            return order;


        else return null;
    }

    /**
     * Gets all the order's history, with status changes registers
     * @param id The Order's ID
     * @return all order's history with status update
     * @throws EntityNotFoundException
     */
    @Override
    public List<OrderHistory> getOrderHistory(UUID id) throws EntityNotFoundException {
        var history = IOrderRepository.getOrderHistoryByOrderId(id);
        if(history == null) throw new EntityNotFoundException("Order", id);

        return history;
    }

    /**
     * Updates the order's status
     * @param id The order ID
     * @param status The new order Status
     * @return true if updated successfully, false otherwise
     * @throws OrderAlreadyWithStatusException
     */
    @Override
    public boolean updateOrderStatus(UUID id, OrderStatus status) throws OrderAlreadyWithStatusException {
        var order = IOrderRepository.getById(id);

        if(order.getStatus().equals(status))
            throw new OrderAlreadyWithStatusException(id, status);

        return IOrderRepository.updateStatus(id, status, order.getStatus()) == 2;
    }

    /**
     * Make payment with fake MercadoPago Checkout
     * @param id Order ID to be paid
     * @return Payment details
     * @throws EntityNotFoundException
     * @throws OrderNotReadyException
     * @throws MercadoPagoUnavailableException
     */
    @Override
    public Payment payOrder(UUID id) throws EntityNotFoundException, OrderNotReadyException, MercadoPagoUnavailableException {
        var order = IOrderRepository.getById(id);

        if (order == null || order.getStatus() == null) {
            throw new EntityNotFoundException("Order", id);
        }

        if (order.getStatus() != RECEIVED){
            throw new OrderNotReadyException();
        }

        try {
            Payment payment = mercadoPagoService.pixPayment(order.getId().toString(), order.getAmount());
            IOrderRepository.updateStatus(id, PAID, order.getStatus());
            IPaymentRepository.create(payment);
            return payment;
        }

        catch (PaymentErrorException e) {
            throw new MercadoPagoUnavailableException();
        }

    }

    /**
     * Calculate the total of time in seconds that the order was created until now
     * @param order: The Oder that will have the time waiting calculated
     * @return the total time of wait in seconds
     */
    private long calculateWaitTime(Order order) {
        if(order.getStatus().equals(OrderStatus.FINISHED)) return 0;
        return Duration.between(order.getCreatedAt(), LocalDateTime.now()).toSeconds();
    }
}
