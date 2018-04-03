package fr.cqrs.domain.query;

import fr.cqrs.domain.valueobjects.Name;

public class SearchClientTableQuery implements Query {
  private Name clientName;

  public SearchClientTableQuery(Name clientName) {
    this.clientName = clientName;
  }

  public Name getClientName() {
    return clientName;
  }
}
