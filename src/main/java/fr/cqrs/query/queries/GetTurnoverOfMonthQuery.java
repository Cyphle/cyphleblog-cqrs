package fr.cqrs.query.queries;

import java.time.Month;

public class GetTurnoverOfMonthQuery implements Query {
  private final int year;
  private final Month month;

  public GetTurnoverOfMonthQuery(int year, Month month) {
    this.year = year;
    this.month = month;
  }
}
