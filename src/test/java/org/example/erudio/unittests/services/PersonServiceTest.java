package org.example.erudio.unittests.services;

import org.example.erudio.data.dto.v1.Person2Dto;
import org.example.erudio.exceptions.RequiredObjectIsNullException;
import org.example.erudio.model.Person;
import org.example.erudio.repositories.PersonReopsitory;
import org.example.erudio.services.PersonService;
import org.example.erudio.unittests.mapper.moks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    PersonReopsitory reopsitory;

    @BeforeEach
    void setUp() throws Exception {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Person person = input.mockEntity(1);
        when(reopsitory.findById(1L)).thenReturn(Optional.of(person));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals(person.getAddress(), result.getAddress());
        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals( person.getLastName(), result.getLastName());
        assertEquals(person.getGender(), result.getGender());
    }

    @Test
    void create() {
        Person entity = input.mockEntity();
        Person persisted = new Person();
        persisted.setFirstName(entity.getFirstName());
        persisted.setLastName(entity.getLastName());
        persisted.setGender(entity.getGender());
        persisted.setAddress(entity.getAddress());
        persisted.setId(1L);

        Person2Dto dto = input.mockDto(1);
        dto.setKey(1L);

        when(reopsitory.save(any(Person.class))).thenReturn(persisted);

        var result = service.create(dto);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals(entity.getAddress(), result.getAddress());
        assertEquals(entity.getFirstName(), result.getFirstName());
        assertEquals( entity.getLastName(), result.getLastName());
        assertEquals(entity.getGender(), result.getGender());
    }

    @Test
    void createWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void update() {
        Person entity = input.mockEntity();
        entity.setId(1L);

        Person persisted = new Person();
        persisted.setFirstName(entity.getFirstName());
        persisted.setLastName(entity.getLastName());
        persisted.setGender(entity.getGender());
        persisted.setAddress(entity.getAddress());
        persisted.setId(1L);

        Person2Dto dto = input.mockDto();
        dto.setKey(1L);

        when(reopsitory.findById(1L)).thenReturn(Optional.of(entity));
        when(reopsitory.save(any(Person.class))).thenReturn(persisted);

        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals(entity.getAddress(), result.getAddress());
        assertEquals(entity.getFirstName(), result.getFirstName());
        assertEquals( entity.getLastName(), result.getLastName());
        assertEquals(entity.getGender(), result.getGender());
    }

    @Test
    void updateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });
        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void delete() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        when(reopsitory.findById(1L)).thenReturn(Optional.of(entity));
            service.delete(1L);


    }

    @Test
    void findAll() {
//        List<Person> persons = input.mockEntityList();
//        when(reopsitory.findAll()).thenReturn(persons);
//
//        var result = service.findAll();
//        assertNotNull(result);
//        assertEquals(result.size(), persons.size());
//        assertEquals(14, result.size());
    }


}
