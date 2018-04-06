package fr.cqrs.command.aggregate;

import fr.cqrs.command.events.Event;
import fr.cqrs.command.events.ProductAddedEvent;
import fr.cqrs.command.events.TableOrderedEvent;
import fr.cqrs.command.valueobjects.Id;
import fr.cqrs.command.valueobjects.Name;
import fr.cqrs.command.valueobjects.Product;
import fr.cqrs.common.Quantity;

import java.util.*;

public class Table {
  public static final Table UNOCCUPIED_TABLE = new Table();
  private Id id;
  private Name clientName;
  private Map<Product, Quantity> products;
  private List<Event> changes;

  public Table() { }

  private Table(Id id, Name clientName) {
    TableOrderedEvent tableOrderedEvent = new TableOrderedEvent(id, clientName);
    apply(tableOrderedEvent);
    saveChanges(tableOrderedEvent);
  }

  public Id getAggregateId() {
    return id;
  }

  public List<Event> getChanges() {
    return changes;
  }

  public Map<Product, Quantity> getProducts() {
    return products;
  }

  public boolean hasClient(Name clientName) {
    return this.clientName.equals(clientName);
  }

  public boolean is(Id id) {
    return this.id.equals(id);
  }

  public void addOrder(Product product) {
    ProductAddedEvent event = new ProductAddedEvent(id, product);
    apply(event);
    saveChanges(event);
  }

  public Table apply(ProductAddedEvent event) {
    this.products.merge(event.getProduct(), Quantity.of(1), Quantity::add);
    return this;
  }

  public Table apply(TableOrderedEvent event) {
    id = event.getId();
    clientName = event.getClientName();
    products = new HashMap<>();
    changes = new ArrayList<>();
    return this;
  }

  public static Table replay(List<Event> events) {
    if (events.isEmpty())
      return UNOCCUPIED_TABLE;

    return events.stream()
            .reduce(new Table(),
                    (table, event) -> (Table) event.apply(table),
                    (table1, table2) -> table2);
  }

  private void saveChanges(Event event) {
    changes.add(event);
  }

  public static Table newTable(Id id, Name clientName) {
    return new Table(id, clientName);
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
}
