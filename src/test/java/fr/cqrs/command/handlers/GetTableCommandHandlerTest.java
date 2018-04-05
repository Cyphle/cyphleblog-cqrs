package fr.cqrs.command.handlers;

import fr.cqrs.command.aggregate.Table;
import fr.cqrs.command.commands.GetTableCommand;
import fr.cqrs.command.exceptions.ClientAlreadyHasTableException;
import fr.cqrs.command.handlers.GetTableCommandHandler;
import fr.cqrs.command.valueobjects.Client;
import fr.cqrs.command.valueobjects.Id;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.utils.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GetTableCommandHandlerTest {
  @Mock
  private TableRepository tableRepository;
  @Mock
  private IdGenerator idGenerator;
  private GetTableCommandHandler getTableCommandHandler;

  @Before
  public void setUp() {
    given(idGenerator.generate()).willReturn("1");
    getTableCommandHandler = new GetTableCommandHandler(tableRepository, idGenerator);
  }

  @Test
  public void should_add_comment_to_content() throws Exception {
    // Given
    Client client = Client.withName("John Doe");
    GetTableCommand command = new GetTableCommand(client.getName());
    // When
    getTableCommandHandler.handle(command);
    // Then
    verify(tableRepository).save(Table.of(Id.of("1"), client.getName()));
  }

  @Test(expected = ClientAlreadyHasTableException.class)
  public void should_throw_exception_if_client_already_has_a_table() throws Exception {
    // Given
    given(tableRepository.getAllTables()).willReturn(Collections.singletonList(
            Table.of(Id.of("1"), Client.withName("John Doe").getName())
    ));
    Client client = Client.withName("John Doe");
    GetTableCommand command = new GetTableCommand(client.getName());
    // When
    getTableCommandHandler.handle(command);
  }
}
