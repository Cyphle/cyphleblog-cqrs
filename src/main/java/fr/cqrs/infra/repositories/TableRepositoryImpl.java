package fr.cqrs.infra.repositories;

import fr.cqrs.command.aggregate.Table;
import fr.cqrs.command.events.Event;
import fr.cqrs.command.valueobjects.Id;
import fr.cqrs.infra.entities.StoredEvent;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TableRepositoryImpl implements TableRepository {
  private List<StoredEvent> eventStore;
  private QueryRepository queryRepository;

  public TableRepositoryImpl(QueryRepository queryRepository) {
    this.queryRepository = queryRepository;
    this.eventStore = new ArrayList<>();
  }

  public void save(Table table) {
    table.getChanges()
            .forEach(change -> {
              eventStore.add(StoredEvent.of(change.getAggregateId(), LocalDateTime.of(2018, Month.FEBRUARY, 3, 15, 50, 12), change));
              queryRepository.apply(change);
            });
  }

  @Override
  public List<Table> getAllTables() {
    // Extract list of ids
    Set<Id> ids = eventStore.stream()
            .map(event -> Id.of(event.getAggregateId()))
            .collect(Collectors.toSet());

    // For each id, get list of events and rebuild category
    List<Table> tables = new ArrayList<>();
    ids.forEach(id -> {
      List<StoredEvent> eventsOfCurrentAggregate = eventStore
              .stream()
              .filter(event -> id.equals(Id.of(event.getAggregateId()))).sorted(Comparator.comparing(StoredEvent::getTimestamp))
              .collect(Collectors.toList());

      tables.add(Table.replay(eventsOfCurrentAggregate.stream().map(StoredEvent::getEvent).collect(Collectors.toList())));
    });

    return tables;
  }

  @Override
  public Table getByAggregateId(Id id) {
    return Table.replay(eventStore
            .stream()
            .filter(event -> id.equals(Id.of(event.getAggregateId())))
            .map(StoredEvent::getEvent)
            .collect(Collectors.toList())
    );
  }
}
