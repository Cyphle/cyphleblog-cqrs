package fr.cqrs.domain.valueobjects;

public enum Product {
  BEER(Money.of(6.0));

  public final Money price;

  Product(Money price) {
    this.price = price;
  }
}
