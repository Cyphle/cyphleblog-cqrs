package fr.cqrs.acceptance;

import fr.cqrs.domain.aggregate.Table;
import fr.cqrs.domain.command.GetTableCommand;
import fr.cqrs.domain.command.GetTableCommandHandler;
import fr.cqrs.domain.command.OrderProductCommand;
import fr.cqrs.domain.command.OrderProductCommandHandler;
import fr.cqrs.domain.query.GetTableOrdersQuery;
import fr.cqrs.domain.query.GetTableOrdersQueryHandler;
import fr.cqrs.domain.query.SearchClientTableQuery;
import fr.cqrs.domain.query.SearchClientTableQueryHandler;
import fr.cqrs.domain.valueobjects.Name;
import fr.cqrs.domain.valueobjects.Product;
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

  @Before
  public void setUp() throws Exception {
    idGenerator = new UUIDGenerator();
    tableRepository = new TableRepositoryImpl();
    getTableCommandHandler = new GetTableCommandHandler(tableRepository, idGenerator);
    searchClientTableQueryHandler = new SearchClientTableQueryHandler(tableRepository);
    orderProductCommandHandler = new OrderProductCommandHandler(tableRepository);
    getTableOrdersQueryHandler = new GetTableOrdersQueryHandler(tableRepository);

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
