import java.util.Scanner;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}

class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void displayMenu() {
        System.out.println("Welcome to the ATM!");
        System.out.println("1. Check Balance");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Exit");
    }

    public void processOption(int option) {
        switch (option) {
            case 1:
                checkBalance();
                break;
            case 2:
                withdraw();
                break;
            case 3:
                deposit();
                break;
            case 4:
                System.out.println("Thank you for using the ATM.");
                System.exit(0);
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void checkBalance() {
        System.out.println("Your current balance is: $" + account.getBalance());
    }

    private void withdraw() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount to withdraw: $");
        double amount = scanner.nextDouble();

        if (account.withdraw(amount)) {
            System.out.println("Withdrawal successful. Your Remaining balance is: $" + account.getBalance());
        } else {
            System.out.println("Insufficient funds. Withdrawal failed.");
        }
    }

    private void deposit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount to deposit: $");
        double amount = scanner.nextDouble();

        account.deposit(amount);
        System.out.println("Deposit successful.Your Current balance is: $" + account.getBalance());
    }
}

public class AtmInterface {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(1000.0); 
        ATM atm = new ATM(bankAccount);

        while (true) {
            atm.displayMenu();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter option: ");
            int option = scanner.nextInt();

            atm.processOption(option);
            System.out.println();
        }
    }
}

