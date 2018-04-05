package fr.cqrs.domain.query;

import fr.cqrs.domain.valueobjects.Id;

public class GetTableBillQuery implements Query {
  private Id aggregateId;

  public GetTableBillQuery(Id aggregateId) {
    this.aggregateId = aggregateId;
  }

  public Id getTableId() {
    return aggregateId;
  }
}
