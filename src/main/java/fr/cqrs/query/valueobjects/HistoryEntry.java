package fr.cqrs.query.valueobjects;

import fr.cqrs.command.valueobjects.Product;

import java.time.Month;

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
}
