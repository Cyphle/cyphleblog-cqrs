package fr.cqrs.domain.query;

import fr.cqrs.domain.valueobjects.Bill;

import java.util.List;

public class GetTableBillQueryHandler implements QueryHandler<Bill> {
  @Override
  public List<Bill> handle(Query query) {
    throw new UnsupportedOperationException();
  }
}
