package com.wardweb.spring6_reactive_examples.repositories;

import com.wardweb.spring6_reactive_examples.domain.Person;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {

    PersonRepository personRepository = new PersonRepositoryImpl();

    @Test
    void testMonoBtIdBlock(){
        Mono<Person> personMono = personRepository.getById(1);

        Person person = personMono.block();

        System.out.println(person.toString());
    }

    @Test
    void testMonoBtIdSubscriber(){
        Mono<Person> personMono = personRepository.getById(1);

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testMapOperation(){
        Mono<Person> personMono = personRepository.getById(1);

        personMono.map(Person::getFirstName).subscribe(System.out::println);
    }
}