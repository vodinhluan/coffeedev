package com.coffeedev.admin.customer;

import com.coffeedev.common.dto.CustomerDTO;
import com.coffeedev.common.entity.Customer;
import com.coffeedev.common.exception.CustomerNotFoundException;
import com.coffeedev.common.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> listAllCustomers() {
        List<Customer> customers = customerService.listAll();
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(CustomerMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        try {
            Customer customer = customerService.get(id);
            return ResponseEntity.ok(CustomerMapper.toDTO(customer));
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.toEntity(customerDTO);
        customerService.save(customer);
        return ResponseEntity.ok(CustomerMapper.toDTO(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer id, @RequestBody CustomerDTO customerDTO) {
        try {
            Customer existingCustomer = customerService.get(id);
            Customer updatedCustomer = CustomerMapper.updateEntity(existingCustomer, customerDTO);
            customerService.save(updatedCustomer);
            return ResponseEntity.ok(CustomerMapper.toDTO(updatedCustomer));
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        try {
            customerService.delete(id);
            return ResponseEntity.ok().build();
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
