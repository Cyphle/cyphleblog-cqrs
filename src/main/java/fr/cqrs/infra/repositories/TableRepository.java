package fr.cqrs.infra.repositories;

import fr.cqrs.domain.aggregate.Table;
import fr.cqrs.domain.valueobjects.Id;

import java.util.List;

public interface TableRepository {
  void save(Table table);

  List<Table> getAllTables();

  Table getByAggregateId(Id id);
}
