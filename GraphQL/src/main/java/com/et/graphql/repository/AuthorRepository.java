package com.et.graphql.repository;

import com.et.graphql.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Integer> {

    Author findAuthorByBookId(Integer bookId);
}