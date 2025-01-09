package com.example.restaurant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RestaurantApplicationTest {
	@Test
	void main() {
		assertDoesNotThrow(() -> RestaurantApplication.main(new String[0]));
	}
}