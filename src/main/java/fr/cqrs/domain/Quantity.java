package fr.cqrs.domain;

public class Quantity {
  private int quantity;

  private Quantity(int quantity) {
    this.quantity = quantity;
  }

  public static Quantity of(int quantity) {
    return new Quantity(quantity);
  }
}
