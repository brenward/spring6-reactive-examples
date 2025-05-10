package com.wardweb.spring6_reactive_examples.repositories;

import com.wardweb.spring6_reactive_examples.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {

    Person michael = Person.builder()
            .id(1)
            .firstName("Michael")
            .lastName("Ward")
            .build();

    Person sarah = Person.builder()
            .id(2)
            .firstName("Sarah")
            .lastName("Murphy")
            .build();

    Person joe = Person.builder()
            .id(3)
            .firstName("Joseph")
            .lastName("Roberts")
            .build();

    Person michelle = Person.builder()
            .id(4)
            .firstName("Michelle")
            .lastName("Spencer")
            .build();

    @Override
    public Mono<Person> getById(Integer id) {
        return Mono.just(michael);
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(michael, sarah, joe, michelle);
    }
}
