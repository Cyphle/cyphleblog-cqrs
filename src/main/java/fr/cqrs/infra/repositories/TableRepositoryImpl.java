package fr.cqrs.infra.repositories;

import fr.cqrs.domain.aggregate.Table;
import fr.cqrs.domain.valueobjects.Id;

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

  @Override
  public Table getByAggregateId(Id id) {
    return this.tables
            .stream()
            .filter(t -> t.is(id))
            .findAny()
            .orElse(Table.UNOCCUPIED_TABLE);
  }
}
