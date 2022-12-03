package com.techbank.account.cmd.infrastructure;

import com.techbank.account.cmd.domain.AccountAggregate;
import com.techbank.cqrs.core.domain.AggregateRoot;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.handlers.EventSourcingHandler;
import com.techbank.cqrs.core.infrastructure.EventStore;
import com.techbank.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler {

    @Autowired
    private EventStore eventStore;
    @Autowired
    private EventProducer eventProducer;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        AccountAggregate aggregate = new AccountAggregate();
        List<BaseEvent> events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            Optional<Integer> latestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

    /*
    * republish only active events
    * */
    @Override
    public void republishEvents() {
        List<String> aggregateIds = eventStore.getAggregateIds();
        for (String aggregateId : aggregateIds) {
            AccountAggregate aggregate = getById(aggregateId);
            if (aggregate == null || !aggregate.getActive()) {
                continue;
            }
            List<BaseEvent> events = eventStore.getEvents(aggregateId);
            for (BaseEvent event : events) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}
