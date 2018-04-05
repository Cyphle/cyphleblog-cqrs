package fr.cqrs.query.queries;

import fr.cqrs.command.valueobjects.Id;

public class GetTableOrdersQuery implements Query {
  private Id aggregateId;

  public GetTableOrdersQuery(Id aggregateId) {
    this.aggregateId = aggregateId;
  }

  public Id getTableId() {
    return aggregateId;
  }
}
