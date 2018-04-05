package fr.cqrs.query.handlers;

import fr.cqrs.query.queries.Query;

import java.util.List;

public interface QueryHandler<T> {
  List<T> handle(Query query);
}
