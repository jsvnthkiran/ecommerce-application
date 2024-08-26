package com.ecommerce.service;

import com.ecommerce.dao.SubscriptionDAO;
import com.ecommerce.model.Subscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionServiceImplTest {

    private SubscriptionServiceImpl subscriptionService;
    private SubscriptionDAOStub subscriptionDAOStub;

    @BeforeEach
    void setUp() {
        subscriptionDAOStub = new SubscriptionDAOStub();
        subscriptionService = new SubscriptionServiceImpl(subscriptionDAOStub);
    }

    @Test
    void testAddSubscription() throws SQLException {
        Subscription subscription = new Subscription(1, 1, "Basic Plan", true, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        subscriptionService.addSubscription(subscription);

        assertEquals(1, subscriptionDAOStub.subscriptions.size());
        assertEquals(subscription, subscriptionDAOStub.subscriptions.get(0));
    }

    @Test
    void testGetSubscriptionById() throws SQLException {
        Subscription subscription = new Subscription(1, 1, "Basic Plan", true, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        subscriptionDAOStub.subscriptions.add(subscription);

        Subscription retrievedSubscription = subscriptionService.getSubscriptionById(1);

        assertEquals(subscription, retrievedSubscription);
    }

    @Test
    void testGetSubscriptionsByCustomerId() throws SQLException {
        Subscription subscription1 = new Subscription(1, 1, "Basic Plan", true, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        Subscription subscription2 = new Subscription(2, 1, "Premium Plan", false, Date.valueOf("2024-02-01"), Date.valueOf("2024-11-30"));
        subscriptionDAOStub.subscriptions.add(subscription1);
        subscriptionDAOStub.subscriptions.add(subscription2);

        List<Subscription> retrievedSubscriptions = subscriptionService.getSubscriptionsByCustomerId(1);

        assertEquals(2, retrievedSubscriptions.size());
        assertTrue(retrievedSubscriptions.contains(subscription1));
        assertTrue(retrievedSubscriptions.contains(subscription2));
    }

    @Test
    void testGetAllSubscriptions() throws SQLException {
        Subscription subscription1 = new Subscription(1, 1, "Basic Plan", true, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        Subscription subscription2 = new Subscription(2, 2, "Premium Plan", false, Date.valueOf("2024-02-01"), Date.valueOf("2024-11-30"));
        subscriptionDAOStub.subscriptions.add(subscription1);
        subscriptionDAOStub.subscriptions.add(subscription2);

        List<Subscription> retrievedSubscriptions = subscriptionService.getAllSubscriptions();

        assertEquals(2, retrievedSubscriptions.size());
        assertTrue(retrievedSubscriptions.contains(subscription1));
        assertTrue(retrievedSubscriptions.contains(subscription2));
    }

    @Test
    void testUpdateSubscription() throws SQLException {
        Subscription subscription = new Subscription(1, 1, "Basic Plan", true, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        subscriptionDAOStub.subscriptions.add(subscription);

        Subscription updatedSubscription = new Subscription(1, 1, "Updated Plan", false, Date.valueOf("2024-01-15"), Date.valueOf("2024-11-15"));
        subscriptionService.updateSubscription(updatedSubscription);

        Subscription retrievedSubscription = subscriptionDAOStub.subscriptions.get(0);
        assertEquals(updatedSubscription, retrievedSubscription);
    }

    @Test
    void testDeleteSubscription() throws SQLException {
        Subscription subscription = new Subscription(1, 1, "Basic Plan", true, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        subscriptionDAOStub.subscriptions.add(subscription);

        subscriptionService.deleteSubscription(1);

        assertTrue(subscriptionDAOStub.subscriptions.isEmpty());
    }

    @Test
    void testAddSubscriptionNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            subscriptionService.addSubscription(null);
        });
        assertEquals("Subscription cannot be null", exception.getMessage());
    }

    @Test
    void testGetSubscriptionByIdInvalidId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            subscriptionService.getSubscriptionById(-1);
        });
        assertEquals("Subscription ID must be positive", exception.getMessage());
    }

    @Test
    void testGetSubscriptionsByCustomerIdInvalidId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            subscriptionService.getSubscriptionsByCustomerId(-1);
        });
        assertEquals("Customer ID must be positive", exception.getMessage());
    }

    @Test
    void testUpdateSubscriptionNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            subscriptionService.updateSubscription(null);
        });
        assertEquals("Subscription cannot be null", exception.getMessage());
    }

    @Test
    void testUpdateSubscriptionInvalidId() {
        Subscription subscription = new Subscription(0, 1, "Basic Plan", true, Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            subscriptionService.updateSubscription(subscription);
        });
        assertEquals("Subscription ID must be positive", exception.getMessage());
    }

    @Test
    void testDeleteSubscriptionInvalidId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            subscriptionService.deleteSubscription(-1);
        });
        assertEquals("Subscription ID must be positive", exception.getMessage());
    }

    // Stub class for SubscriptionDAO
    static class SubscriptionDAOStub implements SubscriptionDAO {

        List<Subscription> subscriptions = new ArrayList<>();

        @Override
        public void addSubscription(Subscription subscription) throws SQLException {
            subscriptions.add(subscription);
        }

        @Override
        public Subscription getSubscriptionById(int id) throws SQLException {
            for (Subscription subscription : subscriptions) {
                if (subscription.getId() == id) {
                    return subscription;
                }
            }
            return null; // Or throw an exception if you prefer
        }

        @Override
        public List<Subscription> getSubscriptionsByCustomerId(int customerId) throws SQLException {
            List<Subscription> result = new ArrayList<>();
            for (Subscription subscription : subscriptions) {
                if (subscription.getCustomerId() == customerId) {
                    result.add(subscription);
                }
            }
            return result;
        }

        @Override
        public List<Subscription> getAllSubscriptions() throws SQLException {
            return new ArrayList<>(subscriptions);
        }

        @Override
        public void updateSubscription(Subscription subscription) throws SQLException {
            for (int i = 0; i < subscriptions.size(); i++) {
                if (subscriptions.get(i).getId() == subscription.getId()) {
                    subscriptions.set(i, subscription);
                    break;
                }
            }
        }

        @Override
        public void deleteSubscription(int id) throws SQLException {
            subscriptions.removeIf(subscription -> subscription.getId() == id);
        }
    }
}
