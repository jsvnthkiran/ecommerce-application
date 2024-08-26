package com.ecommerce.dao;

import com.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.model.Product;
import com.ecommerce.util.DatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDAOImplTest {

    private ProductDAOImpl productDAO;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        // Set up in-memory database connection using DatabaseConnection factory class
        connection = DatabaseConnection.getConnection();
        // Initialize DAO
        productDAO = new ProductDAOImpl(connection);
        // Set up schema and initial data
        setUpDatabase();
    }

    private void setUpDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create the table
            stmt.execute("CREATE TABLE product (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), price DOUBLE)");
            // Insert sample data
            stmt.execute("INSERT INTO product (name, price) VALUES ('Test Product', 10.99)");
        }
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product(0, "New Product", 20.99);
        productDAO.createProduct(product);

        // Retrieve the created product by ID
        Product result = productDAO.getProductById(2); // Adjust ID based on your setup
        assertNotNull(result);
        assertEquals("New Product", result.getName());
        assertEquals(20.99, result.getPrice());
    }

    @Test
    public void testGetProductById() {
        Product product = productDAO.getProductById(1);

        assertNotNull(product);
        assertEquals("Test Product", product.getName());
        assertEquals(10.99, product.getPrice());
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = productDAO.getAllProducts();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size()); // There should be one product
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product(1, "Updated Product", 15.99);
        productDAO.updateProduct(product);

        Product result = productDAO.getProductById(1);
        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        assertEquals(15.99, result.getPrice());
    }

    @Test
    public void testDeleteProduct() {
        productDAO.deleteProduct(1);

        assertThrows(ProductNotFoundException.class, () -> productDAO.getProductById(1));
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
