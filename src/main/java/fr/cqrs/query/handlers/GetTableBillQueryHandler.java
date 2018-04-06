package fr.cqrs.query.handlers;

import fr.cqrs.command.valueobjects.Bill;
import fr.cqrs.infra.repositories.QueryRepository;
import fr.cqrs.query.queries.GetTableBillQuery;
import fr.cqrs.query.queries.Query;

import java.util.Collections;
import java.util.List;

public class GetTableBillQueryHandler implements QueryHandler<Bill> {
  private QueryRepository queryRepository;

  public GetTableBillQueryHandler(QueryRepository queryRepository) {
    this.queryRepository = queryRepository;
  }

  @Override
  public List<Bill> handle(Query query) {
    return Collections.singletonList(queryRepository.getBillOf(((GetTableBillQuery) query).getTableId()));
  }
}
