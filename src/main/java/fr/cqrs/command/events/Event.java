package fr.cqrs.command.events;

public interface Event<T> {
  String getAggregateId();

  T apply(T aggregate);
}
