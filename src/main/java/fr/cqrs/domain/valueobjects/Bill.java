package fr.cqrs.domain.valueobjects;

import java.util.ArrayList;
import java.util.List;

public class Bill {
  private List<BillEntry> entries;

  public Bill() {
    this.entries = new ArrayList<>();
  }

  public List<BillEntry> getEntries() {
    return this.entries;
  }
}
