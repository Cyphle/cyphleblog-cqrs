package fr.cqrs.infra.entities;

import fr.cqrs.command.events.Event;

import java.time.LocalDateTime;

public class StoredEvent {
  private String aggregateId;
  private final LocalDateTime timestamp;
  private final Event event;

  private StoredEvent(String aggregateId, LocalDateTime timestamp, Event event) {
    this.aggregateId = aggregateId;
    this.timestamp = timestamp;
    this.event = event;
  }

  public String getAggregateId() {
    return aggregateId;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public Event getEvent() {
    return event;
  }

  public static StoredEvent of(String aggregateId, LocalDateTime timestamp, Event event) {
    return new StoredEvent(aggregateId, timestamp, event);
  }
}
