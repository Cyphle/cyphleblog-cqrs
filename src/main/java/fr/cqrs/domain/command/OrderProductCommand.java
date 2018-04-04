package fr.cqrs.domain.command;

import fr.cqrs.domain.valueobjects.Id;
import fr.cqrs.domain.valueobjects.Product;

public class OrderProductCommand implements Command {
  private final Id aggregateId;
  private final Product product;

  public OrderProductCommand(Id aggregateId, Product product) {
    this.aggregateId = aggregateId;
    this.product = product;
  }
}
