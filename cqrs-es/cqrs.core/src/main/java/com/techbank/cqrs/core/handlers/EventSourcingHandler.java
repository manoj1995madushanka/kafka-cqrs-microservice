package com.techbank.cqrs.core.handlers;

import com.techbank.cqrs.core.domain.AggregateRoot;

/*
 * this is responsible for retrieving all events for a given aggregate from the event
 * store and to invoke the replayEvents method on aggregateRoot to recreate the latest state of the aggregate
 * */
public interface EventSourcingHandler<T> {

    void save(AggregateRoot aggregate);

    T getById(String id);
}
