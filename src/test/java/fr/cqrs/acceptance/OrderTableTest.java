package fr.cqrs.acceptance;

import fr.cqrs.domain.aggregate.Table;
import fr.cqrs.domain.command.GetTableCommand;
import fr.cqrs.domain.command.GetTableCommandHandler;
import fr.cqrs.domain.query.SearchClientTableQuery;
import fr.cqrs.domain.query.SearchClientTableQueryHandler;
import fr.cqrs.domain.valueobjects.Client;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTableTest {
  private SearchClientTableQueryHandler searchClientTableQueryHandler;
  private GetTableCommandHandler getTableCommandHandler;

  // En tant que client, je souhaite avoir une table afin de pouvoir être servi.
  @Test
  public void should_order_a_table_in_order_to_get_served() throws Exception {
    // Given
    Client client = new Client("John Doe");
    // When
    GetTableCommand command = new GetTableCommand(client.getName());
    getTableCommandHandler.handle(command);
    // Then
    SearchClientTableQuery query = new SearchClientTableQuery(client.getName());
    List<Table> table = searchClientTableQueryHandler.handle(query);
    assertThat(table).hasSize(1);
  }
}
