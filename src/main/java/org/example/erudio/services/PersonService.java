package org.example.erudio.services;

import org.example.erudio.controllers.PersonController;
import org.example.erudio.data.dto.mapper.DozerMapper;
import org.example.erudio.data.dto.mapper.custom.PersonMapper;
import org.example.erudio.data.dto.v1.Person2Dto;
import org.example.erudio.data.dto.v2.PersonDtoV2;
import org.example.erudio.exceptions.RequiredObjectIsNullException;
import org.example.erudio.exceptions.ResourceNotFound;
import org.example.erudio.model.Person;
import org.example.erudio.repositories.PersonReopsitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonService {

    @Autowired
    private PersonReopsitory personReopsitory;

    @Autowired
    PagedResourcesAssembler<Person2Dto> assembler;

    @Autowired
    private PersonMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public Person2Dto findById(Long id) {
        logger.info("Find one person");
        var entity = personReopsitory.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Person not found"));

        Person2Dto dto = DozerMapper.parseObject(entity, Person2Dto.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return dto;
    }

    public PagedModel<EntityModel<Person2Dto>> findAll(Pageable pageable) {
        logger.info("Find people");
        var personPage = personReopsitory.findAll(pageable);
        var personsDtoPage = personPage.map(person -> DozerMapper.parseObject(person, Person2Dto.class));
        personsDtoPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        Link link = linkTo(methodOn(PersonController.class)
                .findAll(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        "asc"
                        )).withSelfRel();
        var hatoesPageDto = assembler.toModel(personsDtoPage, link);
        return hatoesPageDto;
    }

    public Person2Dto create(Person2Dto person) {
        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Create person");
        var entity = DozerMapper.parseObject(person, Person.class);
        Person2Dto dto = DozerMapper.parseObject(personReopsitory.save(entity), Person2Dto.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public PersonDtoV2 createV2(PersonDtoV2 person) {
        logger.info("Create person");
        var entity = mapper.convertDtoToEntity(person);
        return mapper.convertEntityToDto(personReopsitory.save(entity));
    }

    public Person2Dto update(Person2Dto person) {
        if (person == null) throw new RequiredObjectIsNullException();

        logger.info("Update person");
        var entity = personReopsitory.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFound("Person not found"));
        entity.setGender(person.getGender());
        entity.setAddress(person.getAddress());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());

        Person2Dto dto = DozerMapper.parseObject(personReopsitory.save(entity), Person2Dto.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(person.getKey())).withSelfRel());
        return dto;
    }

    public void delete(Long id) {
        logger.info("Delete person");
        var entity = personReopsitory.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Person not found"));
        personReopsitory.deleteById(id);
    }
}
