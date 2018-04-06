package fr.cqrs.query.handlers;

import fr.cqrs.command.exceptions.TableNotFoundException;
import fr.cqrs.command.valueobjects.Product;
import fr.cqrs.infra.repositories.QueryRepository;
import fr.cqrs.infra.repositories.TableRepository;
import fr.cqrs.query.queries.GetTableOrdersQuery;
import fr.cqrs.query.queries.Query;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GetTableOrdersQueryHandler implements QueryHandler<Product> {
  private QueryRepository queryRepository;

  public GetTableOrdersQueryHandler(QueryRepository queryRepository) {
    this.queryRepository = queryRepository;
  }

  @Override
  public List<Product> handle(Query query) throws TableNotFoundException {
    return queryRepository.getTableOrders(((GetTableOrdersQuery) query).getTableId())
            .entrySet()
            .stream()
            .map(product -> Collections.nCopies(product.getValue().getQuantity(), product.getKey()))
            .flatMap(List::stream)
            .collect(Collectors.toList());
  }
}
