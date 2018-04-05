package fr.cqrs.command.valueobjects;

import fr.cqrs.common.Quantity;

import java.util.Objects;

public class BillEntry {
  private final Product product;
  private final Quantity quantity;
  private final Money price;

  private BillEntry(Product product, Quantity quantity, Money price) {
    this.product = product;
    this.quantity = quantity;
    this.price = price;
  }

  public static BillEntry of(Product product, Quantity quantity, Money price) {
    return new BillEntry(product, quantity, price);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BillEntry billEntry = (BillEntry) o;
    return product == billEntry.product &&
            Objects.equals(quantity, billEntry.quantity) &&
            Objects.equals(price, billEntry.price);
  }

  @Override
  public int hashCode() {

    return Objects.hash(product, quantity, price);
  }
}
