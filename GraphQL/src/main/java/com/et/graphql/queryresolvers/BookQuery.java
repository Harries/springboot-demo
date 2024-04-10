package com.et.graphql.queryresolvers;

import com.et.graphql.model.Author;
import com.et.graphql.model.Book;
import com.et.graphql.repository.AuthorRepository;
import com.et.graphql.repository.BookRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BookQuery implements GraphQLQueryResolver{

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

    public Iterable<Book> allBook(){
        return bookRepository.findAll();
    }

    public Book getBookByName(String name){
        return bookRepository.findBookByName(name);
    }

    public Iterable<Author> allAuthor(){
        return authorRepository.findAll();
    }

}
