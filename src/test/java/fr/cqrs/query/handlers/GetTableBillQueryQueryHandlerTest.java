package fr.cqrs.query.handlers;

import fr.cqrs.command.valueobjects.*;
import fr.cqrs.common.Quantity;
import fr.cqrs.command.aggregate.Table;
import fr.cqrs.infra.repositories.QueryRepository;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.query.handlers.GetTableBillQueryHandler;
import fr.cqrs.query.queries.GetTableBillQuery;
import org.assertj.core.util.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GetTableBillQueryQueryHandlerTest {
  @Mock
  private QueryRepository queryRepository;
  private GetTableBillQueryHandler getTableBillQueryHandler;

  @Before
  public void setUp() throws Exception {
    Map<Product, BillEntry> entries = new HashMap<>();
    entries.put(Product.BEER, BillEntry.of(Product.BEER, Quantity.of(1), Product.BEER.price));
    given(queryRepository.getBillOf(Id.of("1"))).willReturn(Bill.of(entries));

    getTableBillQueryHandler = new GetTableBillQueryHandler(queryRepository);
  }

  @Test
  public void should_get_bill_of_a_table() throws Exception {
    // Given
    GetTableBillQuery query = new GetTableBillQuery(Id.of("1"));
    // When
    List<Bill> bill = getTableBillQueryHandler.handle(query);
    // Then
    assertThat(bill).hasSize(1);
    assertThat(bill.get(0).getEntries().values()).contains(
            BillEntry.of(Product.BEER, Quantity.of(1), Money.of(6))
    );
  }
}
