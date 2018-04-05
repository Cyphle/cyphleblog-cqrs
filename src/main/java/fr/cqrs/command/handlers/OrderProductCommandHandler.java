package fr.cqrs.command.handlers;

import fr.cqrs.command.aggregate.Table;
import fr.cqrs.command.commands.Command;
import fr.cqrs.command.commands.OrderProductCommand;
import fr.cqrs.command.exceptions.TableNotFoundException;
import fr.cqrs.infra.repositories.TableRepository;

public class OrderProductCommandHandler implements CommandHandler {
  private TableRepository tableRepository;

  public OrderProductCommandHandler(TableRepository tableRepository) {
    this.tableRepository = tableRepository;
  }

  @Override
  public void handle(Command command) throws Exception {
    Table table = this.tableRepository.getByAggregateId(((OrderProductCommand) command).getTableId());
    if (table.equals(Table.UNOCCUPIED_TABLE))
      throw new TableNotFoundException();
    table.addOrder(((OrderProductCommand) command).getProduct());
    this.tableRepository.save(table);
  }
}
