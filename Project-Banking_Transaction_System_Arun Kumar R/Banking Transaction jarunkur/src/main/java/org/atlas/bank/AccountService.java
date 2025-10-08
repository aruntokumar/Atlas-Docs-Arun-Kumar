package org.atlas.bank;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.atlas.bank.PinHasher;

public class AccountService {

    private static final String ACCOUNT_TABLE = "Account";
    private static final String TRANSACTION_TABLE = "TransactionHistory";

    // ✅ Global Bank Info
    private static final String BANK_NAME = "HDFC Bank";
    private static final String BANK_ADDRESS = "Mumbai, India";

    // ---------------- Table Creation ----------------
    public static void createTable(DynamoDbClient client) {
        ensureAccountTableExists(client);
        ensureTransactionTableExists(client);
    }

    private static void ensureAccountTableExists(DynamoDbClient client) {
        try {
            client.describeTable(DescribeTableRequest.builder().tableName(ACCOUNT_TABLE).build());
            System.out.println("✅ Table already exists: " + ACCOUNT_TABLE);
        } catch (ResourceNotFoundException e) {
            client.createTable(CreateTableRequest.builder()
                    .tableName(ACCOUNT_TABLE)
                    .attributeDefinitions(AttributeDefinition.builder()
                            .attributeName("AccountID").attributeType("S").build())
                    .keySchema(KeySchemaElement.builder()
                            .attributeName("AccountID").keyType(KeyType.HASH).build())
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .build());
            System.out.println("🚀 Created new table: " + ACCOUNT_TABLE);
        }
    }

    private static void ensureTransactionTableExists(DynamoDbClient client) {
        try {
            client.describeTable(DescribeTableRequest.builder().tableName(TRANSACTION_TABLE).build());
            System.out.println("✅ Table already exists: " + TRANSACTION_TABLE);
        } catch (ResourceNotFoundException e) {
            client.createTable(CreateTableRequest.builder()
                    .tableName(TRANSACTION_TABLE)
                    .attributeDefinitions(
                            AttributeDefinition.builder().attributeName("TransactionID").attributeType("S").build(),
                            AttributeDefinition.builder().attributeName("AccountID").attributeType("S").build()
                    )
                    .keySchema(
                            KeySchemaElement.builder().attributeName("TransactionID").keyType(KeyType.HASH).build(),
                            KeySchemaElement.builder().attributeName("AccountID").keyType(KeyType.RANGE).build()
                    )
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .build());
            System.out.println("🚀 Created new table: " + TRANSACTION_TABLE);
        }
    }

    // ---------------- Account Creation ----------------
    public static void createAccount(DynamoDbClient client, Scanner sc, String customerId) {
        String accountId = "A" + System.currentTimeMillis();

        System.out.println("Select Account Type: 1-Saving 2-Current");
        System.out.print("Enter your choice: ");
        String typeChoice = sc.nextLine();
        String type = typeChoice.equals("1") ? "Saving" : "Current";

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("AccountID", AttributeValue.builder().s(accountId).build());
        item.put("CustomerID", AttributeValue.builder().s(customerId).build());
        item.put("AccountType", AttributeValue.builder().s(type).build());
        item.put("Balance", AttributeValue.builder().n("0").build());

        client.putItem(PutItemRequest.builder().tableName(ACCOUNT_TABLE).item(item).build());
        System.out.println("✅ Account created with AccountID: " + accountId);

        // ---------------- Set Transfer PIN ----------------
        System.out.print("Set a 4-digit transfer PIN: ");
        String pin = sc.nextLine();
        String hashedPin = PinHasher.hashPin(pin);

        client.updateItem(UpdateItemRequest.builder()
                .tableName(ACCOUNT_TABLE)
                .key(Map.of("AccountID", AttributeValue.builder().s(accountId).build()))
                .attributeUpdates(Map.of("TransferPinHash", AttributeValueUpdate.builder()
                        .value(AttributeValue.builder().s(hashedPin).build())
                        .action(AttributeAction.PUT)
                        .build()))
                .build());
        System.out.println("✅ Transfer PIN set successfully!");
    }

    // ---------------- Helper Methods ----------------
    private static boolean accountBelongsToCustomer(DynamoDbClient client, String accountId, String customerId) {
        GetItemResponse response = client.getItem(
                GetItemRequest.builder()
                        .tableName(ACCOUNT_TABLE)
                        .key(Map.of("AccountID", AttributeValue.builder().s(accountId).build()))
                        .build()
        );
        return response.hasItem() && response.item().get("CustomerID").s().equals(customerId);
    }

    // ✅ Made public for JUnit testing
    public static boolean accountExists(DynamoDbClient client, String accountId) {
        GetItemResponse response = client.getItem(
                GetItemRequest.builder()
                        .tableName(ACCOUNT_TABLE)
                        .key(Map.of("AccountID", AttributeValue.builder().s(accountId).build()))
                        .build()
        );
        return response.hasItem();
    }

    // ✅ Made public for JUnit testing
    public static double getBalance(DynamoDbClient client, String accountId) {
        GetItemResponse response = client.getItem(
                GetItemRequest.builder()
                        .tableName(ACCOUNT_TABLE)
                        .key(Map.of("AccountID", AttributeValue.builder().s(accountId).build()))
                        .build()
        );
        if (!response.hasItem() || !response.item().containsKey("Balance")) {
            return 0;
        }
        return Double.parseDouble(response.item().get("Balance").n());
    }

    // ✅ Made public for JUnit testing
    public static void updateBalance(DynamoDbClient client, String accountId, double newBalance) {
        client.updateItem(UpdateItemRequest.builder()
                .tableName(ACCOUNT_TABLE)
                .key(Map.of("AccountID", AttributeValue.builder().s(accountId).build()))
                .attributeUpdates(Map.of("Balance", AttributeValueUpdate.builder()
                        .value(AttributeValue.builder().n(String.valueOf(newBalance)).build())
                        .action(AttributeAction.PUT)
                        .build()))
                .build());
    }

    private static void addTransaction(DynamoDbClient client, String accountId, String type,
                                       double amount, double balance, String destAccountId, String bankName) {
        String transactionId = "T" + System.currentTimeMillis();
        String timestamp = DateTimeFormatter.ofPattern("MM/dd/yyyy & HH:mm a")
                .withZone(ZoneId.systemDefault()).format(Instant.now());

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("TransactionID", AttributeValue.builder().s(transactionId).build());
        item.put("AccountID", AttributeValue.builder().s(accountId).build());
        if (destAccountId != null) {
            item.put("DestinationAccountID", AttributeValue.builder().s(destAccountId).build());
        }
        item.put("Type", AttributeValue.builder().s(type).build());
        item.put("Amount", AttributeValue.builder().n(String.valueOf(amount)).build());
        item.put("CurrentBalance", AttributeValue.builder().n(String.valueOf(balance)).build());
        item.put("Timestamp", AttributeValue.builder().s(timestamp).build());

        String bankDetails = (bankName != null ? bankName : BANK_NAME) + " - " + BANK_ADDRESS;
        item.put("BankDetails", AttributeValue.builder().s(bankDetails).build());

        client.putItem(PutItemRequest.builder().tableName(TRANSACTION_TABLE).item(item).build());
    }

    // ---------------- PIN Verification Helper ----------------
    private static boolean verifyOrSetPin(DynamoDbClient client, Scanner sc, String accountId) {
        GetItemResponse pinResponse = client.getItem(
                GetItemRequest.builder()
                        .tableName(ACCOUNT_TABLE)
                        .key(Map.of("AccountID", AttributeValue.builder().s(accountId).build()))
                        .build()
        );

        String storedPinHash = pinResponse.hasItem() && pinResponse.item().containsKey("TransferPinHash")
                ? pinResponse.item().get("TransferPinHash").s()
                : null;

        if (storedPinHash == null) {
            System.out.println("⚠️ No transfer PIN set for this account!");
            System.out.print("Do you want to set it now? (Y/N): ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                System.out.print("Enter new 4-digit transfer PIN: ");
                String newPin = sc.nextLine();
                String hashedPin = PinHasher.hashPin(newPin);

                client.updateItem(UpdateItemRequest.builder()
                        .tableName(ACCOUNT_TABLE)
                        .key(Map.of("AccountID", AttributeValue.builder().s(accountId).build()))
                        .attributeUpdates(Map.of("TransferPinHash", AttributeValueUpdate.builder()
                                .value(AttributeValue.builder().s(hashedPin).build())
                                .action(AttributeAction.PUT)
                                .build()))
                        .build());

                System.out.println("✅ Transfer PIN set successfully!");
                storedPinHash = hashedPin;
            } else {
                System.out.println("❌ Operation cancelled. Transfer PIN required.");
                return false;
            }
        }

        System.out.print("Enter your 4-digit transfer PIN: ");
        String enteredPin = sc.nextLine();
        if (!PinHasher.verifyPin(enteredPin, storedPinHash)) {
            System.out.println("❌ Invalid transfer PIN. Operation denied.");
            return false;
        }
        return true;
    }

    // ---------------- Deposit ----------------
    public static void deposit(DynamoDbClient client, Scanner sc, String customerId) {
        String accountId;
        while (true) {
            System.out.print("Enter AccountID: ");
            accountId = sc.nextLine();
            if (!accountBelongsToCustomer(client, accountId, customerId)) {
                System.out.println("❌ Wrong AccountID! Enter again:");
            } else break;
        }

        if (!verifyOrSetPin(client, sc, accountId)) return;

        System.out.print("Enter Amount to Deposit: ");
        double amount = Double.parseDouble(sc.nextLine());
        if (amount <= 0) {
            System.out.println("❌ Invalid amount!");
            return;
        }

        double balance = getBalance(client, accountId) + amount;
        updateBalance(client, accountId, balance);
        addTransaction(client, accountId, "Deposit", amount, balance, null, null);

        TransactionStackQueue.addTransaction("Deposit | AccID: " + accountId + " | Amount: " + amount);
        System.out.println("✅ Deposit successful! Current Balance: " + balance);
    }

    // ---------------- Withdraw ----------------
    public static void withdraw(DynamoDbClient client, Scanner sc, String customerId) {
        String accountId;
        while (true) {
            System.out.print("Enter AccountID: ");
            accountId = sc.nextLine();
            if (!accountBelongsToCustomer(client, accountId, customerId)) {
                System.out.println("❌ Wrong AccountID! Enter again:");
            } else break;
        }

        if (!verifyOrSetPin(client, sc, accountId)) return;

        System.out.print("Enter Amount to Withdraw: ");
        double amount = Double.parseDouble(sc.nextLine());
        if (amount <= 0) {
            System.out.println("❌ Invalid amount!");
            return;
        }

        double balance = getBalance(client, accountId);
        if (amount > balance) {
            System.out.println("❌ Insufficient balance!");
            return;
        }

        balance -= amount;
        updateBalance(client, accountId, balance);
        addTransaction(client, accountId, "Withdrawal", amount, balance, null, null);

        TransactionStackQueue.addTransaction("Withdrawal | AccID: " + accountId + " | Amount: " + amount);
        System.out.println("✅ Withdrawal successful! Current Balance: " + balance);
    }

    // ---------------- Transfer ----------------
    public static void transfer(DynamoDbClient client, Scanner sc, String customerId) {
        String sourceAccount;
        while (true) {
            System.out.print("Enter Source AccountID: ");
            sourceAccount = sc.nextLine();
            if (!accountBelongsToCustomer(client, sourceAccount, customerId)) {
                System.out.println("❌ Wrong AccountID! Enter again:");
            } else break;
        }

        if (!verifyOrSetPin(client, sc, sourceAccount)) return;

        System.out.println("Select Destination Bank:");
        System.out.println("1) Indian Bank");
        System.out.println("2) ICICI Bank");
        System.out.println("3) HDFC Bank");
        System.out.println("4) Axis Bank");
        System.out.print("Enter choice: ");
        String bankChoice = sc.nextLine();
        String destBank;
        switch (bankChoice) {
            case "1" -> destBank = "Indian Bank";
            case "2" -> destBank = "ICICI Bank";
            case "3" -> destBank = "HDFC Bank";
            case "4" -> destBank = "Axis Bank";
            default -> {
                System.out.println("⚠️ Invalid choice! Defaulting to Indian Bank.");
                destBank = "Indian Bank";
            }
        }

        System.out.print("Enter Destination AccountID: ");
        String destAccount = sc.nextLine();
        if (!accountExists(client, destAccount)) {
            System.out.println("❌ Destination AccountID not available!");
            return;
        }

        System.out.print("Enter Amount to Transfer: ");
        double amount = Double.parseDouble(sc.nextLine());
        if (amount <= 0) {
            System.out.println("❌ Invalid amount!");
            return;
        }

        double sourceBalance = getBalance(client, sourceAccount);
        if (amount > sourceBalance) {
            System.out.println("❌ Insufficient balance!");
            return;
        }
        double destBalance = getBalance(client, destAccount);

        // Update balances
        sourceBalance -= amount;
        destBalance += amount;

        updateBalance(client, sourceAccount, sourceBalance);
        updateBalance(client, destAccount, destBalance);

        // Add transactions
        addTransaction(client, sourceAccount, "Transfer Out", amount, sourceBalance, destAccount, destBank);
        addTransaction(client, destAccount, "Transfer In", amount, destBalance, sourceAccount, destBank);

        TransactionStackQueue.addTransaction("Transfer Out | AccID: " + sourceAccount + " | To Bank: " + destBank + " | Amount: " + amount);
        TransactionStackQueue.addTransaction("Transfer In  | AccID: " + destAccount + " | From Bank: " + destBank + " | Amount: " + amount);

        System.out.println("✅ Transfer successful! Current Balance: " + sourceBalance);
    }

    // ---------------- Transaction History ----------------
    public static void showTransactionHistory(DynamoDbClient client, Scanner sc, String customerId) {
        System.out.print("Enter AccountID: ");
        String accountId = sc.nextLine();

        if (!accountBelongsToCustomer(client, accountId, customerId)) {
            System.out.println("❌ Wrong AccountID!");
            return;
        }

        ScanResponse response = client.scan(ScanRequest.builder()
                .tableName(TRANSACTION_TABLE)
                .build());

        List<Map<String, AttributeValue>> txns = new ArrayList<>();
        for (Map<String, AttributeValue> item : response.items()) {
            if (item.get("AccountID") != null && item.get("AccountID").s().equals(accountId)) {
                txns.add(item);
            }
        }

        txns.sort(Comparator.comparingLong(m -> Long.parseLong(m.get("TransactionID").s().substring(1))));
        if (txns.isEmpty()) {
            System.out.println("No Transaction History for the Account");
        }
        else{
        System.out.println("\n📜 Transaction History for AccountID: " + accountId);
        for (Map<String, AttributeValue> txn : txns) {
            String bankDetails = txn.containsKey("BankDetails") ? txn.get("BankDetails").s() : "N/A";
            String destAcc = txn.containsKey("DestinationAccountID") ? txn.get("DestinationAccountID").s() : null;

            if (destAcc != null && !destAcc.isEmpty()) {
                System.out.printf("TransactionID: %s | Type: %s | Amount: %s | Current Balance: %s | Timestamp: %s | BankDetails: %s | DestinationAccountID: %s%n",
                        txn.get("TransactionID").s(),
                        txn.get("Type").s(),
                        txn.get("Amount").n(),
                        txn.get("CurrentBalance").n(),
                        txn.get("Timestamp").s(),
                        bankDetails,
                        destAcc);
            } else {
                System.out.printf("TransactionID: %s | Type: %s | Amount: %s | Current Balance: %s | Timestamp: %s | BankDetails: %s%n",
                        txn.get("TransactionID").s(),
                        txn.get("Type").s(),
                        txn.get("Amount").n(),
                        txn.get("CurrentBalance").n(),
                        txn.get("Timestamp").s(),
                        bankDetails);
            }
        }
    }
    }
}
