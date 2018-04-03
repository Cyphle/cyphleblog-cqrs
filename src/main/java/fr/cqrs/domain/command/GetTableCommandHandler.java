package fr.cqrs.domain.command;

import fr.cqrs.domain.aggregate.Table;
import fr.cqrs.domain.valueobjects.Id;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.utils.IdGenerator;

public class GetTableCommandHandler implements CommandHandler {
  private TableRepository tableRepository;
  private IdGenerator idGenerator;

  public GetTableCommandHandler(TableRepository tableRepository, IdGenerator idGenerator) {
    this.tableRepository = tableRepository;
    this.idGenerator = idGenerator;
  }

  public void handle(Command command) throws Exception {
    Table table = Table.of(Id.of(this.idGenerator.generate()), ((GetTableCommand) command).getClientName());
    this.tableRepository.save(table);
  }
}
