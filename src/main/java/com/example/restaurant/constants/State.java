package com.example.restaurant.constants;

public enum State {
  CREATED("Creada"),
  CONFIRMED("Confirmada"),
  CANCELED("Cancelada"),
  IN_PREPARATION("En preparacion"),
  COMPLETED("Completado"),
  DELIVERED("Entregada");

  private String name;

  State(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static boolean isValidState(String state) {
    try {
      State.valueOf(state);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
