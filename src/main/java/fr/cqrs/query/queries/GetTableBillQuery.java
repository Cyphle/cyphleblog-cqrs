package fr.cqrs.query.queries;

import fr.cqrs.command.valueobjects.Id;

public class GetTableBillQuery implements Query {
  private Id aggregateId;

  public GetTableBillQuery(Id aggregateId) {
    this.aggregateId = aggregateId;
  }

  public Id getTableId() {
    return aggregateId;
  }
}
