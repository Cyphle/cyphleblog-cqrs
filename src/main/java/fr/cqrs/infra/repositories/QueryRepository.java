package fr.cqrs.infra.repositories;

import fr.cqrs.command.aggregate.Table;
import fr.cqrs.command.events.Event;
import fr.cqrs.command.exceptions.TableNotFoundException;
import fr.cqrs.command.valueobjects.Bill;
import fr.cqrs.command.valueobjects.Id;
import fr.cqrs.command.valueobjects.Name;
import fr.cqrs.command.valueobjects.Product;
import fr.cqrs.common.Quantity;

import java.util.List;
import java.util.Map;

public interface QueryRepository {
  void apply(Event event);

  Table getClientTable(Name clientName) throws TableNotFoundException;

  Map<Product, Quantity> getTableOrders(Id tableId) throws TableNotFoundException;

  Bill getBillOf(Id tableId);
}
