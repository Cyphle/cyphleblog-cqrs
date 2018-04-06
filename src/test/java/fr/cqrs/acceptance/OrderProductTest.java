package fr.cqrs.acceptance;

import fr.cqrs.command.aggregate.Table;
import fr.cqrs.command.commands.GetTableCommand;
import fr.cqrs.command.handlers.GetTableCommandHandler;
import fr.cqrs.command.commands.OrderProductCommand;
import fr.cqrs.command.handlers.OrderProductCommandHandler;
import fr.cqrs.infra.repositories.QueryRepository;
import fr.cqrs.infra.repositories.QueryRepositoryImpl;
import fr.cqrs.query.queries.GetTableOrdersQuery;
import fr.cqrs.query.handlers.GetTableOrdersQueryHandler;
import fr.cqrs.query.queries.SearchClientTableQuery;
import fr.cqrs.query.handlers.SearchClientTableQueryHandler;
import fr.cqrs.command.valueobjects.Name;
import fr.cqrs.command.valueobjects.Product;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.infra.repositories.TableRepositoryImpl;
import fr.cqrs.utils.IdGenerator;
import fr.cqrs.utils.UUIDGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderProductTest {
  private GetTableCommandHandler getTableCommandHandler;
  private TableRepository tableRepository;
  private IdGenerator idGenerator;
  private SearchClientTableQueryHandler searchClientTableQueryHandler;
  private OrderProductCommandHandler orderProductCommandHandler;
  private GetTableOrdersQueryHandler getTableOrdersQueryHandler;
  private QueryRepository queryRepository;

  @Before
  public void setUp() throws Exception {
    idGenerator = new UUIDGenerator();
    queryRepository = new QueryRepositoryImpl();
    tableRepository = new TableRepositoryImpl(queryRepository);
    getTableCommandHandler = new GetTableCommandHandler(tableRepository, idGenerator);
    searchClientTableQueryHandler = new SearchClientTableQueryHandler(queryRepository);
    orderProductCommandHandler = new OrderProductCommandHandler(tableRepository);
    getTableOrdersQueryHandler = new GetTableOrdersQueryHandler(queryRepository);

    GetTableCommand command = new GetTableCommand(Name.of("John Doe"));
    getTableCommandHandler.handle(command);
  }

  // En tant que client, je souhaite commander (une bière et une planche mixte) afin de me ressourcer.
  @Test
  public void should_order_a_table_in_order_to_get_served() throws Exception {
    // Given
    Table table = searchClientTableQueryHandler.handle(new SearchClientTableQuery(Name.of("John Doe"))).get(0);
    // When
    OrderProductCommand command = new OrderProductCommand(table.getAggregateId(), Product.BEER);
    orderProductCommandHandler.handle(command);
    // Then
    GetTableOrdersQuery query = new GetTableOrdersQuery(table.getAggregateId());
    List<Product> orders = getTableOrdersQueryHandler.handle(query);
    assertThat(orders).contains(Product.BEER);
  }
}
