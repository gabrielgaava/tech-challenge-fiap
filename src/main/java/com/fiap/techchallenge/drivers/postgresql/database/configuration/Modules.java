package com.fiap.techchallenge.drivers.postgresql.database.configuration;

import com.fiap.techchallenge.domain.customer.usecase.impl.CreateCustomerUseCase;
import com.fiap.techchallenge.domain.customer.usecase.impl.GetCustomerByCPFUseCase;
import com.fiap.techchallenge.domain.customer.usecase.impl.ListAllCustomerUseCase;
import com.fiap.techchallenge.domain.customer.usecase.impl.UpdateCustomerUseCase;
import com.fiap.techchallenge.domain.order.gateway.ICheckoutGateway;
import com.fiap.techchallenge.domain.order.usecase.*;
import com.fiap.techchallenge.domain.order.usecase.impl.*;
import com.fiap.techchallenge.domain.payment.usecase.ICreatePaymentUseCase;
import com.fiap.techchallenge.domain.payment.usecase.IGetPaymentUseCase;
import com.fiap.techchallenge.domain.payment.usecase.IHandleExternalPaymentUseCase;
import com.fiap.techchallenge.domain.payment.usecase.IUpdatePaymentUseCase;
import com.fiap.techchallenge.domain.payment.usecase.impl.CreatePaymentUseCase;
import com.fiap.techchallenge.domain.payment.usecase.impl.GetPaymentUseCase;
import com.fiap.techchallenge.domain.payment.usecase.impl.HandleExternalPaymentUseCase;
import com.fiap.techchallenge.domain.payment.usecase.impl.UpdatePaymentUseCase;
import com.fiap.techchallenge.domain.product.usecase.ICreateProductUseCase;
import com.fiap.techchallenge.domain.product.usecase.IDeleteProductUseCase;
import com.fiap.techchallenge.domain.product.usecase.IListAllProductsUseCase;
import com.fiap.techchallenge.domain.product.usecase.impl.CreateProductUseCase;
import com.fiap.techchallenge.domain.product.usecase.impl.DeleteProductUseCase;
import com.fiap.techchallenge.domain.product.usecase.impl.ListAllProductsUseCase;
import com.fiap.techchallenge.drivers.postgresql.CustomerRepository;
import com.fiap.techchallenge.drivers.postgresql.OderRepository;
import com.fiap.techchallenge.drivers.postgresql.PaymentRepository;
import com.fiap.techchallenge.drivers.postgresql.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Modules {

  // ========================= CUSTOMERS =========================

  @Bean
  public CreateCustomerUseCase createCustomerUseCase(CustomerRepository repository) {
    return new CreateCustomerUseCase(repository);
  }

  @Bean
  public GetCustomerByCPFUseCase getCustomerByCPFUseCase(CustomerRepository repository) {
    return new GetCustomerByCPFUseCase(repository);
  }

  @Bean
  public ListAllCustomerUseCase listAllCustomerUseCase(CustomerRepository repository) {
    return new ListAllCustomerUseCase(repository);
  }

  @Bean
  public UpdateCustomerUseCase updateCustomerUseCase(CustomerRepository repository) {
    return new UpdateCustomerUseCase(repository);
  }

  // ========================= PRODUCTS =========================

  @Bean
  public ICreateProductUseCase createProductUseCase(ProductRepository repository) {
    return new CreateProductUseCase(repository);
  }

  @Bean
  public IDeleteProductUseCase deleteProductUseCase(ProductRepository repository) {
    return new DeleteProductUseCase(repository);
  }

  @Bean
  public IListAllProductsUseCase listAllProductsUseCase(ProductRepository repository) {
    return new ListAllProductsUseCase(repository);
  }

  // ========================= ORDERS =========================

  @Bean
  public IGetOrderUseCase getOrderUseCase(OderRepository oderRepository) {
    return new GetOrderUseCase(oderRepository, new CalculateOrderWaitTimeUseCase());
  }

  @Bean
  public IListOrdersWithFiltersUseCase listOrdersWithFiltersUseCase(OderRepository oderRepository) {
    return new ListOrdersWithFiltersUseCase(oderRepository, new CalculateOrderWaitTimeUseCase());
  }

  @Bean
  public IUpdateOrderStatusUseCase updateOrderStatusUseCase(OderRepository oderRepository) {
    return new UpdateOrderStatusUseCase(oderRepository);
  }

  @Bean
  public ICheckoutOrderUseCase checkoutOrderUseCase(OderRepository oderRepository, CustomerRepository customerRepository, PaymentRepository paymentRepository, ICheckoutGateway gateway) {
    return new CheckoutOrderUseCase(
        oderRepository,
        customerRepository,
        paymentRepository,
        gateway
    );
  }

  @Bean
  public IGetOrderHistoryUseCase getOrderHistoryUseCase(OderRepository oderRepository) {
    return new GetOrderHistoryUseCase(oderRepository);
  }

  @Bean
  public ICreateOrderUseCase createOrderUseCase(OderRepository oderRepository, ProductRepository productRepository) {
    return new CreateOrderUseCase(oderRepository, productRepository);
  }

  // ========================= PAYMENTS =========================

  @Bean
  public ICreatePaymentUseCase createPaymentUseCase(PaymentRepository paymentRepository) {
    return new CreatePaymentUseCase(paymentRepository);
  }

  @Bean
  public IGetPaymentUseCase getPaymentUseCase(PaymentRepository paymentRepository) {
    return new GetPaymentUseCase(paymentRepository);
  }

  @Bean
  public IUpdatePaymentUseCase updatePaymentUseCase(PaymentRepository paymentRepository) {
    return new UpdatePaymentUseCase(paymentRepository);
  }

  @Bean
  public IHandleExternalPaymentUseCase handleExternalPaymentUseCase(PaymentRepository paymentRepository, OderRepository oderRepository) {
    return new HandleExternalPaymentUseCase(
        paymentRepository,
        new GetOrderUseCase(oderRepository, new CalculateOrderWaitTimeUseCase()),
        new UpdateOrderStatusUseCase(oderRepository)
    );
  }

}
