package fr.cqrs.domain.query;

import fr.cqrs.domain.valueobjects.Id;

public class GetTableBill implements Query {
  private Id aggregateId;

  public GetTableBill(Id aggregateId) {
    this.aggregateId = aggregateId;
  }
}
