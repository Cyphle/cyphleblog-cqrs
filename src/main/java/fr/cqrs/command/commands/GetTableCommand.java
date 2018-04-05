package fr.cqrs.command.commands;

import fr.cqrs.command.valueobjects.Name;

public class GetTableCommand implements Command {
  private Name clientName;

  public GetTableCommand(Name clientName) {
    this.clientName = clientName;
  }

  public Name getClientName() {
    return clientName;
  }
}
