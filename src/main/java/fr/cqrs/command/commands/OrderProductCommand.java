package fr.cqrs.command.commands;

import fr.cqrs.command.valueobjects.Product;
import fr.cqrs.command.valueobjects.Id;

public class OrderProductCommand implements Command {
  private final Id aggregateId;
  private final Product product;

  public OrderProductCommand(Id aggregateId, Product product) {
    this.aggregateId = aggregateId;
    this.product = product;
  }

  public Id getTableId() {
    return aggregateId;
  }

  public Product getProduct() {
    return product;
  }
}
