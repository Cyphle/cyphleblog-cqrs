package fr.cqrs.infra.repositories;

import fr.cqrs.domain.aggregate.Table;

import java.util.ArrayList;
import java.util.List;

public class TableRepositoryImpl implements TableRepository {
  private List<Table> tables;

  public TableRepositoryImpl() {
    this.tables = new ArrayList<>();
  }

  public void save(Table table) {
    this.tables.add(table);
  }

  @Override
  public List<Table> getAllTables() {
    return this.tables;
  }
}
