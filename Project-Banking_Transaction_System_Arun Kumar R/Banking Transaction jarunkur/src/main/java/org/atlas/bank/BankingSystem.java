package org.atlas.bank;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.util.Scanner;

public class BankingSystem {

    public static void main(String[] args) {

        // AWS DynamoDB client
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create("accessKey", "secretKey");

        DynamoDbClient client = DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:8001"))
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        Scanner sc = new Scanner(System.in);

        // Initialize tables (except Admin table, which already exists)
        CustomerService.createTable(client);
        AccountService.createTable(client);
        AdminService.createDeleteCustomerTable(client);

        System.out.println("Customer Table ready.");
        System.out.println("Account Table ready.");
        System.out.println("TransactionHistory Table ready.");
        System.out.println("DeleteCustomer Table ready.\n");

        // Main menu loop
        while (true) {
            System.out.println("=== DynamoDB Banking System ===\n");
            System.out.println("Select Option: 1-Login  2-Register  3-Exit");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    handleLogin(client, sc);
                    break;

                case "2":
                    CustomerService.registerCustomer(client, sc);
                    break;

                case "3":
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // Handle login menu for Admin and Customer
    private static void handleLogin(DynamoDbClient client, Scanner sc) {
        System.out.print("Enter EmailId: ");
        String email = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        if (AdminService.isAdmin(client, email, password)) {
            System.out.println("\nWelcome Admin! You can perform insert/update/delete operations.");
            AdminService.adminOperations(client, sc);
        } else if (CustomerService.login(client, email, password) != null) {
            String customerId = CustomerService.login(client, email, password);
            System.out.println("\nWelcome Customer! CustomerID: " + customerId);
            CustomerService.customerOperations(client, sc, customerId);
        } else {
            handleIncorrectLogin(client, sc, email);
        }
    }

    // Handle incorrect login with Retry / Forget Password / Back to Main Menu
    private static void handleIncorrectLogin(DynamoDbClient client, Scanner sc, String email) {
        System.out.println("Incorrect Email or Password!");
        System.out.println("1) Retry Login  2) Forget Password  3) Back to Main Menu");
        System.out.print("Enter your choice: ");
        String option = sc.nextLine();

        switch (option) {
            case "1":
                handleLogin(client, sc);
                break;

            case "2":
                handleForgetPassword(client, sc, email);
                break;

            case "3":
                return;

            default:
                System.out.println("Invalid option! Returning to main menu.");
        }
    }

    // Handle forget password for Admin or Customer
    private static void handleForgetPassword(DynamoDbClient client, Scanner sc, String email) {
        System.out.print("Enter registered Contact Number for verification: ");
        String contact = sc.nextLine();

        // Check if Admin
        if (AdminService.verifyContact(client, email, contact)) {
            String newPassword;
            while (true) {
                System.out.print("Enter New Password (min 8 chars, 1 uppercase, 1 lowercase, 1 special): ");
                newPassword = sc.nextLine();
                if (BankingSystem.validatePassword(newPassword)) break;
                else System.out.println("Password not valid!");
            }
            AdminService.updatePassword(client, email, newPassword);
            System.out.println("Admin password reset successfully! Logging you in now...");
            AdminService.adminOperations(client, sc);
            return;
        }

        // Check if Customer
        String customerId = CustomerService.getCustomerIdByEmail(client, email);
        if (customerId != null) {
            String storedContact = CustomerService.getCustomerContact(client, customerId);
            if (storedContact.equals(contact)) {
                String newPassword;
                while (true) {
                    System.out.print("Enter New Password (min 8 chars, 1 uppercase, 1 lowercase, 1 special): ");
                    newPassword = sc.nextLine();
                    if (BankingSystem.validatePassword(newPassword)) break;
                    else System.out.println("Password not valid!");
                }
                CustomerService.updatePassword(client, customerId, newPassword);
                System.out.println("Customer password reset successfully! Logging you in now...");
                CustomerService.customerOperations(client, sc, customerId);
                return;
            }
        }

        System.out.println("Verification failed! Contact number does not match.");
    }

    // Shared password validation for registration/reset
    public static boolean validatePassword(String password) {
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*[a-z].*")) return false;
        if (!password.matches(".*[!@#$%^&*()].*")) return false;
        return true;
    }
}
