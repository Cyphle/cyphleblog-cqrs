package fr.cqrs.domain.command;

public interface CommandHandler {
  void handle(Command command) throws Exception;
}
