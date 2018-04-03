package fr.cqrs.domain.aggregate;

import fr.cqrs.domain.valueobjects.Id;
import fr.cqrs.domain.valueobjects.Name;

import java.util.Objects;

public class Table {
  private final Id id;
  private final Name clientName;

  private Table(Id id, Name clientName) {
    this.id = id;
    this.clientName = clientName;
  }

  public static Table of(Id id, Name clientName) {
    return new Table(id, clientName);
  }

  public boolean hasClient(Name clientName) {
    return this.clientName.equals(clientName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Table table = (Table) o;
    return Objects.equals(id, table.id) &&
            Objects.equals(clientName, table.clientName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, clientName);
  }
}
