package fr.cqrs.query.handlers;

import fr.cqrs.command.valueobjects.*;
import fr.cqrs.common.Quantity;
import fr.cqrs.command.aggregate.Table;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.query.handlers.GetTableBillQueryHandler;
import fr.cqrs.query.queries.GetTableBillQuery;
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
public class GetTableBillQueryQueryHandlerTest {
  @Mock
  private TableRepository tableRepository;
  private GetTableBillQueryHandler getTableBillQueryHandler;

  @Before
  public void setUp() throws Exception {
    given(tableRepository.getByAggregateId(Id.of("1"))).willReturn(
            Table.of(Id.of("1"), Name.of("John Doe"), Maps.newHashMap(Product.BEER, Quantity.of(1)))
    );
    getTableBillQueryHandler = new GetTableBillQueryHandler(tableRepository);
  }

  @Test
  public void should_get_bill_of_a_table() throws Exception {
    // Given
    GetTableBillQuery query = new GetTableBillQuery(Id.of("1"));
    // When
    List<Bill> bill = getTableBillQueryHandler.handle(query);
    // Then
    assertThat(bill).hasSize(1);
    assertThat(bill.get(0).getEntries()).contains(
            BillEntry.of(Product.BEER, Quantity.of(1), Money.of(6))
    );
  }
}
