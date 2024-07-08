package com.et.rmi.server.mapper;

import com.et.rmi.server.model.Customer;

import om.et.rmi.common.CustomerDTO;

public class CustomerMapper {

    public CustomerMapper() {
    }

    public CustomerDTO mapToDTO(Customer customer){
        CustomerDTO dto = new CustomerDTO();
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setSocialSecurityCode(customer.getSocialSecurityCode());
        return dto;
    }
}
