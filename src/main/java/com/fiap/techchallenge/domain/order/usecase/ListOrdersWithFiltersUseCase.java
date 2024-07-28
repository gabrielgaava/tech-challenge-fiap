package com.fiap.techchallenge.domain.order.usecase;

import com.fiap.techchallenge.domain.order.*;
import com.fiap.techchallenge.gateway.OrderGateway;

import java.util.ArrayList;
import java.util.List;


public class ListOrdersWithFiltersUseCase  {

  private final OrderGateway Gateway;
  private final CalculateOrderWaitTimeUseCase calculateOrderWaitTimeUseCase;

  public ListOrdersWithFiltersUseCase(OrderGateway Gateway, CalculateOrderWaitTimeUseCase useCase) {
    this.Gateway = Gateway;
    this.calculateOrderWaitTimeUseCase = useCase;
  }

  public List<Order> execute(OrderFilters filters, OrderGateway orderGateway)
  {
    List<Order> orders;

    if(filters.hasNoParameters()) {
      OrderFilters defaultFilters = new OrderFilters(null, OrderSortFields.CREATED_AT, SortDirection.ASC);
      orders = Gateway.getAll(defaultFilters);
      orders = this.getDefaultListOrders(orders);
    }

    else {
      orders = Gateway.getAll(filters);
    }

    return this.calculateOrdersWaitTime(orders);
  }

  /**
   * Gets the orders using default hierarchy:
   * 1. READY_TO_DELIVERY > IN_PREPARATION > RECEIVED
   * 2. Older orders first
   * 3. Finished orders should NOT be present
   * @param orders the list of orders in any ordination
   * @return: the filtered list of orders
   */
  private List<Order> getDefaultListOrders(List<Order> orders) {

    // List with only READY_TO_DELIVERY, IN_PREPARATION and RECEIVED status
    List<Order> cleanList = orders.stream().filter(i -> i.getStatus().isDefaultListStatus()).toList();

    // Separating lists by status
    List<Order> ordersReady = filterListByStatus(cleanList, OrderStatus.READY_TO_DELIVERY);
    List<Order> ordersInPreparation = filterListByStatus(cleanList, OrderStatus.IN_PREPARATION);
    List<Order> ordersReceived = filterListByStatus(cleanList, OrderStatus.RECEIVED);

    //Merging all list in
    List<Order> finalList = new ArrayList<>();
    finalList.addAll(ordersReady);
    finalList.addAll(ordersInPreparation);
    finalList.addAll(ordersReceived);

    return finalList;
  }

  /**
   * Filter a list of orders by the orders status
   * @param orders the list of orders that will be filtered
   * @param status the status to be filtered
   * @return the list of orders filtered by the status
   */
  private List<Order> filterListByStatus(List<Order> orders, OrderStatus status) {
    return orders.stream().filter(i -> i.getStatus().equals(status)).toList();
  }

  /**
   * Calculate the time in seconds for each order in the list
   * @param orders the list of orders that will have the time calculated
   * @return the list of orders with time calculated in seconds
   */
  private List<Order> calculateOrdersWaitTime(List<Order> orders) {

    if(!orders.isEmpty()) {
      for (Order order : orders) {
        var waitTime = calculateOrderWaitTimeUseCase.execute(order);
        order.setWaitingTimeInSeconds(waitTime);
      }
    }

    return orders;
  }

}
