package com.ecommerce.dao;

import com.ecommerce.model.Subscription;
import com.ecommerce.util.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionDAOImplTest {

    private SubscriptionDAOImpl subscriptionDAO;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        // Set up in-memory database connection
        connection = DatabaseConnection.getConnection();
        // Initialize DAO
        subscriptionDAO = new SubscriptionDAOImpl(connection);
        // Set up schema and initial data
        setUpDatabase();
    }

    private void setUpDatabase() throws SQLException {
        try (var stmt = connection.createStatement()) {
            // Create the table
            stmt.execute("CREATE TABLE subscription (id INT AUTO_INCREMENT PRIMARY KEY, customer_id INT, type VARCHAR(255), is_active BOOLEAN, start_date DATE, end_date DATE)");
            // Insert sample data
            stmt.execute("INSERT INTO subscription (customer_id, type, is_active, start_date, end_date) VALUES (1, 'Basic', true, '2024-01-01', '2024-12-31')");
        }
    }

    @Test
    public void testAddSubscription() throws SQLException {
        // Define a subscription to add
        Subscription subscription = new Subscription(0, 2, "Premium", true, new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis() + 86400000L));
        subscriptionDAO.addSubscription(subscription);

        // Verify the subscription was added
        Subscription result = subscriptionDAO.getSubscriptionById(2);
        assertNotNull(result);
        assertEquals(2, result.getCustomerId());
        assertEquals("Premium", result.getType());
    }

    @Test
    public void testGetSubscriptionById() throws SQLException {
        // Retrieve a subscription by ID
        Subscription subscription = subscriptionDAO.getSubscriptionById(1);

        // Verify the retrieved subscription
        assertNotNull(subscription);
        assertEquals(1, subscription.getCustomerId());
        assertEquals("Basic", subscription.getType());
    }

    @Test
    public void testGetSubscriptionsByCustomerId() throws SQLException {
        // Retrieve subscriptions by customer ID
        List<Subscription> subscriptions = subscriptionDAO.getSubscriptionsByCustomerId(1);

        // Verify the retrieved subscriptions
        assertNotNull(subscriptions);
        assertFalse(subscriptions.isEmpty());
        assertEquals(1, subscriptions.size());
    }

    @Test
    public void testUpdateSubscription() throws SQLException {
        // Define a subscription to update
        Subscription subscription = new Subscription(1, 1, "Updated", false, new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis() + 86400000L));
        subscriptionDAO.updateSubscription(subscription);

        // Retrieve and verify the updated subscription
        Subscription result = subscriptionDAO.getSubscriptionById(1);
        assertNotNull(result);
        assertEquals("Updated", result.getType());
        assertFalse(result.isActive());
    }

    @Test
    public void testDeleteSubscription() throws SQLException {
        // Delete a subscription
        subscriptionDAO.deleteSubscription(1);

        // Verify the subscription was deleted
        Subscription result = subscriptionDAO.getSubscriptionById(1);
        assertNull(result);
    }
}
