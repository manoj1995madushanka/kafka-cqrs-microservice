package com.techbank.cqrs.core.queries;

import com.techbank.cqrs.core.domain.BaseEntity;

import java.util.List;

/*
* here we are using mediator pattern
* */
@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {

    List<BaseEntity> handle(T query);
}
