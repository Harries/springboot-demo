package com.et.vaadin.repository;

import com.et.vaadin.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName CustomerRepository
 * @Description todo
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByLastNameStartsWithIgnoreCase(String lastName);
}
