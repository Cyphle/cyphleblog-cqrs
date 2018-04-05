package fr.cqrs.domain.command;

import fr.cqrs.domain.aggregate.Table;
import fr.cqrs.domain.exceptions.TableNotFoundException;
import fr.cqrs.infra.repositories.TableRepository;

import static fr.cqrs.domain.aggregate.Table.UNOCCUPIED_TABLE;

public class OrderProductCommandHandler implements CommandHandler {
  private TableRepository tableRepository;

  public OrderProductCommandHandler(TableRepository tableRepository) {
    this.tableRepository = tableRepository;
  }

  @Override
  public void handle(Command command) throws Exception {
    Table table = this.tableRepository.getByAggregateId(((OrderProductCommand) command).getTableId());
    if (table.equals(UNOCCUPIED_TABLE))
      throw new TableNotFoundException();
    table.addOrder(((OrderProductCommand) command).getProduct());
    this.tableRepository.save(table);
  }
}
