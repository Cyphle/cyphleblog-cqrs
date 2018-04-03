package fr.cqrs.domain.query;

import fr.cqrs.domain.aggregate.Table;
import fr.cqrs.domain.valueobjects.Client;
import fr.cqrs.domain.valueobjects.Id;
import fr.cqrs.infra.repositories.TableRepository;
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
  private TableRepository tableRepository;
  private SearchClientTableQueryHandler searchClientTableQueryHandler;

  @Before
  public void setUp() throws Exception {
    given(tableRepository.getAllTables()).willReturn(Collections.singletonList(Table.of(Id.of("1"), Client.withName("John Doe").getName())));
    searchClientTableQueryHandler = new SearchClientTableQueryHandler(tableRepository);
  }

  @Test
  public void should_search_table_of_client() throws Exception {
    // Given
    Client client = Client.withName("John Doe");
    SearchClientTableQuery query = new SearchClientTableQuery(client.getName());
    // When
    List<Table> tables = searchClientTableQueryHandler.handle(query);
    // Then
    assertThat(tables).hasSize(1);
    assertThat(tables.get(0)).isEqualTo(Table.of(Id.of("1"), Client.withName("John Doe").getName()));
  }
}
