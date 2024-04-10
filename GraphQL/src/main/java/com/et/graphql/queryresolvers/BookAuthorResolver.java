package com.et.graphql.queryresolvers;


import com.et.graphql.model.Author;
import com.et.graphql.model.Book;
import com.et.graphql.repository.AuthorRepository;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookAuthorResolver implements GraphQLResolver<Book> {

    @Autowired
    AuthorRepository authorRepository;

    public Author getAuthor(Book book){

        return authorRepository.findAuthorByBookId(book.getId());
    }
}
