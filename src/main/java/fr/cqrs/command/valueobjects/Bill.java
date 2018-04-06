package fr.cqrs.command.valueobjects;

import fr.cqrs.common.Quantity;

import java.util.Map;

public class Bill {
  private Map<Product, BillEntry> entries;

  private Bill(Map<Product, BillEntry> entries) {
    this.entries = entries;
  }

  public Map<Product, BillEntry> getEntries() {
    return this.entries;
  }

  public void addProduct(Product product) {
    BillEntry billEntry = entries.get(product);
    if (billEntry != null)
      billEntry.increment();
    else
      entries.put(Product.BEER, BillEntry.of(product, Quantity.of(1), product.price));
  }

  public static Bill of(Map<Product, BillEntry> entries) {
    return new Bill(entries);
  }
}
