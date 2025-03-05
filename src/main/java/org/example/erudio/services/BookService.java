package org.example.erudio.services;

import org.example.erudio.controllers.BookController;
import org.example.erudio.controllers.PersonController;
import org.example.erudio.data.dto.mapper.DozerMapper;
import org.example.erudio.data.dto.mapper.custom.PersonMapper;
import org.example.erudio.data.dto.v1.BookDto;
import org.example.erudio.exceptions.RequiredObjectIsNullException;
import org.example.erudio.exceptions.ResourceNotFound;
import org.example.erudio.model.Book;
import org.example.erudio.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PersonMapper mapper;

    @Autowired
    PagedResourcesAssembler<BookDto> assembler;

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public PagedModel<EntityModel<BookDto>> findAll(Pageable pageable) {
        logger.info("Getting all books");
        var bookPage = bookRepository.findAll(pageable);
        var bookPageDto = bookPage.map(book -> DozerMapper.parseObject(book, BookDto.class));
        bookPageDto.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        Link link = linkTo(methodOn(PersonController.class)
                .findAll(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        "asc"
                )).withSelfRel();
        var hatoesPageDto = assembler.toModel(bookPageDto, link);
        return hatoesPageDto;
    }

    public BookDto findById(Long id) {
        logger.info("Getting book by id {}", id);
        var book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Book not found"));
        var dto = DozerMapper.parseObject(book, BookDto.class);
        dto.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return dto;
    }

    public BookDto create(BookDto bookDto) {
        if (bookDto == null) throw new RequiredObjectIsNullException();;

        logger.info("Creating book {}", bookDto);
        var book = DozerMapper.parseObject(bookDto, Book.class);
        book = bookRepository.save(book);
        var dto = DozerMapper.parseObject(book, BookDto.class);
        dto.add(linkTo(methodOn(BookController.class).findById(book.getId())).withSelfRel());
        return dto;
    }

    public BookDto update(BookDto bookDto) {
        if (bookDto == null) throw new RequiredObjectIsNullException();;

        logger.info("Updating book {}", bookDto);
        var book = bookRepository.findById(bookDto.getKey()).orElseThrow(
                () -> new ResourceNotFound("Book not found")
        );

        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setPrice(bookDto.getPrice());
        book.setPublishDate(bookDto.getPublishDate());
        var dto = DozerMapper.parseObject(bookRepository.save(book), BookDto.class);
        dto.add(linkTo(methodOn(BookController.class).findById(bookDto.getKey())).withSelfRel());
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting book {}", id);
        bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Book not found")
        );
        bookRepository.deleteById(id);
    }
}
