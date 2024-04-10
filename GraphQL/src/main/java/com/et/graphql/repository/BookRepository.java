package com.et.graphql.repository;


import com.et.graphql.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {

    Book findBookByName(String name);
}
