import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;

class BankAccount {
    private double balance;
    private int pin;
    private String accountNumber;

    private List<Transaction> transactionHistory;

    public BankAccount(double initialBalance, int accountPin, String accountNumber) {
        this.balance = initialBalance;
        this.pin = accountPin;
        this.accountNumber = accountNumber;
        this.transactionHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public boolean validateCredentials(String inputAccountNumber, int inputPin) {
        return this.accountNumber.equals(inputAccountNumber) && this.pin == inputPin;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction(new DepositTransaction(amount));
            System.out.println("Deposit successful. New balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0) {
            if (amount <= balance) {
                balance -= amount;
                addTransaction(new WithdrawalTransaction(amount));
                System.out.println("Withdrawal successful. New balance: $" + balance);
                return true;
            } else {
                System.out.println("Insufficient funds for withdrawal. Current balance: $" + balance);
            }
        } else {
            System.out.println("Invalid withdrawal amount. Please enter a positive amount.");
        }
        return false;
    }

    public boolean transfer(BankAccount recipient, double amount) {
        if (amount > 0) {
            if (amount <= balance) {
                balance -= amount;
                recipient.balance += amount;
                addTransaction(new TransferTransaction(recipient.getAccountNumber(), amount));
                System.out.println("Transfer successful. New balance: $" + balance);
                return true;
            } else {
                System.out.println("Insufficient funds for transfer. Current balance: $" + balance);
            }
        } else {
            System.out.println("Invalid transfer amount. Please enter a positive amount.");
        }
        return false;
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public void printTransactionHistory() {
        System.out.println("\nTransaction history:");
        for (int i = 0; i < transactionHistory.size(); i++) {
            Transaction transaction = transactionHistory.get(i);
            System.out.println((i + 1) + ". " + transaction.getDescription());
        }
    }
}

interface Transaction {
    String getDescription();
}

class DepositTransaction implements Transaction {
    private double amount;

    public DepositTransaction(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String getDescription() {
        return String.format("Deposit of $%.2f", amount);
    }
}

class TransferTransaction implements Transaction {
    private String recipientAccountNumber;
    private double amount;

    public TransferTransaction(String recipientAccountNumber, double amount) {
        this.recipientAccountNumber = recipientAccountNumber;
        this.amount = amount;
    }

    public String getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String getDescription() {
        return String.format("Transfer of $%.2f to account %s", amount, recipientAccountNumber);
    }
}

class WithdrawalTransaction implements Transaction {
    private double amount;

    public WithdrawalTransaction(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String getDescription() {
        return String.format("Withdrawal of $%.2f", amount);
    }
}

class ATM {
    private BankAccount bankAccount;
    private HashMap<String, BankAccount> accountMap;

    public ATM(BankAccount bankAccount, HashMap<String, BankAccount> accountMap) {
        this.bankAccount = bankAccount;
        this.accountMap = accountMap;
    }

    public void displayMenu() {
        System.out.println("ATM Menu:");
        System.out.println("1. Transaction History");
        System.out.println("2. Transfer");
        System.out.println("3. Withdraw");
        System.out.println("4. Deposit");
        System.out.println("5. Check Balance");
        System.out.println("6. Exit");
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            BankAccount recipientAccount = null;

            while (true) {
                displayMenu();
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        bankAccount.printTransactionHistory();
                        break;
                    case 2:
                        System.out.print("Enter the recipient's account number: ");
                        String recipientAccountNumber = scanner.next();
                        recipientAccount = accountMap.get(recipientAccountNumber);
                        if (recipientAccount == null) {
                            System.out.println("Invalid recipient account number.");
                        } else {
                            System.out.print("Enter the transfer amount: $");
                            double transferAmount = scanner.nextDouble();
                            boolean transferSuccess = bankAccount.transfer(recipientAccount, transferAmount);
                            if (transferSuccess) {
                                System.out.println("Transfer successful.");
                            }
                        }
                        break;
                    case 3:
                        System.out.print("Enter the withdrawal amount: $");
                        double withdrawalAmount = scanner.nextDouble();
                        boolean withdrawSuccess = bankAccount.withdraw(withdrawalAmount);
                        if (withdrawSuccess) {
                            System.out.println("Withdrawal successful.");
                        }
                        break;
                    case 4:
                        System.out.print("Enter the deposit amount: $");
                        double depositAmount = scanner.nextDouble();
                        bankAccount.deposit(depositAmount);

                        break;
                    case 5:
                        System.out.println("Current balance: $" + bankAccount.getBalance());
                        break;
                    case 6:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }

    }
}

public class BankingSystem {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\n\n**********Welcome to the bank!**************");

            HashMap<String, BankAccount> accountMap = new HashMap<>();
            accountMap.put("220900", new BankAccount(5000, 4455, "220900"));
            System.out.println("\n\nAccount Opened: Account Number: 220900 || Balance:15000 ");
            accountMap.put("230996", new BankAccount(10000, 5566, "230996"));
            System.out.println("\n\nAccount Opened: Account Number: 230996 || Balance:10000 ");

            System.out.print("Enter your Account Number: ");
            String accountNumber = scanner.next();

            System.out.print("Enter your PIN: ");
            int pin = scanner.nextInt();

            BankAccount currentAccount = null;
            for (BankAccount account : accountMap.values()) {
                if (account.getBalance() > 0 && account.validateCredentials(accountNumber, pin)) {
                    currentAccount = account;
                    break;
                }
            }

            if (currentAccount == null) {
                System.out.println("\nInvalid account number or PIN. Exiting the system.");
                System.exit(0);
            }

            System.out.println("\n\nWorking on Account Number: " + accountNumber);
            ATM atm = new ATM(currentAccount, accountMap);
            atm.run();
        }
        ;
    }
}