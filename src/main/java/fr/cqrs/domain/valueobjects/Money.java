package fr.cqrs.domain.valueobjects;

public class Money {
  private int value;

  private Money(int value) {
    this.value = value;
  }

  public static Money of(int value) {
    return new Money(value);
  }
}
