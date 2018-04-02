package fr.cqrs.domain.command;

import fr.cqrs.domain.valueobjects.Name;

public class GetTableCommand implements Command {
  private Name clientName;

  public GetTableCommand(Name clientName) {
    this.clientName = clientName;
  }
}
