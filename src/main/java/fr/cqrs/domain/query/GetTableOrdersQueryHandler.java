package fr.cqrs.domain.query;

import fr.cqrs.domain.valueobjects.Product;
import fr.cqrs.infra.repositories.TableRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GetTableOrdersQueryHandler implements QueryHandler<Product> {
  private TableRepository tableRepository;

  public GetTableOrdersQueryHandler(TableRepository tableRepository) {
    this.tableRepository = tableRepository;
  }

  @Override
  public List<Product> handle(Query query) {
    return this.tableRepository
            .getByAggregateId(((GetTableOrdersQuery) query).getTableId()).getProducts()
            .entrySet()
            .stream()
            .map(product -> Collections.nCopies(product.getValue().getQuantity(), product.getKey()))
            .flatMap(List::stream)
            .collect(Collectors.toList());
  }
}
