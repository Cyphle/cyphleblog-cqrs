package fr.cqrs.command.events;

import fr.cqrs.command.aggregate.Table;
import fr.cqrs.command.valueobjects.Id;
import fr.cqrs.command.valueobjects.Product;

public class ProductAddedEvent implements Event<Table> {
  private final Id id;
  private final Product product;

  public ProductAddedEvent(Id id, Product product) {
    this.id = id;
    this.product = product;
  }

  @Override
  public String getAggregateId() {
    return id.getId();
  }

  @Override
  public Table apply(Table aggregate) {
    return aggregate.apply(this);
  }

  public Product getProduct() {
    return product;
  }
}
