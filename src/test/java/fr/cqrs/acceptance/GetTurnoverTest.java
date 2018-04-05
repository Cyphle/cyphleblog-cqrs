package fr.cqrs.acceptance;

import fr.cqrs.command.aggregate.Table;
import fr.cqrs.command.commands.GetTableCommand;
import fr.cqrs.command.commands.OrderProductCommand;
import fr.cqrs.command.handlers.GetTableCommandHandler;
import fr.cqrs.command.handlers.OrderProductCommandHandler;
import fr.cqrs.command.valueobjects.Money;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.infra.repositories.TableRepositoryImpl;
import fr.cqrs.query.handlers.GetTurnoverOfMonthQueryHandler;
import fr.cqrs.query.valueobjects.HistoryEntry;
import fr.cqrs.query.views.Turnover;
import fr.cqrs.query.handlers.SearchClientTableQueryHandler;
import fr.cqrs.query.queries.GetTurnoverOfMonthQuery;
import fr.cqrs.query.queries.SearchClientTableQuery;
import fr.cqrs.command.valueobjects.Client;
import fr.cqrs.command.valueobjects.Name;
import fr.cqrs.command.valueobjects.Product;
import fr.cqrs.utils.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GetTurnoverTest {
  @Mock
  private IdGenerator idGenerator;
  private GetTableCommandHandler getTableCommandHandler;
  private SearchClientTableQueryHandler searchClientTableQueryHandler;
  private OrderProductCommandHandler orderProductCommandHandler;
  private GetTurnoverOfMonthQueryHandler getTurnoverOfMonthQueryHandler;
  private TableRepository tableRepository;

  @Before
  public void setUp() throws Exception {
    tableRepository = new TableRepositoryImpl();

    getTableCommandHandler = new GetTableCommandHandler(tableRepository, idGenerator);
    searchClientTableQueryHandler = new SearchClientTableQueryHandler(tableRepository);
    orderProductCommandHandler = new OrderProductCommandHandler(tableRepository);
    getTurnoverOfMonthQueryHandler = new GetTurnoverOfMonthQueryHandler();
  }

  @Test
  public void should_get_turn_over_of_current_month() throws Exception {
    // Given
    Client client = Client.withName("John Doe");
    GetTableCommand command = new GetTableCommand(client.getName());
    getTableCommandHandler.handle(command);
    Table table = searchClientTableQueryHandler.handle(new SearchClientTableQuery(Name.of("John Doe"))).get(0);
    OrderProductCommand beerCommand = new OrderProductCommand(table.getAggregateId(), Product.BEER);
    orderProductCommandHandler.handle(beerCommand);
    OrderProductCommand secondBeerCommand = new OrderProductCommand(table.getAggregateId(), Product.BEER);
    orderProductCommandHandler.handle(secondBeerCommand);
    OrderProductCommand cheeseBoardCommand = new OrderProductCommand(table.getAggregateId(), Product.BOARD);
    orderProductCommandHandler.handle(cheeseBoardCommand);
    // When
    GetTurnoverOfMonthQuery query = new GetTurnoverOfMonthQuery(2018, Month.MARCH);
    List<Turnover> turnover = getTurnoverOfMonthQueryHandler.handle(query);
    // Then
    assertThat(turnover).hasSize(1);
    assertThat(turnover.get(0).getTotal()).isEqualTo(Money.of(27.0));
    assertThat(turnover.get(0).getHistory()).contains(
            HistoryEntry.of(2018, Month.MARCH, 5, Product.BEER, "1"),
            HistoryEntry.of(2018, Month.MARCH, 5, Product.BEER, "1"),
            HistoryEntry.of(2018, Month.MARCH, 5, Product.BOARD, "1")
    );
  }
}
