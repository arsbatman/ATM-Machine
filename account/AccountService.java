package Account;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import Customer.Customer;
import Utility.DBconnection;
import Utility.MScanner;

public class AccountService {
    private static String choice;
    private static PreparedStatement stmt;

    public void openAccount(Customer customer, Account account) {
        System.out.println("Which type of account would you like to open?\n");
        System.out.println("Checking");
        System.out.println("Savings\n");

        choice = MScanner.getInputToLower();
        //checker type account in bank
        if (choice.equals("checking") || choice.equals("savings")) {
            try {
                stmt = DBconnection.prepareStatement("Insert into account (account_type, account_balance, customer_id) values (?, 0, ?)");
                stmt.setString(1, choice);
                stmt.setInt(2, customer.getCustomerID());
                stmt.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("The "+choice+" account you requested is now open.\n");
        }
        else {
            System.out.println("Please try again.\n");
        }
    }
}