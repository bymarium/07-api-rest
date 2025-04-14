package com.example.restaurant.utils.prices;

import com.example.restaurant.constants.Type;
import com.example.restaurant.models.Order;

public class BronzeClient extends Handler{
	@Override
	public void handlerRequest(Order order) {
		if(nextHandler != null && !order.getClient().getUserType().equals(Type.BRONZE.getName())) {
			nextHandler.handlerRequest(order);
		}
	}
}