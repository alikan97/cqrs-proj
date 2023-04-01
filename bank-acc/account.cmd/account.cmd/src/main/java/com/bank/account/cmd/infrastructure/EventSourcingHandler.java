package com.bank.account.cmd.infrastructure;

import com.bank.account.cmd.domain.AccountAggregate;
import com.bank.cqrs.core.domain.AggregateRoot;
import com.bank.cqrs.core.infrastructure.EventStore;
import com.bank.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class EventSourcingHandler implements com.bank.cqrs.core.handlers.EventSourcingHandler<AccountAggregate> {
    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommitedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }

        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();

        for (var aggregateId : aggregateIds){
            var aggregate = getById(aggregateId);
            if (aggregate == null || !aggregate.active) continue ;

            var events = eventStore.getEvents(aggregateId);

            for (var event : events) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}
