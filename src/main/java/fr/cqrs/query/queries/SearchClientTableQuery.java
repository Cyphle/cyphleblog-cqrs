package fr.cqrs.query.queries;

import fr.cqrs.command.valueobjects.Name;

public class SearchClientTableQuery implements Query {
  private Name clientName;

  public SearchClientTableQuery(Name clientName) {
    this.clientName = clientName;
  }

  public Name getClientName() {
    return clientName;
  }
}
