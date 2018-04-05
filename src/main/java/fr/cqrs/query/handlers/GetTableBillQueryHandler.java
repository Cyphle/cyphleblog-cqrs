package fr.cqrs.query.handlers;

import fr.cqrs.command.aggregate.Table;
import fr.cqrs.command.valueobjects.Bill;
import fr.cqrs.command.valueobjects.BillEntry;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.query.queries.GetTableBillQuery;
import fr.cqrs.query.queries.Query;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GetTableBillQueryHandler implements QueryHandler<Bill> {
  private TableRepository tableRepository;

  public GetTableBillQueryHandler(TableRepository tableRepository) {
    this.tableRepository = tableRepository;
  }

  @Override
  public List<Bill> handle(Query query) {
    Table table = this.tableRepository.getByAggregateId(((GetTableBillQuery) query).getTableId());
    if (!table.equals(Table.UNOCCUPIED_TABLE)) {
      return Collections.singletonList(Bill.of(table.getProducts()
              .entrySet()
              .stream()
              .map(product -> BillEntry.of(product.getKey(), product.getValue(), product.getKey().price))
              .collect(Collectors.toSet())));
    } else {
      return Collections.emptyList();
    }
  }
}
