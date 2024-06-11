package com.et.vaadin.repository;

import com.et.vaadin.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author liuhaihua
 * @version 1.0
 * @ClassName CustomerRepository
 * @Description todo
 * @date 2024年06月11日 10:07
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByLastNameStartsWithIgnoreCase(String lastName);
}
