package fr.cqrs.query.handlers;

import fr.cqrs.command.valueobjects.Id;
import fr.cqrs.command.valueobjects.Name;
import fr.cqrs.command.valueobjects.Product;
import fr.cqrs.common.Quantity;
import fr.cqrs.command.aggregate.Table;
import fr.cqrs.infra.repositories.QueryRepository;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.query.handlers.GetTableOrdersQueryHandler;
import fr.cqrs.query.queries.GetTableOrdersQuery;
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
  private QueryRepository queryRepository;
  private GetTableOrdersQueryHandler getTableOrdersQueryHandler;

  @Before
  public void setUp() throws Exception {
    given(queryRepository.getTableOrders(Id.of("1"))).willReturn(Maps.newHashMap(Product.BEER, Quantity.of(1)));
    getTableOrdersQueryHandler = new GetTableOrdersQueryHandler(queryRepository);
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
