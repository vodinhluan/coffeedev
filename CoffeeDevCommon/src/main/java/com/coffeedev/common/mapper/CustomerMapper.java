package com.coffeedev.common.mapper;

import com.coffeedev.common.dto.CustomerDTO;
import com.coffeedev.common.entity.AuthenticationType;
import com.coffeedev.common.entity.Customer;

public class CustomerMapper {

    public static CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setEmail(customer.getEmail());
        dto.setName(customer.getName());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setAddress(customer.getAddress());
        dto.setEnabled(customer.isEnabled());
        dto.setCreatedTime(customer.getCreatedTime());

        if (customer.getAuthenticationType() != null) {
            dto.setAuthenticationType(customer.getAuthenticationType().name());
        }

        if (customer.getDistrict() != null) {
            dto.setDistrictId(customer.getDistrict().getId());
            dto.setDistrictName(customer.getDistrict().getName());
        }

        return dto;
    }

    public static Customer toEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());  // Cẩn thận: ID thường không được set khi tạo mới
        customer.setEmail(dto.getEmail());
        customer.setName(dto.getName());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setAddress(dto.getAddress());
        customer.setEnabled(dto.isEnabled());

        if (dto.getAuthenticationType() != null) {
            customer.setAuthenticationType(AuthenticationType.valueOf(dto.getAuthenticationType()));
        }

        return customer;
    }

    public static Customer updateEntity(Customer existingCustomer, CustomerDTO dto) {
        existingCustomer.setEmail(dto.getEmail());
        existingCustomer.setName(dto.getName());
        existingCustomer.setPhoneNumber(dto.getPhoneNumber());
        existingCustomer.setAddress(dto.getAddress());
        existingCustomer.setEnabled(dto.isEnabled());

        if (dto.getAuthenticationType() != null) {
            existingCustomer.setAuthenticationType(AuthenticationType.valueOf(dto.getAuthenticationType()));
        }

        return existingCustomer;
    }
}

