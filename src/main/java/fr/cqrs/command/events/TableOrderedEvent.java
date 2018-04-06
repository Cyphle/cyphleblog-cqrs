package fr.cqrs.command.events;

import fr.cqrs.command.aggregate.Table;
import fr.cqrs.command.valueobjects.Id;
import fr.cqrs.command.valueobjects.Name;

public class TableOrderedEvent implements Event<Table> {
  private Id id;
  private Name clientName;

  public TableOrderedEvent(Id id, Name clientName) {
    this.id = id;
    this.clientName = clientName;
  }

  public Id getId() {
    return id;
  }

  public Name getClientName() {
    return clientName;
  }

  @Override
  public String getAggregateId() {
    return id.getId();
  }

  @Override
  public Table apply(Table aggregate) {
    return aggregate.apply(this);
  }
}
