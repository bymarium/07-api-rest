package com.example.restaurant.constants;

public enum Type {
	BRONZE("Bronce"),
	SILVER("Plata"),
	GOLD("Oro"),
	DIAMOND("Diamante"),
	COMMON("Comun"),
	POPULAR("Popular");

	private String name;

	Type(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}