package com.example.restaurant.services.interfaces;

public interface ICommandModification<T, S> {
	T execute(Long id, S value);
}