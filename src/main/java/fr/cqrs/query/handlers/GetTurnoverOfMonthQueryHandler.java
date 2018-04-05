package fr.cqrs.query.handlers;

import fr.cqrs.query.queries.Query;
import fr.cqrs.query.views.Turnover;

import java.util.List;

public class GetTurnoverOfMonthQueryHandler implements QueryHandler<Turnover> {
  @Override
  public List<Turnover> handle(Query query) {
    throw new UnsupportedOperationException();
  }
}
