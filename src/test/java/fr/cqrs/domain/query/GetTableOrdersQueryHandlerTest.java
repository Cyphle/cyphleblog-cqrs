package fr.cqrs.domain.query;

import fr.cqrs.domain.Quantity;
import fr.cqrs.domain.aggregate.Table;
import fr.cqrs.domain.valueobjects.*;
import fr.cqrs.infra.repositories.TableRepository;
import org.assertj.core.util.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GetTableOrdersQueryHandlerTest {
  @Mock
  private TableRepository tableRepository;
  private GetTableOrdersQueryHandler getTableOrdersQueryHandler;

  @Before
  public void setUp() throws Exception {
    given(tableRepository.getByAggregateId(Id.of("1"))).willReturn(
            Table.of(Id.of("1"), Name.of("John Doe"), Maps.newHashMap(Product.BEER, Quantity.of(1)))
    );
    getTableOrdersQueryHandler = new GetTableOrdersQueryHandler(tableRepository);
  }

  @Test
  public void should_get_orders_of_a_table() throws Exception {
    // Given
    GetTableOrdersQuery query = new GetTableOrdersQuery(Id.of("1"));
    // When
    List<Product> orders = getTableOrdersQueryHandler.handle(query);
    // Then
    assertThat(orders).hasSize(1);
    assertThat(orders).contains(Product.BEER);
  }
}
