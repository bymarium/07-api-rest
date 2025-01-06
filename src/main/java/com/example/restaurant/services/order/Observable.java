package com.example.restaurant.services.order;

import com.example.restaurant.models.Order;
import com.example.restaurant.services.interfaces.IObserver;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Observable {
	private List<IObserver> observers = new ArrayList<>();

	public void addObserver(IObserver observer) {
		observers.add(observer);
	}

	public void notifyObservers(Order order) {
		for (IObserver observer : observers) {
			observer.update(order);
		}
	}
}