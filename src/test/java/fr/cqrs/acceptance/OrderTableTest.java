package fr.cqrs.acceptance;

import fr.cqrs.infra.repositories.TableRepositoryImpl;
import fr.cqrs.command.aggregate.Table;
import fr.cqrs.command.commands.GetTableCommand;
import fr.cqrs.command.handlers.GetTableCommandHandler;
import fr.cqrs.query.queries.SearchClientTableQuery;
import fr.cqrs.query.handlers.SearchClientTableQueryHandler;
import fr.cqrs.command.valueobjects.Client;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.utils.IdGenerator;
import fr.cqrs.utils.UUIDGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTableTest {
  private SearchClientTableQueryHandler searchClientTableQueryHandler;
  private GetTableCommandHandler getTableCommandHandler;
  private TableRepository tableRepository;
  private IdGenerator idGenerator;

  @Before
  public void setUp() throws Exception {
    idGenerator = new UUIDGenerator();
    tableRepository = new TableRepositoryImpl();
    searchClientTableQueryHandler = new SearchClientTableQueryHandler(tableRepository);
    getTableCommandHandler = new GetTableCommandHandler(tableRepository, idGenerator);
  }

  // En tant que client, je souhaite avoir une table afin de pouvoir être servi.
  @Test
  public void should_order_a_table_in_order_to_get_served() throws Exception {
    // Given
    Client client = Client.withName("John Doe");
    // When
    GetTableCommand command = new GetTableCommand(client.getName());
    getTableCommandHandler.handle(command);
    // Then
    SearchClientTableQuery query = new SearchClientTableQuery(client.getName());
    List<Table> table = searchClientTableQueryHandler.handle(query);
    assertThat(table).hasSize(1);
  }
}
