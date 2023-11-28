package com.QuartzSpringApplication.QuartzSpringApplication.repository;

import com.QuartzSpringApplication.QuartzSpringApplication.models.Book;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookRepository {
    private final List<com.QuartzSpringApplication.QuartzSpringApplication.models.Book> books = new ArrayList<>();
    public List<Book> getAllBooks(){
        return books;
    }
    public void addBook(com.QuartzSpringApplication.QuartzSpringApplication.models.Book book){
        books.add(book);
    }
    public int getBooksCount(){
        return books.size();
    }
}
