package fr.cqrs.domain.command;

import fr.cqrs.domain.Quantity;
import fr.cqrs.domain.aggregate.Table;
import fr.cqrs.domain.exceptions.TableNotFoundException;
import fr.cqrs.domain.valueobjects.Id;
import fr.cqrs.domain.valueobjects.Name;
import fr.cqrs.domain.valueobjects.Product;
import fr.cqrs.infra.repositories.TableRepository;
import org.assertj.core.util.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderProductCommandHandlerTest {
  @Mock
  private TableRepository tableRepository;
  private OrderProductCommandHandler orderProductCommandHandler;

  @Before
  public void setUp() throws Exception {
    given(tableRepository.getByAggregateId(Id.of("1"))).willReturn(Table.of(Id.of("1"), Name.of("John Doe")));
    given(tableRepository.getByAggregateId(Id.of("2"))).willReturn(Table.UNOCCUPIED_TABLE);
    orderProductCommandHandler = new OrderProductCommandHandler(tableRepository);
  }

  @Test
  public void should_add_comment_to_content() throws Exception {
    // Given
    OrderProductCommand command = new OrderProductCommand(Id.of("1"), Product.BEER);
    // When
    orderProductCommandHandler.handle(command);
    // Then
    verify(tableRepository).save(Table.of(Id.of("1"), Name.of("John Doe"), Maps.newHashMap(Product.BEER, Quantity.of(1))));
  }

  @Test(expected = TableNotFoundException.class)
  public void should_throw_exception_if_table_is_not_found() throws Exception {
    // Given
    OrderProductCommand command = new OrderProductCommand(Id.of("2"), Product.BEER);
    // When
    orderProductCommandHandler.handle(command);
  }
}
