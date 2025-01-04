package com.example.restaurant.services.interfaces;

public interface ICommandParametrized<T, R> {
	T execute(R parameter);
}