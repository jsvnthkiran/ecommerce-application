package com.ecommerce.model;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryScheduleTest {

    @Test
    public void testDeliveryScheduleConstructorAndGetters() {
        // Arrange: Create a DeliverySchedule object using the constructor
        Date deliveryDate = Date.valueOf("2023-08-25");
        DeliverySchedule deliverySchedule = new DeliverySchedule(1, 101, deliveryDate);

        // Act & Assert: Test that the getters return the correct values
        assertEquals(1, deliverySchedule.getId());
        assertEquals(101, deliverySchedule.getSubscriptionId());
        assertEquals(deliveryDate, deliverySchedule.getDeliveryDate());
    }

    @Test
    public void testSetters() {
        // Arrange: Create a DeliverySchedule object
        DeliverySchedule deliverySchedule = new DeliverySchedule(0, 0, null);

        // Act: Set values using setters
        deliverySchedule.setId(2);
        deliverySchedule.setSubscriptionId(102);
        Date newDeliveryDate = Date.valueOf("2023-08-26");
        deliverySchedule.setDeliveryDate(newDeliveryDate);

        // Assert: Test that the setters updated the values correctly
        assertEquals(2, deliverySchedule.getId());
        assertEquals(102, deliverySchedule.getSubscriptionId());
        assertEquals(newDeliveryDate, deliverySchedule.getDeliveryDate());
    }

    @Test
    public void testToString() {
        // Arrange: Create a DeliverySchedule object
        Date deliveryDate = Date.valueOf("2023-08-27");
        DeliverySchedule deliverySchedule = new DeliverySchedule(3, 103, deliveryDate);

        // Act: Get the string representation of the object
        String deliveryScheduleString = deliverySchedule.toString();

        // Assert: Check that the string representation is correct
        String expectedString = "DeliverySchedule{id=3, subscriptionId=103, deliveryDate=2023-08-27}";
        assertEquals(expectedString, deliveryScheduleString);
    }
}
