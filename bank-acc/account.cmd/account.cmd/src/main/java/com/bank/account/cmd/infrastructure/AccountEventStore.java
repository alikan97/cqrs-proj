package com.bank.account.cmd.infrastructure;

import com.bank.account.cmd.domain.AccountAggregate;
import com.bank.account.cmd.domain.EventStoreRepository;
import com.bank.account.common.dto.AccountTypes;
import com.bank.cqrs.core.events.BaseEvent;
import com.bank.cqrs.core.events.EventModel;
import com.bank.cqrs.core.exceptions.AggregateNotFoundException;
import com.bank.cqrs.core.exceptions.ConcurrencyException;
import com.bank.cqrs.core.infrastructure.EventStore;
import com.bank.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {
    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateId(aggregateId);

        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }

        int version = expectedVersion;
        for (var event : events) {
            version ++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timestamp(new Date())
                    .aggregateId(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();

            var persistedEvent = eventStoreRepository.save(eventModel);

            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateId(aggregateId);

        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect id!");
        }
        return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }

    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();

        if (eventStream == null || eventStream.isEmpty()) {
            throw new IllegalStateException("Could not retrieve event stream from event store");
        }

        return eventStream.stream().map(EventModel::getAggregateId).distinct().collect(Collectors.toList());
    }
}
