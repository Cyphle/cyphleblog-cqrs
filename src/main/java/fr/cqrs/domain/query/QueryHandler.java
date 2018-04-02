package fr.cqrs.domain.query;

import java.util.List;

public interface QueryHandler<T> {
  List<T> handle(Query query);
}
