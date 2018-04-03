package fr.cqrs.domain.command;

import fr.cqrs.domain.aggregate.Table;
import fr.cqrs.domain.exceptions.ClientAlreadyHasTableException;
import fr.cqrs.domain.valueobjects.Id;
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
    Table table = Table.of(Id.of(this.idGenerator.generate()), ((GetTableCommand) command).getClientName());
    this.tableRepository.save(table);
  }

  private void checkIfClientAlreadyHasATable(GetTableCommand command) throws ClientAlreadyHasTableException {
    List<Table> tables = this.tableRepository.getAllTables();
    if (tables.stream().anyMatch(table -> table.hasClient(command.getClientName())))
      throw new ClientAlreadyHasTableException();
  }
}
