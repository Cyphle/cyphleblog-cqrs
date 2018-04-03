package fr.cqrs.domain.query;

import fr.cqrs.domain.aggregate.Table;
import fr.cqrs.infra.repositories.TableRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SearchClientTableQueryHandler implements QueryHandler<Table> {
  private TableRepository tableRepository;

  public SearchClientTableQueryHandler(TableRepository tableRepository) {
    this.tableRepository = tableRepository;
  }

  public List<Table> handle(Query query) {
    return this.tableRepository
            .getAllTables()
            .stream()
            .filter(table -> table.hasClient(((SearchClientTableQuery) query).getClientName()))
            .collect(Collectors.toList());
  }
}
