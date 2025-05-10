package com.wardweb.spring6_reactive_examples.repositories;

import com.wardweb.spring6_reactive_examples.domain.Person;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

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

    @Test
    void testFluxBlockFirst(){
        Flux<Person> personFlux = personRepository.findAll();

        Person person = personFlux.blockFirst();

        System.out.println(person.toString());
    }

    @Test
    void testFluxSuscriber(){
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testFluxMap(){
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.map(Person::getFirstName).subscribe(System.out::println);
    }

    @Test
    void testFluxToList(){
        Flux<Person> personFlux = personRepository.findAll();

        Mono<List<Person>> personListMono = personFlux.collectList();

        personListMono.subscribe(list -> {
           list.forEach(person -> {
               System.out.println(person.toString());
           });
        });
    }

    @Test
    void testFilterOnName(){
        personRepository.findAll()
                .filter(person ->
                    person.getFirstName().equals("Joseph")
                ).subscribe(
                    person -> System.out.println(person.getLastName())
                );
    }

    @Test
    void testGetById(){
        Mono<Person> sarahMono = personRepository.findAll()
                .filter(person ->
                        person.getFirstName().equals("Sarah")
                ).next();

        sarahMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testFindPersonByIdNotFound(){
        Flux<Person> personFlux = personRepository.findAll();

        final Integer id = 9;

        Mono<Person> personMono = personFlux
                .filter(person ->
                        person.getId().equals(id))
                .single().doOnError(throwable -> {
                    System.out.println("Person not found in flux");
                    System.out.println(throwable.getMessage());
                });

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        }, throwable -> {
            System.out.println("Person not found");
            System.out.println(throwable.getMessage());
        });
    }

    @Test
    void testFindById_NotFound(){
        Mono<Person> personMono = personRepository.getById(9);

        assertFalse(personMono.hasElement().block());
    }

    @Test
    void testFindyId_Found() {
        Mono<Person> personMono = personRepository.getById(2);

        assertTrue(personMono.hasElement().block());
    }

    @Test
    void testFindyId_FoundStepVerifier() {
        Mono<Person> personMono = personRepository.getById(2);

        StepVerifier.create(personMono).expectNextCount(1).verifyComplete();

        personMono.subscribe(person -> {
           System.out.println(person.toString());
        });
    }

    @Test
    void testFindyId_NotFoundStepVerifier() {
        Mono<Person> personMono = personRepository.getById(9);

        StepVerifier.create(personMono).expectNextCount(0).verifyComplete();

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }
}