package org.example.erudio.unittests.mapper.moks;

import org.example.erudio.data.dto.v1.BookDto;
import org.example.erudio.model.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookDto mockVO() {
        return mockVO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDto> mockVOList() {
        List<BookDto> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setId(number.longValue());
        book.setAuthor("Some Author" + number);
        book.setPublishDate(LocalDate.now());
        book.setPrice(25D);
        book.setTitle("Some Title" + number);
        return book;
    }

    public BookDto mockVO(Integer number) {
        BookDto book = new BookDto();
        book.setKey(number.longValue());
        book.setAuthor("Some Author" + number);
        book.setPublishDate(LocalDate.now());
        book.setPrice(25D);
        book.setTitle("Some Title" + number);
        return book;
    }

}
