package fr.cqrs.domain.query;

import fr.cqrs.domain.aggregate.Table;

import java.util.List;

public class SearchClientTableQueryHandler implements QueryHandler<Table> {
  public List<Table> handle(Query query) {
    throw new UnsupportedOperationException();
  }
}
