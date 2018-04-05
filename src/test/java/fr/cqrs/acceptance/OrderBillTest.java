package fr.cqrs.acceptance;

import fr.cqrs.command.valueobjects.*;
import fr.cqrs.common.Quantity;
import fr.cqrs.command.aggregate.Table;
import fr.cqrs.command.commands.GetTableCommand;
import fr.cqrs.command.handlers.GetTableCommandHandler;
import fr.cqrs.command.commands.OrderProductCommand;
import fr.cqrs.command.handlers.OrderProductCommandHandler;
import fr.cqrs.query.queries.GetTableBillQuery;
import fr.cqrs.query.handlers.GetTableBillQueryHandler;
import fr.cqrs.query.queries.SearchClientTableQuery;
import fr.cqrs.query.handlers.SearchClientTableQueryHandler;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.infra.repositories.TableRepositoryImpl;
import fr.cqrs.utils.IdGenerator;
import fr.cqrs.utils.UUIDGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderBillTest {
  private GetTableCommandHandler getTableCommandHandler;
  private TableRepository tableRepository;
  private IdGenerator idGenerator;
  private SearchClientTableQueryHandler searchClientTableQueryHandler;
  private OrderProductCommandHandler orderProductCommandHandler;
  private GetTableBillQueryHandler getTableBillQueryHandler;

  @Before
  public void setUp() throws Exception {
    idGenerator = new UUIDGenerator();
    tableRepository = new TableRepositoryImpl();
    getTableCommandHandler = new GetTableCommandHandler(tableRepository, idGenerator);
    searchClientTableQueryHandler = new SearchClientTableQueryHandler(tableRepository);
    orderProductCommandHandler = new OrderProductCommandHandler(tableRepository);
    getTableBillQueryHandler = new GetTableBillQueryHandler(tableRepository);

    GetTableCommand command = new GetTableCommand(Name.of("John Doe"));
    getTableCommandHandler.handle(command);
  }

  // En tant que client, je souhaite commander la note afin de régler mon dû.
  @Test
  public void should_order_the_bill() throws Exception {
    // Given
    Table table = searchClientTableQueryHandler.handle(new SearchClientTableQuery(Name.of("John Doe"))).get(0);
    // When
    OrderProductCommand command = new OrderProductCommand(table.getAggregateId(), Product.BEER);
    orderProductCommandHandler.handle(command);
    // Then
    GetTableBillQuery query = new GetTableBillQuery(table.getAggregateId());
    List<Bill> bill = getTableBillQueryHandler.handle(query);
    assertThat(bill).hasSize(1);
    assertThat(bill.get(0).getEntries()).contains(
            BillEntry.of(Product.BEER, Quantity.of(1), Money.of(6))
    );
  }
}
