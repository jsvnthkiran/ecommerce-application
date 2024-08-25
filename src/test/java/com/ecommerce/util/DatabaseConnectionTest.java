package com.ecommerce.util;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest {

    @Test
    public void testGetConnection() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Assert that the connection is not null
            assertNotNull(connection, "Connection should not be null");

            // Assert that the connection is open
            assertFalse(connection.isClosed(), "Connection should be open");
        } catch (SQLException e) {
            fail("SQLException should not have been thrown: " + e.getMessage());
        }
    }
}
