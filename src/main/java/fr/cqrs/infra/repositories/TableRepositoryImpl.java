package fr.cqrs.infra.repositories;

import fr.cqrs.domain.aggregate.Table;

import java.util.List;

public class TableRepositoryImpl implements TableRepository {
  public void save(Table table) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Table> getAllTables() {
    throw new UnsupportedOperationException();
  }
}
