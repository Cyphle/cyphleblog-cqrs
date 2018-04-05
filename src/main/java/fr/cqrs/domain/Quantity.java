package fr.cqrs.domain;

import java.util.Objects;

public class Quantity {
  private int quantity;

  private Quantity(int quantity) {
    this.quantity = quantity;
  }

  public Quantity add(Quantity toAdd) {
    return Quantity.of(quantity + toAdd.quantity);
  }

  public static Quantity of(int quantity) {
    return new Quantity(quantity);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Quantity quantity1 = (Quantity) o;
    return quantity == quantity1.quantity;
  }

  @Override
  public int hashCode() {

    return Objects.hash(quantity);
  }
}
