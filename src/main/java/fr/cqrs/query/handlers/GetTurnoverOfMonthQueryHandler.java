package fr.cqrs.query.handlers;

import fr.cqrs.command.valueobjects.Money;
import fr.cqrs.infra.repositories.QueryRepository;
import fr.cqrs.query.queries.GetTurnoverOfMonthQuery;
import fr.cqrs.query.queries.Query;
import fr.cqrs.query.valueobjects.HistoryEntry;
import fr.cqrs.query.views.Turnover;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetTurnoverOfMonthQueryHandler implements QueryHandler<Turnover> {
  private QueryRepository queryRepository;

  public GetTurnoverOfMonthQueryHandler(QueryRepository queryRepository) {
    this.queryRepository = queryRepository;
  }

  @Override
  public List<Turnover> handle(Query query) {
    List<HistoryEntry> historyOfMonth = queryRepository.getHistoryOfMonth(((GetTurnoverOfMonthQuery) query).getYear(), ((GetTurnoverOfMonthQuery) query).getMonth());
    Money total = historyOfMonth.stream()
            .map(entry -> entry.getProduct().price)
            .reduce(Money.of(0), Money::add);
    return Collections.singletonList(Turnover.of(total, historyOfMonth));
  }
}
