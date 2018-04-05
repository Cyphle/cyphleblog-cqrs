package fr.cqrs.command.aggregate;

import fr.cqrs.command.valueobjects.Id;
import fr.cqrs.command.valueobjects.Name;
import fr.cqrs.command.valueobjects.Product;
import fr.cqrs.common.Quantity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Table {
  public static final Table UNOCCUPIED_TABLE = Table.of(Id.of(""), Name.of(""));
  private final Id id;
  private final Name clientName;
  private final Map<Product, Quantity> products;

  private Table(Id id, Name clientName) {
    this.id = id;
    this.clientName = clientName;
    this.products = new HashMap<>();
  }

  public Table(Id id, Name clientName, Map<Product, Quantity> products) {
    this.id = id;
    this.clientName = clientName;
    this.products = products;
  }

  public Id getAggregateId() {
    return id;
  }

  public boolean hasClient(Name clientName) {
    return this.clientName.equals(clientName);
  }

  public boolean is(Id tableId) {
    return this.id.equals(tableId);
  }

  public void addOrder(Product product) {
    this.products.merge(product, Quantity.of(1), Quantity::add);
  }

  public Map<Product, Quantity> getProducts() {
    return products;
  }

  public static Table of(Id id, Name clientName) {
    return new Table(id, clientName);
  }

  public static Table of(Id id, Name clientName, Map<Product, Quantity> products) {
    return new Table(id, clientName, products);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Table table = (Table) o;
    return Objects.equals(id, table.id) &&
            Objects.equals(clientName, table.clientName) &&
            Objects.equals(products, table.products);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, clientName, products);
  }

  @Override
  public String toString() {
    return "Table{" +
            "id=" + id +
            ", clientName=" + clientName +
            ", products=" + products +
            '}';
  }
}
