package com.et.rmi.server.service;

import com.et.rmi.server.dao.CustomerRepository;

import com.et.rmi.server.mapper.CustomerMapper;
import com.et.rmi.server.model.Customer;
import om.et.rmi.common.CustomerDTO;
import om.et.rmi.common.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomerDTO getCustomer(long id) {
        Customer customer = repository.findById(id).orElseThrow(IllegalArgumentException::new);
        CustomerMapper mapper = new CustomerMapper();
        CustomerDTO dto = mapper.mapToDTO(customer);
        System.out.println(dto);
        return dto;
    }

    public List<Customer> getCustomers() {
        return (List<Customer>)repository.findAll();
    }
    public void saveCustomer(Customer customer) {
        repository.save(customer);
    }
}
