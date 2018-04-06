package fr.cqrs.infra.repositories;

import fr.cqrs.command.aggregate.Table;
import fr.cqrs.command.events.Event;
import fr.cqrs.command.events.ProductAddedEvent;
import fr.cqrs.command.events.TableOrderedEvent;
import fr.cqrs.command.exceptions.TableNotFoundException;
import fr.cqrs.command.valueobjects.Bill;
import fr.cqrs.command.valueobjects.Id;
import fr.cqrs.command.valueobjects.Name;
import fr.cqrs.command.valueobjects.Product;
import fr.cqrs.common.Quantity;
import fr.cqrs.query.valueobjects.HistoryEntry;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class QueryRepositoryImpl implements QueryRepository {
  private List<Table> tables;
  private Map<String, Bill> bills;
  private List<HistoryEntry> history;

  public QueryRepositoryImpl() {
    tables = new ArrayList<>();
    bills = new HashMap<>();
    history = new ArrayList<>();
  }

  @Override
  public void apply(Event event) {
    switch (event.getClass().getSimpleName()) {
      case "TableOrderedEvent":
        projectTable((TableOrderedEvent) event);
        projectBill((TableOrderedEvent) event);
        break;
      case "ProductAddedEvent":
        projectOrder((ProductAddedEvent) event);
        projectBill((ProductAddedEvent) event);
        projectHistory((ProductAddedEvent) event);
        break;
    }
  }

  @Override
  public Table getClientTable(Name clientName) throws TableNotFoundException {
    return tables.stream()
            .filter(table -> table.hasClient(clientName))
            .findAny()
            .orElseThrow(TableNotFoundException::new);
  }

  @Override
  public Map<Product, Quantity> getTableOrders(Id tableId) throws TableNotFoundException {
    return tables.stream()
            .filter(table -> table.is(tableId))
            .findAny()
            .orElseThrow(TableNotFoundException::new).getProducts();

  }

  @Override
  public Bill getBillOf(Id tableId) {
    return bills.get(tableId.getId());
  }

  @Override
  public List<HistoryEntry> getHistoryOfMonth(int year, Month month) {
    return history
            .stream()
            .filter(entry -> entry.getYear() == year && entry.getMonth().equals(month))
            .collect(Collectors.toList());
  }

  private void projectBill(TableOrderedEvent event) {
    bills.put(event.getAggregateId(), Bill.of(new HashMap<>()));
  }

  private void projectBill(ProductAddedEvent event) {
    bills.get(event.getAggregateId()).addProduct(event.getProduct());
  }

  private void projectTable(TableOrderedEvent event) {
    Table table = new Table();
    tables.add(table.apply(event));
  }

  private void projectOrder(ProductAddedEvent event) {
    Table table = this.getTableById(Id.of(event.getAggregateId()));
    table.apply(event);
  }

  private void projectHistory(ProductAddedEvent event) {
    history.add(HistoryEntry.of(2018, Month.MARCH, 5, event.getProduct(), event.getAggregateId()));
  }

  private Table getTableById(Id id) {
    return tables.stream()
            .filter(table -> table.is(id))
            .findAny()
            .orElseThrow(RuntimeException::new);
  }
}
