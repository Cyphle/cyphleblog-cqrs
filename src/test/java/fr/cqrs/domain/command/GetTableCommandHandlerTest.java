package fr.cqrs.domain.command;

import fr.cqrs.domain.aggregate.Table;
import fr.cqrs.domain.valueobjects.Client;
import fr.cqrs.domain.valueobjects.Id;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.utils.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
}
