package fr.cqrs.command.handlers;

import fr.cqrs.command.commands.Command;

public interface CommandHandler {
  void handle(Command command) throws Exception;
}
