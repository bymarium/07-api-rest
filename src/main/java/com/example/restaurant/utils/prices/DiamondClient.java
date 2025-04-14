package com.example.restaurant.utils.prices;

import com.example.restaurant.constants.Type;
import com.example.restaurant.models.Order;

public class DiamondClient extends Handler{
  @Override
  public void handlerRequest(Order order) {
    if (order.getClient().getUserType().equals(Type.DIAMOND.getName())) {
      order.setTotalPrice(order.getTotalPrice() * order.getClient().getAdjust());
    }
  }
}