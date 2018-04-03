package fr.cqrs.infra.repositories;

import fr.cqrs.domain.aggregate.Table;

public interface TableRepository {
  void save(Table table);
}
