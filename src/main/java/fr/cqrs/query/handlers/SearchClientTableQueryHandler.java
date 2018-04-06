package fr.cqrs.query.handlers;

import fr.cqrs.command.aggregate.Table;
import fr.cqrs.infra.repositories.QueryRepository;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.query.queries.SearchClientTableQuery;
import fr.cqrs.query.queries.Query;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SearchClientTableQueryHandler implements QueryHandler<Table> {
  private QueryRepository queryRepository;

  public SearchClientTableQueryHandler(QueryRepository queryRepository) {
    this.queryRepository = queryRepository;
  }

  public List<Table> handle(Query query) throws Exception {
    return Collections.singletonList(this.queryRepository.getClientTable(((SearchClientTableQuery) query).getClientName()));
  }
}
