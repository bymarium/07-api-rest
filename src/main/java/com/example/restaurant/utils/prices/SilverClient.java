package com.example.restaurant.utils.prices;

import com.example.restaurant.constants.Type;
import com.example.restaurant.models.Order;

public class SilverClient extends Handler{
	@Override
	public void handlerRequest(Order order) {
		if (order.getClient().getUserType().equals(Type.SILVER.getName())) {
			order.setTotalPrice(order.getTotalPrice() * order.getClient().getAdjust());
		} else {
			nextHandler.handlerRequest(order);
		}
	}
}