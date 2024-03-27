package com.et.hikari.repository;


import com.et.hikari.entity.User;
import org.springframework.data.repository.CrudRepository;

public  interface UserRepository extends CrudRepository<User, String> {

}