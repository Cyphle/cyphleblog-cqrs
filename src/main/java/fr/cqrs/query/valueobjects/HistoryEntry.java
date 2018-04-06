package fr.cqrs.query.valueobjects;

import fr.cqrs.command.valueobjects.Product;

import java.time.Month;
import java.util.Objects;

public class HistoryEntry {
  private final int year;
  private final Month month;
  private final int day;
  private final Product product;
  private final String table;

  private HistoryEntry(int year, Month month, int day, Product product, String table) {
    this.year = year;
    this.month = month;
    this.day = day;
    this.product = product;
    this.table = table;
  }

  public static HistoryEntry of(int year, Month month, int day, Product product, String table) {
    return new HistoryEntry(year, month, day, product, table);
  }

  public Product getProduct() {
    return product;
  }

  public int getYear() {
    return year;
  }

  public Month getMonth() {
    return month;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HistoryEntry that = (HistoryEntry) o;
    return year == that.year &&
            day == that.day &&
            month == that.month &&
            product == that.product &&
            Objects.equals(table, that.table);
  }

  @Override
  public int hashCode() {

    return Objects.hash(year, month, day, product, table);
  }

  @Override
  public String toString() {
    return "HistoryEntry{" +
            "year=" + year +
            ", month=" + month +
            ", day=" + day +
            ", product=" + product +
            ", table='" + table + '\'' +
            '}';
  }
}
