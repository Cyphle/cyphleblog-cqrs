package fr.cqrs.command.valueobjects;

import java.util.Objects;

public class Money {
  private double value;

  private Money(double value) {
    this.value = value;
  }

  public static Money of(double value) {
    return new Money(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Money money = (Money) o;
    return Double.compare(money.value, value) == 0;
  }

  @Override
  public int hashCode() {

    return Objects.hash(value);
  }
}
