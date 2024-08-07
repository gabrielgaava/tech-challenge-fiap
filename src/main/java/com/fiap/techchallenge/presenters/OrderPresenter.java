package com.fiap.techchallenge.presenters;

import com.fiap.techchallenge.domain.order.Order;
import com.fiap.techchallenge.domain.product.Product;
import com.fiap.techchallenge.domain.product.ProductAndQuantity;
import com.fiap.techchallenge.handlers.rest.dto.CreateOrderDTO;
import com.fiap.techchallenge.handlers.rest.dto.OrderProductDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class OrderPresenter {

  public static Order toDomain(final CreateOrderDTO dto) {

    List<ProductAndQuantity> orderProducts = new ArrayList<>();

    if(dto.getProducts() == null)
      throw new IllegalArgumentException("Products cannot be null");

    // Convert product list to Domain
    for(OrderProductDTO item : dto.getProducts()) {
      var tempProduct = new ProductAndQuantity();

      if(!StringUtils.isEmpty(item.getId())) {
        tempProduct.setProduct(new Product(UUID.fromString(item.getId())));
        tempProduct.setQuantity(item.getQuantity());
        orderProducts.add(tempProduct);
      }

      else {
        throw new IllegalArgumentException("Product ID must not be empty or null");
      }

    }

    Order order = new Order();

    if(!StringUtils.isEmpty(dto.getCustomerId())){
      order.setCustomerId(UUID.fromString(dto.getCustomerId()));
    }

    order.setProducts(orderProducts);
    return order;
  }

}
