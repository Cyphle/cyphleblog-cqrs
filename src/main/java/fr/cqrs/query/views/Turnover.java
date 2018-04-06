package fr.cqrs.query.views;

import fr.cqrs.command.valueobjects.Money;
import fr.cqrs.query.valueobjects.HistoryEntry;

import java.util.List;

public class Turnover {
  private Money total;
  private List<HistoryEntry> history;

  public Turnover(Money total, List<HistoryEntry> historyOfMonth) {
    this.total = total;
    history = historyOfMonth;
  }

  public Money getTotal() {
    return total;
  }

  public List<HistoryEntry> getHistory() {
    return history;
  }

  public static Turnover of(Money total, List<HistoryEntry> historyOfMonth) {
    return new Turnover(total, historyOfMonth);
  }
}
