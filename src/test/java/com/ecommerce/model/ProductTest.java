package com.ecommerce.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void testProductConstructorAndGetters() {
        // Arrange: Create a Product object using the constructor
        Product product = new Product(1, "Apple", 1.99);

        // Act & Assert: Test that the getters return the correct values
        assertEquals(1, product.getId());
        assertEquals("Apple", product.getName());
        assertEquals(1.99, product.getPrice(), 0.01);
    }

    @Test
    public void testSetters() {
        // Arrange: Create a Product object
        Product product = new Product(0, "", 0.0);

        // Act: Set values using setters
        product.setId(2);
        product.setName("Banana");
        product.setPrice(0.99);

        // Assert: Test that the setters updated the values correctly
        assertEquals(2, product.getId());
        assertEquals("Banana", product.getName());
        assertEquals(0.99, product.getPrice(), 0.01);
    }

    @Test
    public void testToString() {
        // Arrange: Create a Product object
        Product product = new Product(3, "Orange", 0.75);

        // Act: Get the string representation of the object
        String productString = product.toString();

        // Assert: Check that the string representation is correct
        String expectedString = "Product{id=3, name='Orange', price=0.75}";
        assertEquals(expectedString, productString);
    }
}
