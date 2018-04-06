package fr.cqrs.command.handlers;

import fr.cqrs.command.commands.Command;
import fr.cqrs.command.aggregate.Table;
import fr.cqrs.command.commands.GetTableCommand;
import fr.cqrs.command.exceptions.ClientAlreadyHasTableException;
import fr.cqrs.command.valueobjects.Id;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.utils.IdGenerator;

import java.util.List;

public class GetTableCommandHandler implements CommandHandler {
  private TableRepository tableRepository;
  private IdGenerator idGenerator;

  public GetTableCommandHandler(TableRepository tableRepository, IdGenerator idGenerator) {
    this.tableRepository = tableRepository;
    this.idGenerator = idGenerator;
  }

  public void handle(Command command) throws Exception {
    checkIfClientAlreadyHasATable((GetTableCommand) command);
    Table table = Table.newTable(Id.of(idGenerator.generate()), ((GetTableCommand) command).getClientName());
    this.tableRepository.save(table);
  }

  private void checkIfClientAlreadyHasATable(GetTableCommand command) throws ClientAlreadyHasTableException {
    List<Table> tables = this.tableRepository.getAllTables();
    if (tables.stream().anyMatch(table -> table.hasClient(command.getClientName())))
      throw new ClientAlreadyHasTableException();
  }
}
