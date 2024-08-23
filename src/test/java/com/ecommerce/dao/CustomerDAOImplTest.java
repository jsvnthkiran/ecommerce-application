package com.ecommerce.dao;

import com.ecommerce.exceptions.CustomerNotFoundException;
import com.ecommerce.model.Customer;
import com.ecommerce.util.DatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDAOImplTest {

    private CustomerDAOImpl customerDAO;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        // Set up database connection using DatabaseConnection factory class
        connection = DatabaseConnection.getConnection();
        // Initialize DAO
        customerDAO = new CustomerDAOImpl(connection);
        // Set up schema and initial data
        setUpDatabase();
    }

    private void setUpDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create the table
            stmt.execute("CREATE TABLE customer (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), email VARCHAR(255) UNIQUE, password VARCHAR(255))");
            // Insert sample data
            stmt.execute("INSERT INTO customer (name, email, password) VALUES ('John Doe', 'john.doe@example.com', 'password123')");
        }
    }

    @Test
    public void testCreateCustomer() throws SQLException {
        Customer customer = new Customer(0, "Jane Smith", "jane.smith@example.com", "password456");
        customerDAO.createCustomer(customer);

        // Retrieve the customer
        Customer result = customerDAO.getCustomerByEmail("jane.smith@example.com");
        assertNotNull(result);
        assertEquals("Jane Smith", result.getName());
        assertEquals("jane.smith@example.com", result.getEmail());
        assertEquals("password456", result.getPassword());
    }

    @Test
    public void testGetCustomerByEmail() throws SQLException {
        Customer customer = customerDAO.getCustomerByEmail("john.doe@example.com");
        assertNotNull(customer);
        assertEquals("John Doe", customer.getName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals("password123", customer.getPassword());
    }

    @Test
    public void testGetCustomerById() throws SQLException {
        Customer customer = customerDAO.getCustomerById(1); // Adjust the ID if necessary
        assertNotNull(customer);
        assertEquals("John Doe", customer.getName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals("password123", customer.getPassword());
    }

    @Test
    public void testGetAllCustomers() throws SQLException {
        List<Customer> customers = customerDAO.getAllCustomers();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertEquals(1, customers.size()); // Adjust based on initial data setup
        Customer customer = customers.get(0);
        assertEquals("John Doe", customer.getName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals("password123", customer.getPassword());
    }

    @Test
    public void testCustomerNotFoundByEmail() {
        assertThrows(CustomerNotFoundException.class, () -> {
            customerDAO.getCustomerByEmail("nonexistent@example.com");
        });
    }

    @Test
    public void testCustomerNotFoundById() {
        assertThrows(CustomerNotFoundException.class, () -> {
            customerDAO.getCustomerById(999); // Use a non-existent ID
        });
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
