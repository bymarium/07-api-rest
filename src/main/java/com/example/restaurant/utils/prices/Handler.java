package com.example.restaurant.utils.prices;

import com.example.restaurant.models.Order;

public abstract class Handler {
	protected Handler nextHandler;

	public void setNextHandler(Handler nextHandler) {
		this.nextHandler = nextHandler;
	}

	public abstract void handlerRequest(Order order);
}