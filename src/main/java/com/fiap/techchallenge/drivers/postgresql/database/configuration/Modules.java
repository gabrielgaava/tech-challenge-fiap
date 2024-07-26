package com.fiap.techchallenge.drivers.postgresql.database.configuration;

import com.fiap.techchallenge.domain.customer.usecase.CreateCustomerUseCase;
import com.fiap.techchallenge.domain.customer.usecase.GetCustomerByCPFUseCase;
import com.fiap.techchallenge.domain.customer.usecase.ListAllCustomerUseCase;
import com.fiap.techchallenge.domain.customer.usecase.UpdateCustomerUseCase;
import com.fiap.techchallenge.domain.order.usecase.*;
import com.fiap.techchallenge.domain.payment.usecase.CreatePaymentUseCase;
import com.fiap.techchallenge.domain.payment.usecase.GetPaymentUseCase;
import com.fiap.techchallenge.domain.payment.usecase.HandleExternalPaymentUseCase;
import com.fiap.techchallenge.domain.payment.usecase.UpdatePaymentUseCase;
import com.fiap.techchallenge.domain.product.usecase.CreateProductUseCase;
import com.fiap.techchallenge.domain.product.usecase.DeleteProductUseCase;
import com.fiap.techchallenge.domain.product.usecase.ListAllProductsUseCase;
import com.fiap.techchallenge.drivers.postgresql.CustomerRepository;
import com.fiap.techchallenge.drivers.postgresql.OderRepository;
import com.fiap.techchallenge.drivers.postgresql.PaymentRepository;
import com.fiap.techchallenge.drivers.postgresql.ProductRepository;
import com.fiap.techchallenge.gateway.ICheckoutGateway;
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
  public CreateProductUseCase createProductUseCase(ProductRepository repository) {
    return new CreateProductUseCase(repository);
  }

  @Bean
  public DeleteProductUseCase deleteProductUseCase(ProductRepository repository) {
    return new DeleteProductUseCase(repository);
  }

  @Bean
  public ListAllProductsUseCase listAllProductsUseCase(ProductRepository repository) {
    return new ListAllProductsUseCase(repository);
  }

  // ========================= ORDERS =========================

  @Bean
  public GetOrderUseCase getOrderUseCase(OderRepository oderRepository) {
    return new GetOrderUseCase(oderRepository, new CalculateOrderWaitTimeUseCase());
  }

  @Bean
  public ListOrdersWithFiltersUseCase listOrdersWithFiltersUseCase(OderRepository oderRepository) {
    return new ListOrdersWithFiltersUseCase(oderRepository, new CalculateOrderWaitTimeUseCase());
  }

  @Bean
  public UpdateOrderStatusUseCase updateOrderStatusUseCase(OderRepository oderRepository) {
    return new UpdateOrderStatusUseCase(oderRepository);
  }

  @Bean
  public CheckoutOrderUseCase checkoutOrderUseCase(OderRepository oderRepository, CustomerRepository customerRepository, PaymentRepository paymentRepository, ICheckoutGateway gateway) {
    return new CheckoutOrderUseCase(
        oderRepository,
        customerRepository,
        paymentRepository,
        gateway
    );
  }

  @Bean
  public GetOrderHistoryUseCase getOrderHistoryUseCase(OderRepository oderRepository) {
    return new GetOrderHistoryUseCase(oderRepository);
  }

  @Bean
  public CreateOrderUseCase createOrderUseCase(OderRepository oderRepository, ProductRepository productRepository) {
    return new CreateOrderUseCase(oderRepository, productRepository);
  }

  // ========================= PAYMENTS =========================

  @Bean
  public CreatePaymentUseCase createPaymentUseCase(PaymentRepository paymentRepository) {
    return new CreatePaymentUseCase(paymentRepository);
  }

  @Bean
  public GetPaymentUseCase getPaymentUseCase(PaymentRepository paymentRepository) {
    return new GetPaymentUseCase(paymentRepository);
  }

  @Bean
  public UpdatePaymentUseCase updatePaymentUseCase(PaymentRepository paymentRepository) {
    return new UpdatePaymentUseCase(paymentRepository);
  }

  @Bean
  public HandleExternalPaymentUseCase handleExternalPaymentUseCase(PaymentRepository paymentRepository, OderRepository oderRepository) {
    return new HandleExternalPaymentUseCase(
        paymentRepository,
        new GetOrderUseCase(oderRepository, new CalculateOrderWaitTimeUseCase()),
        new UpdateOrderStatusUseCase(oderRepository)
    );
  }

}
