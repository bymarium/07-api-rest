package com.example.restaurant.services.interfaces;

public interface ICommandWithParameters<T, R, S> {
	T execute(R parameter1, S parameter2);
}