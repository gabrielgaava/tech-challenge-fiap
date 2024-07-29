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
import com.fiap.techchallenge.drivers.api.MercadoPagoDriver;
import com.fiap.techchallenge.drivers.postgresql.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Modules {

  // ========================= CUSTOMERS =========================

  @Bean
  public CreateCustomerUseCase createCustomerUseCase(CustomerPostgreDriver Gateway) {
    return new CreateCustomerUseCase(Gateway);
  }

  @Bean
  public GetCustomerByCPFUseCase getCustomerByCPFUseCase(CustomerPostgreDriver Gateway) {
    return new GetCustomerByCPFUseCase(Gateway);
  }

  @Bean
  public ListAllCustomerUseCase listAllCustomerUseCase(CustomerPostgreDriver Gateway) {
    return new ListAllCustomerUseCase(Gateway);
  }

  @Bean
  public UpdateCustomerUseCase updateCustomerUseCase(CustomerPostgreDriver Gateway) {
    return new UpdateCustomerUseCase(Gateway);
  }

  // ========================= PRODUCTS =========================

  @Bean
  public CreateProductUseCase createProductUseCase(ProductPostgreDriver Gateway) {
    return new CreateProductUseCase(Gateway);
  }

  @Bean
  public DeleteProductUseCase deleteProductUseCase(ProductPostgreDriver Gateway) {
    return new DeleteProductUseCase(Gateway);
  }

  @Bean
  public ListAllProductsUseCase listAllProductsUseCase(ProductPostgreDriver Gateway) {
    return new ListAllProductsUseCase(Gateway);
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
  public CheckoutOrderUseCase checkoutOrderUseCase(OrderPostgreDriver orderPostgreDriver,
                                                   CustomerPostgreDriver customerPostgreDriver,
                                                   PaymentPostgreDriver paymentPostgreDriver,
                                                   MercadoPagoDriver mercadoPagoDriver) {
    return new CheckoutOrderUseCase(
            orderPostgreDriver,
            customerPostgreDriver,
            paymentPostgreDriver,
            mercadoPagoDriver
    );
  }

  @Bean
  public GetOrderHistoryUseCase getOrderHistoryUseCase(OrderPostgreDriver orderPostgreDriver) {
    return new GetOrderHistoryUseCase(orderPostgreDriver);
  }

  @Bean
  public CreateOrderUseCase createOrderUseCase(OrderPostgreDriver orderPostgreDriver, ProductPostgreDriver productPostgreDriver, CustomerPostgreDriver customerPostgreDriver) {
    return new CreateOrderUseCase(orderPostgreDriver, productPostgreDriver, customerPostgreDriver);
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
