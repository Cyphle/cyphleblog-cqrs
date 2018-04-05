package fr.cqrs.acceptance;

import fr.cqrs.domain.Quantity;
import fr.cqrs.domain.aggregate.Table;
import fr.cqrs.domain.command.GetTableCommand;
import fr.cqrs.domain.command.GetTableCommandHandler;
import fr.cqrs.domain.command.OrderProductCommand;
import fr.cqrs.domain.command.OrderProductCommandHandler;
import fr.cqrs.domain.query.GetTableBillQuery;
import fr.cqrs.domain.query.GetTableBillQueryHandler;
import fr.cqrs.domain.query.SearchClientTableQuery;
import fr.cqrs.domain.query.SearchClientTableQueryHandler;
import fr.cqrs.domain.valueobjects.*;
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
