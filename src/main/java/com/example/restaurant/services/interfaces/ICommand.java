package com.example.restaurant.services.interfaces;

public interface ICommand<T> {
	T execute();
}