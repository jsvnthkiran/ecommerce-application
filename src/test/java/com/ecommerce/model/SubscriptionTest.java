package com.ecommerce.model;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionTest {

    @Test
    public void testSubscriptionConstructorAndGetters() {
        // Arrange: Create a Subscription object using the constructor
        Date startDate = Date.valueOf("2024-01-01");
        Date endDate = Date.valueOf("2024-12-31");
        Subscription subscription = new Subscription(1, 101, "Premium", true, startDate, endDate);

        // Act & Assert: Test that the getters return the correct values
        assertEquals(1, subscription.getId());
        assertEquals(101, subscription.getCustomerId());
        assertEquals("Premium", subscription.getType());
        assertTrue(subscription.isActive());
        assertEquals(startDate, subscription.getStartDate());
        assertEquals(endDate, subscription.getEndDate());
    }

    @Test
    public void testSetters() {
        // Arrange: Create a Subscription object
        Subscription subscription = new Subscription(0, 0, "", false, null, null);

        // Act: Set values using setters
        Date newStartDate = Date.valueOf("2024-02-01");
        Date newEndDate = Date.valueOf("2024-11-30");
        subscription.setId(2);
        subscription.setCustomerId(202);
        subscription.setType("Standard");
        subscription.setActive(true);
        subscription.setStartDate(newStartDate);
        subscription.setEndDate(newEndDate);

        // Assert: Test that the setters updated the values correctly
        assertEquals(2, subscription.getId());
        assertEquals(202, subscription.getCustomerId());
        assertEquals("Standard", subscription.getType());
        assertTrue(subscription.isActive());
        assertEquals(newStartDate, subscription.getStartDate());
        assertEquals(newEndDate, subscription.getEndDate());
    }

    @Test
    public void testToString() {
        // Arrange: Create a Subscription object
        Date startDate = Date.valueOf("2024-01-01");
        Date endDate = Date.valueOf("2024-12-31");
        Subscription subscription = new Subscription(3, 303, "Basic", false, startDate, endDate);

        // Act: Get the string representation of the object
        String subscriptionString = subscription.toString();

        // Assert: Check that the string representation is correct
        String expectedString = "Subscription{id=3, customerId=303, type='Basic', isActive=false, startDate=2024-01-01, endDate=2024-12-31}";
        assertEquals(expectedString, subscriptionString);
    }
}
