package fr.cqrs.infra.repositories;

import fr.cqrs.domain.aggregate.Table;

import java.util.List;

public interface TableRepository {
  void save(Table table);

  List<Table> getAllTables();
}
