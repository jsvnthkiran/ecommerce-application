package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.model.Subscription;
import com.ecommerce.service.DeliveryScheduleService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.SubscriptionService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CustomerController {

    private ProductService productService;
    private SubscriptionService subscriptionService;
    private DeliveryScheduleService deliveryScheduleService;

    public CustomerController(ProductService productService, SubscriptionService subscriptionService, DeliveryScheduleService deliveryScheduleService) {
        this.productService = productService;
        this.subscriptionService = subscriptionService;
        this.deliveryScheduleService = deliveryScheduleService;
    }

    public void showCustomerMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Customer Menu:");
            System.out.println("1. View all products");
            System.out.println("2. Subscribe to product");
            System.out.println("3. View my subscriptions");
            System.out.println("4. Cancel subscription");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            try {
                switch (choice) {
                    case 1:
                        viewAllProducts();
                        break;
                    case 2:
                        subscribeToProduct(scanner);
                        break;
                    case 3:
                        viewMySubscriptions(scanner);
                        break;
                    case 4:
                        cancelSubscription(scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            }
        }
    }

    private void viewAllProducts() throws SQLException {
        List<Product> products = productService.getAllProducts();
        System.out.println("Products:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private void subscribeToProduct(Scanner scanner) throws SQLException {
        System.out.print("Enter product ID to subscribe: ");
        int productId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter customer ID to subscribe: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter subscription type (daily, weekly, bi-weekly, monthly): ");
        String type = scanner.nextLine();
        System.out.print("Enter start date (yyyy-mm-dd): ");
        Date startDate = Date.valueOf(scanner.nextLine());
        System.out.print("Enter end date (yyyy-mm-dd): ");
        Date endDate = Date.valueOf(scanner.nextLine());

        Subscription subscription = new Subscription(productId,customerId, type, true, startDate, endDate);
        subscriptionService.addSubscription(subscription);
        System.out.println("Subscription added successfully.");
    }

    private void viewMySubscriptions(Scanner scanner) throws SQLException {
        System.out.print("Enter your customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByCustomerId(customerId);
        System.out.println("My Subscriptions:");
        for (Subscription subscription : subscriptions) {
            System.out.println(subscription);
        }
    }

    private void cancelSubscription(Scanner scanner) throws SQLException {
        System.out.print("Enter subscription ID to cancel: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Subscription subscription = subscriptionService.getSubscriptionById(id);
        if (subscription != null) {
            subscription.setActive(false);
            subscriptionService.updateSubscription(subscription);
            System.out.println("Subscription cancelled successfully.");
        } else {
            System.out.println("Subscription not found.");
        }
    }
}
