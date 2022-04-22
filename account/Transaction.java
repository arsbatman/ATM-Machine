package Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import Customer.Customer;
import Utility.DBconnection;
import Utility.MScanner;

public class Transaction {
    private int transactionID;
    private int transactionAmount;
    private String transactionType;
    private int customerID;
    private String accountNumber;
    private static ResultSet resultSet;
    private static PreparedStatement stmt;
    boolean isValidAmount = false;


    public int getTransactionID() {
        return transactionID;
    }
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
    public int getTransactionAmount() {
        return transactionAmount;
    }
    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    public String getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public static ArrayList<Transaction> createTransactionArrayList (Customer customer) {
        ArrayList<Transaction> transactionArray = null;

        //Create an ArrayList of Transaction objects.
        //Then add each transaction object to the ArrayList
        try {
            stmt = DBconnection.prepareStatement("select count(transaction_id) from transaction where customer_id = ?");
            stmt.setInt(1, customer.getCustomerID());
            resultSet = stmt.executeQuery();
            resultSet.next();

            int numberOfTransactions = resultSet.getInt(1);

            transactionArray = new ArrayList<Transaction>(numberOfTransactions);

            for (int i = 1; i <= numberOfTransactions; i++) {
                Transaction transactionInArrayList = new Transaction();
                transactionArray.add(transactionInArrayList);
            }

            //retrieve transaction data from the database about all the transactions that the customer
            stmt = DBconnection.prepareStatement("select transaction_id, transaction_amount, transaction_type, customer_id, account_number from transaction where customer_id = ?");
            stmt.setInt(1, customer.getCustomerID());
            resultSet = stmt.executeQuery();

            //Add the data
            //Transaction instance
            int i = 0;
            while (i <= numberOfTransactions-1) {
                while (resultSet.next()) {
                    transactionArray.get(i).setTransactionID(resultSet.getInt(1));
                    transactionArray.get(i).setTransactionAmount(resultSet.getInt(2));
                    transactionArray.get(i).setTransactionType(resultSet.getString(3));
                    transactionArray.get(i).setCustomerID(resultSet.getInt(4));
                    transactionArray.get(i).setAccountNumber(resultSet.getString(5));
                    i++;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionArray;
    }

    public void makeDeposit(Customer customer, Account account, Transaction transaction, ArrayList<Transaction> transactionArray) {
        transaction.setTransactionType("Deposit");

        while (isValidAmount == false) {
            System.out.println("How much would you like to deposit?  Enter numbers only.");
            transaction.validateAmount(customer, account, transaction, transactionArray);
        }
        try {
            //update account table in db
            stmt = DBconnection.prepareStatement("Update account set account_balance = ? where account_number = ?");
            stmt.setInt(1, account.getAccountBalance() + transaction.getTransactionAmount());
            stmt.setInt(2, account.getAccountNumber());
            stmt.executeUpdate();

            //update transaction table in db
            stmt = DBconnection.prepareStatement("Insert into transaction (customer_id, transaction_amount, transaction_type, account_number) values (?,?,?,?)");
            stmt.setInt(1, customer.getCustomerID());
            stmt.setInt(2, transaction.getTransactionAmount());
            stmt.setString(3, "Deposit");
            stmt.setInt(4, account.getAccountNumber());
            stmt.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Your money have been deposited.\n");
    }

    public void makeWithdrawal(Customer customer, Account account, Transaction transaction, ArrayList<Transaction> transactionArray) {
        transaction.setTransactionType("Withdrawal");

        while (isValidAmount == false) {
            System.out.println("How much would you like to withdraw?  Enter numbers.");
            transaction.validateAmount(customer, account, transaction, transactionArray);
        }
        try {
            //update account table in db
            stmt = DBconnection.prepareStatement("Update account set account_balance = ? where account_number = ?");
            stmt.setInt(1, account.getAccountBalance() - transaction.getTransactionAmount());
            stmt.setInt(2, account.getAccountNumber());
            stmt.executeUpdate();

            //update transaction table in db
            stmt = DBconnection.prepareStatement("Insert into transaction (customer_id, transaction_amount, transaction_type, account_number) values (?,?,?,?)");
            stmt.setInt(1, customer.getCustomerID());
            stmt.setInt(2, transaction.getTransactionAmount());
            stmt.setString(3, "Withdrawal");
            stmt.setInt(4, account.getAccountNumber());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Your funds have been withdrawn.\n");
    }
    public void validateAmount(Customer customer, Account account, Transaction transaction, ArrayList<Transaction> transactionArray) {
        int amount = 0;
        boolean condition1 = true;
        boolean condition2 = true;
        boolean condition3 = true;

        //check for InputMismatchExceptions
        try {
            amount = MScanner.getNumber();
        }
        catch (InputMismatchException e) {
            MScanner.scan.nextLine();

            System.out.println("Please enter numbers only.\n");

            condition1 = false;
        }
        //check for negative amounts entered
        if (amount < 0) {
            System.out.println("Please enter a positive amount.\n");

            condition2 = false;
        }
        //check to make sure customer is not withdrawing more money
        else if (transaction.getTransactionType().equals("Withdrawal") && account.getAccountBalance() - amount < 0) {
            System.out.println("You don't have that much money in your account.  Try withdrawing a lesser amount.\n");

            condition3 = false;
        }
        else if (condition1 == true && condition2 == true && condition3 == true){
            isValidAmount = true;
            transaction.setTransactionAmount(amount);
        }
    }
}