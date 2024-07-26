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
import com.fiap.techchallenge.drivers.postgresql.CustomerPostgreDriver;
import com.fiap.techchallenge.drivers.postgresql.OrderPostgreDriver;
import com.fiap.techchallenge.drivers.postgresql.PaymentPostgreDriver;
import com.fiap.techchallenge.drivers.postgresql.ProductPostgreDriver;
import com.fiap.techchallenge.gateway.CheckoutGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Modules {

  // ========================= CUSTOMERS =========================

  @Bean
  public CreateCustomerUseCase createCustomerUseCase(CustomerPostgreDriver repository) {
    return new CreateCustomerUseCase(repository);
  }

  @Bean
  public GetCustomerByCPFUseCase getCustomerByCPFUseCase(CustomerPostgreDriver repository) {
    return new GetCustomerByCPFUseCase(repository);
  }

  @Bean
  public ListAllCustomerUseCase listAllCustomerUseCase(CustomerPostgreDriver repository) {
    return new ListAllCustomerUseCase(repository);
  }

  @Bean
  public UpdateCustomerUseCase updateCustomerUseCase(CustomerPostgreDriver repository) {
    return new UpdateCustomerUseCase(repository);
  }

  // ========================= PRODUCTS =========================

  @Bean
  public CreateProductUseCase createProductUseCase(ProductPostgreDriver repository) {
    return new CreateProductUseCase(repository);
  }

  @Bean
  public DeleteProductUseCase deleteProductUseCase(ProductPostgreDriver repository) {
    return new DeleteProductUseCase(repository);
  }

  @Bean
  public ListAllProductsUseCase listAllProductsUseCase(ProductPostgreDriver repository) {
    return new ListAllProductsUseCase(repository);
  }

  // ========================= ORDERS =========================

  @Bean
  public GetOrderUseCase getOrderUseCase(OrderPostgreDriver orderPostgreDriver) {
    return new GetOrderUseCase(orderPostgreDriver, new CalculateOrderWaitTimeUseCase());
  }

  @Bean
  public ListOrdersWithFiltersUseCase listOrdersWithFiltersUseCase(OrderPostgreDriver orderPostgreDriver) {
    return new ListOrdersWithFiltersUseCase(orderPostgreDriver, new CalculateOrderWaitTimeUseCase());
  }

  @Bean
  public UpdateOrderStatusUseCase updateOrderStatusUseCase(OrderPostgreDriver orderPostgreDriver) {
    return new UpdateOrderStatusUseCase(orderPostgreDriver);
  }

  @Bean
  public CheckoutOrderUseCase checkoutOrderUseCase(OrderPostgreDriver orderPostgreDriver, CustomerPostgreDriver customerPostgreDriver, PaymentPostgreDriver paymentPostgreDriver, CheckoutGateway gateway) {
    return new CheckoutOrderUseCase(
            orderPostgreDriver,
            customerPostgreDriver,
            paymentPostgreDriver,
        gateway
    );
  }

  @Bean
  public GetOrderHistoryUseCase getOrderHistoryUseCase(OrderPostgreDriver orderPostgreDriver) {
    return new GetOrderHistoryUseCase(orderPostgreDriver);
  }

  @Bean
  public CreateOrderUseCase createOrderUseCase(OrderPostgreDriver orderPostgreDriver, ProductPostgreDriver productPostgreDriver) {
    return new CreateOrderUseCase(orderPostgreDriver, productPostgreDriver);
  }

  // ========================= PAYMENTS =========================

  @Bean
  public CreatePaymentUseCase createPaymentUseCase(PaymentPostgreDriver paymentPostgreDriver) {
    return new CreatePaymentUseCase(paymentPostgreDriver);
  }

  @Bean
  public GetPaymentUseCase getPaymentUseCase(PaymentPostgreDriver paymentPostgreDriver) {
    return new GetPaymentUseCase(paymentPostgreDriver);
  }

  @Bean
  public UpdatePaymentUseCase updatePaymentUseCase(PaymentPostgreDriver paymentPostgreDriver) {
    return new UpdatePaymentUseCase(paymentPostgreDriver);
  }

  @Bean
  public HandleExternalPaymentUseCase handleExternalPaymentUseCase(PaymentPostgreDriver paymentPostgreDriver, OrderPostgreDriver orderPostgreDriver) {
    return new HandleExternalPaymentUseCase(
            paymentPostgreDriver,
        new GetOrderUseCase(orderPostgreDriver, new CalculateOrderWaitTimeUseCase()),
        new UpdateOrderStatusUseCase(orderPostgreDriver)
    );
  }

}
