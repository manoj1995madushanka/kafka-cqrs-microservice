package com.techbank.account.cmd.domain;

import com.techbank.cqrs.core.events.EventModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * we use mongodb as our event store
 * */
@Repository
public interface EventStoreRepository extends MongoRepository<EventModel,String> {

    List<EventModel> findByAggregateIdentifier(String aggregateIdentifier);
}
