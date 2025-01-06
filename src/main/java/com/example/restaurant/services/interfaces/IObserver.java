package com.example.restaurant.services.interfaces;

import com.example.restaurant.models.Order;

public interface IObserver {
	void update(Order order);
}