package com.example.restaurant.models;

import com.example.restaurant.constants.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DishTest {

	@Test
	void applyAdjustFrecuent() {
		Dish dish = new Dish(1L, "Pollo", "Pollo apanado", 38f);
		dish.setDishType(Type.POPULAR.getName());
		dish.setAdjust(1.0573f);
		Float adjust = dish.applyAdjust();

		assertEquals(40.1774f, adjust);
	}
}