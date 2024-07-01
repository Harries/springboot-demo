package com.et.gemfire.repository;

import com.et.gemfire.entity.People;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<People, String> {}
