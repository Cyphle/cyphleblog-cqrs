package fr.cqrs.command.valueobjects;

public enum Product {
  BEER(Money.of(6.0)), BOARD(Money.of(15.0));

  public final Money price;

  Product(Money price) {
    this.price = price;
  }
}
