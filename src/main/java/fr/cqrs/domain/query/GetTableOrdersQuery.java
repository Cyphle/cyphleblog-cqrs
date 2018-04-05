package fr.cqrs.domain.query;

import fr.cqrs.domain.valueobjects.Id;

public class GetTableOrdersQuery implements Query {
  private Id aggregateId;

  public GetTableOrdersQuery(Id aggregateId) {
    this.aggregateId = aggregateId;
  }

  public Id getTableId() {
    return aggregateId;
  }
}
