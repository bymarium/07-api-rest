package com.example.restaurant.constants;

public enum Type {
	COMMON("Comun"),
	FREQUENT("Frecuente"),
	POPULAR("Popular");

	private String name;

	Type(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}