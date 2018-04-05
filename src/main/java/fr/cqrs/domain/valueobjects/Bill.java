package fr.cqrs.domain.valueobjects;

import java.util.Set;

public class Bill {
  private Set<BillEntry> entries;

  private Bill(Set<BillEntry> entries) {
    this.entries = entries;
  }

  public Set<BillEntry> getEntries() {
    return this.entries;
  }

  public static Bill of(Set<BillEntry> entries) {
    return new Bill(entries);
  }
}
