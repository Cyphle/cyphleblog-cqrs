package fr.cqrs.query.handlers;

import fr.cqrs.command.aggregate.Table;
import fr.cqrs.command.events.TableOrderedEvent;
import fr.cqrs.command.valueobjects.Client;
import fr.cqrs.command.valueobjects.Id;
import fr.cqrs.infra.repositories.QueryRepository;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.query.handlers.SearchClientTableQueryHandler;
import fr.cqrs.query.queries.SearchClientTableQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SearchClientTableQueryHandlerTest {
  @Mock
  private QueryRepository queryRepository;
  private SearchClientTableQueryHandler searchClientTableQueryHandler;

  @Before
  public void setUp() throws Exception {
    Table table = new Table();
    table.apply(new TableOrderedEvent(Id.of("1"), Client.withName("John Doe").getName()));
    given(queryRepository.getClientTable(Client.withName("John Doe").getName())).willReturn(table);
    searchClientTableQueryHandler = new SearchClientTableQueryHandler(queryRepository);
  }

  @Test
  public void should_search_table_of_client() throws Exception {
    // Given
    Client client = Client.withName("John Doe");
    SearchClientTableQuery query = new SearchClientTableQuery(client.getName());
    // When
    List<Table> tables = searchClientTableQueryHandler.handle(query);
    // Then
    Table table = new Table();
    table.apply(new TableOrderedEvent(Id.of("1"), Client.withName("John Doe").getName()));
    assertThat(tables).hasSize(1);
    assertThat(tables.get(0)).isEqualTo(table);
  }
}
