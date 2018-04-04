package fr.cqrs.domain.valueobjects;

import fr.cqrs.domain.Quantity;

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
}
