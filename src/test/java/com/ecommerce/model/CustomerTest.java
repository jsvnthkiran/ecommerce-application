package com.ecommerce.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void testCustomerConstructorAndGetters() {
        // Arrange: Create a Customer object using the constructor
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", "password123");

        // Act & Assert: Test that the getters return the correct values
        assertEquals(1, customer.getId());
        assertEquals("John Doe", customer.getName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals("password123", customer.getPassword());
    }

    @Test
    public void testSetters() {
        // Arrange: Create a Customer object
        Customer customer = new Customer(0, null, null, null);

        // Act: Set values using setters
        customer.setId(2);
        customer.setName("Jane Smith");
        customer.setEmail("jane.smith@example.com");
        customer.setPassword("password456");

        // Assert: Test that the setters updated the values correctly
        assertEquals(2, customer.getId());
        assertEquals("Jane Smith", customer.getName());
        assertEquals("jane.smith@example.com", customer.getEmail());
        assertEquals("password456", customer.getPassword());
    }

    @Test
    public void testToString() {
        // Arrange: Create a Customer object
        Customer customer = new Customer(3, "Alice Brown", "alice.brown@example.com", "password789");

        // Act: Get the string representation of the object
        String customerString = customer.toString();

        // Assert: Check that the string representation is correct
        String expectedString = "Customer{id=3, name='Alice Brown', email='alice.brown@example.com', password='password789'}";
        assertEquals(expectedString, customerString);
    }
}

