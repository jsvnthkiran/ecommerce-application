package com.ecommerce.view;


import com.ecommerce.controller.AdminController;
import com.ecommerce.controller.CustomerController;
import com.ecommerce.dao.*;
import com.ecommerce.service.*;
import com.ecommerce.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ViewImpl {

//    private static Connection getConnection() throws SQLException {
//        String url = "jdbc:mysql://localhost:3306/food_delivery?useSSL=false";
//        String user = "root";
//        String password = "1234";
//        return DriverManager.getConnection(url, user, password);
//    }

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            ProductDAO productDAO = new ProductDAOImpl(connection);
            SubscriptionDAO subscriptionDAO = new SubscriptionDAOImpl(connection);
            DeliveryScheduleDAO deliveryScheduleDAO = new DeliveryScheduleDAOImpl(connection);
            OrderHistoryDAO orderHistoryDAO = new OrderHistoryDAOImpl(connection);
            CustomerDAO customerDAO = new CustomerDAOImpl(connection);

            ProductService productService = new ProductServiceImpl(productDAO);
            SubscriptionService subscriptionService = new SubscriptionServiceImpl(subscriptionDAO);
            DeliveryScheduleService deliveryScheduleService = new DeliveryScheduleServiceImpl(deliveryScheduleDAO);
            OrderHistoryService orderHistoryService = new OrderHistoryServiceImpl(orderHistoryDAO);
            CustomerService customerService = new CustomerServiceImpl(customerDAO);


            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Main Menu:");
                System.out.println("1. Admin");
                System.out.println("2. Customer");
                System.out.println("3. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        AdminController adminController = new AdminController(
                                productService,
                                subscriptionService,
                                deliveryScheduleService,
                                orderHistoryService
                        );
                        adminController.showAdminMenu();
                        break;
                    case 2:
                        // Customer Menu
                        CustomerController customerController = new CustomerController(
                                productService,
                                subscriptionService,
                                deliveryScheduleService,
                                orderHistoryService,
                                customerService
                        );
                        customerController.showCustomerMenu();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
