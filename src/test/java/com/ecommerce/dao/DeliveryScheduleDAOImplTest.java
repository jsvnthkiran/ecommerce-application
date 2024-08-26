package com.ecommerce.dao;

import com.ecommerce.exceptions.DeliveryScheduleNotFoundException;
import com.ecommerce.model.DeliverySchedule;
import com.ecommerce.util.DatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryScheduleDAOImplTest {

    private DeliveryScheduleDAOImpl deliveryScheduleDAO;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        // Set up database connection using DatabaseConnection factory class
        connection = DatabaseConnection.getConnection();
        // Initialize DAO
        deliveryScheduleDAO = new DeliveryScheduleDAOImpl(connection);
        // Set up schema and initial data
        setUpDatabase();
    }

    private void setUpDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create the table
            stmt.execute("CREATE TABLE delivery_schedule (id INT AUTO_INCREMENT PRIMARY KEY, subscription_id INT, delivery_date DATE)");
            // Insert sample data
            stmt.execute("INSERT INTO delivery_schedule (subscription_id, delivery_date) VALUES (1, '2024-08-24')");
        }
    }

    @Test
    public void testAddDeliverySchedule() throws SQLException {
        DeliverySchedule deliverySchedule = new DeliverySchedule(0, 2, Date.valueOf("2024-08-25"));
        deliveryScheduleDAO.addDeliverySchedule(deliverySchedule);

        // Retrieve the added schedule
        List<DeliverySchedule> schedules = deliveryScheduleDAO.getDeliverySchedulesBySubscriptionId(2);
        assertFalse(schedules.isEmpty());
        assertEquals(1, schedules.size());
        DeliverySchedule result = schedules.get(0);
        assertEquals(2, result.getSubscriptionId());
        assertEquals(Date.valueOf("2024-08-25"), result.getDeliveryDate());
    }

    @Test
    public void testGetDeliverySchedulesBySubscriptionId() throws SQLException {
        List<DeliverySchedule> schedules = deliveryScheduleDAO.getDeliverySchedulesBySubscriptionId(1);

        assertNotNull(schedules);
        assertFalse(schedules.isEmpty());
        assertEquals(1, schedules.size()); // Adjust based on your setup
        DeliverySchedule schedule = schedules.get(0);
        assertEquals(1, schedule.getSubscriptionId());
        assertEquals(Date.valueOf("2024-08-24"), schedule.getDeliveryDate());
    }

    @Test
    public void testUpdateDeliverySchedule() throws SQLException {
        DeliverySchedule deliverySchedule = new DeliverySchedule(1, 1, Date.valueOf("2024-08-26"));
        deliveryScheduleDAO.updateDeliverySchedule(deliverySchedule);

        List<DeliverySchedule> schedules = deliveryScheduleDAO.getDeliverySchedulesBySubscriptionId(1);
        assertNotNull(schedules);
        assertFalse(schedules.isEmpty());
        DeliverySchedule result = schedules.get(0);
        assertEquals(Date.valueOf("2024-08-26"), result.getDeliveryDate());
    }

    @Test
    public void testDeleteDeliverySchedule() throws SQLException {
        deliveryScheduleDAO.deleteDeliverySchedule(1);

        List<DeliverySchedule> schedules = deliveryScheduleDAO.getDeliverySchedulesBySubscriptionId(1);
        assertTrue(schedules.isEmpty());
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
