package com.et.jpa.repository;

import com.et.jpa.entity.User;
import org.springframework.data.repository.CrudRepository;

public  interface UserRepository extends CrudRepository<User, String> {

}