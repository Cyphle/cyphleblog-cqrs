package fr.cqrs.query.views;

import fr.cqrs.command.valueobjects.Money;
import fr.cqrs.query.valueobjects.HistoryEntry;

import java.util.List;

public class Turnover {
  private Money total;
  private List<HistoryEntry> history;

  public Money getTotal() {
    return total;
  }

  public List<HistoryEntry> getHistory() {
    return history;
  }
}
