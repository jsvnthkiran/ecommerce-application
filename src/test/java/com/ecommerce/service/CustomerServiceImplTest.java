package com.ecommerce.service;

import com.ecommerce.dao.CustomerDAO;
import com.ecommerce.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceImplTest {

    private CustomerServiceImpl customerService;
    private CustomerDAOStub customerDAOStub;

    @BeforeEach
    void setUp() {
        customerDAOStub = new CustomerDAOStub();
        customerService = new CustomerServiceImpl(customerDAOStub);
    }

    @Test
    void testAddCustomer() {
        Customer customer = new Customer(1, "John Doe", "john@example.com", "password123");

        customerService.addCustomer(customer);

        assertEquals(1, customerDAOStub.customers.size());
        assertEquals(customer, customerDAOStub.customers.get(0));
    }

    @Test
    void testGetCustomerByEmail() {
        Customer customer = new Customer(1, "John Doe", "john@example.com", "password123");
        customerDAOStub.customers.add(customer);

        Customer retrievedCustomer = customerService.getCustomerByEmail("john@example.com");

        assertNotNull(retrievedCustomer);
        assertEquals(customer, retrievedCustomer);
    }

    @Test
    void testGetCustomerById() {
        Customer customer = new Customer(1, "John Doe", "john@example.com", "password123");
        customerDAOStub.customers.add(customer);

        Customer retrievedCustomer = customerService.getCustomer(1);

        assertNotNull(retrievedCustomer);
        assertEquals(customer, retrievedCustomer);
    }

    @Test
    void testGetAllCustomers() {
        Customer customer1 = new Customer(1, "John Doe", "john@example.com", "password123");
        Customer customer2 = new Customer(2, "Jane Doe", "jane@example.com", "password123");
        customerDAOStub.customers.add(customer1);
        customerDAOStub.customers.add(customer2);

        List<Customer> customers = customerService.getAllCustomers();

        assertEquals(2, customers.size());
        assertTrue(customers.contains(customer1));
        assertTrue(customers.contains(customer2));
    }

    // Stub class for CustomerDAO
    static class CustomerDAOStub implements CustomerDAO {

        List<Customer> customers = new ArrayList<>();

        @Override
        public void createCustomer(Customer customer) {
            customers.add(customer);
        }

        @Override
        public Customer getCustomerByEmail(String email) {
            return customers.stream()
                    .filter(c -> c.getEmail().equals(email))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public Customer getCustomerById(int customerId) {
            return customers.stream()
                    .filter(c -> c.getId() == customerId)
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public List<Customer> getAllCustomers() {
            return new ArrayList<>(customers);
        }
    }
}
