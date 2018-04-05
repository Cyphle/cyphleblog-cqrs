package fr.cqrs.domain.valueobjects;

import java.util.Objects;

public class Id {
  private String id;

  private Id(String id) {
    this.id = id;
  }

  public static Id of(String id) {
    return new Id(id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Id id1 = (Id) o;
    return Objects.equals(id, id1.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Id{" +
            "id='" + id + '\'' +
            '}';
  }
}
