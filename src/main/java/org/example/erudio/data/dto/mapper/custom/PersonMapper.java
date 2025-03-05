package org.example.erudio.data.dto.mapper.custom;

import org.example.erudio.data.dto.v2.PersonDtoV2;
import org.example.erudio.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PersonMapper {

    public PersonDtoV2 convertEntityToDto(Person person) {
        PersonDtoV2 personDto = new PersonDtoV2();
        personDto.setId(person.getId());
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setAddress(person.getAddress());
        personDto.setGender(person.getGender());
        personDto.setBirthDate(LocalDate.now());

        return personDto;
    }

    public Person convertDtoToEntity(PersonDtoV2 person) {
        Person entity = new Person();
        entity.setId(person.getId());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return entity;
    }

}
