package Customer;
import Utility.DBconnection;
import Utility.MScanner;
import java.util.ArrayList;
import Account.Account;
import Account.AccountService;
import Account.Transaction;
import Calculator.Main;

public class CD{
    private static String choice;

    public static void dashboardMenu (Customer customer) {
        boolean isLoggedIn = true;
        boolean hasAccount;

        while (isLoggedIn == true) {

            Account account = new Account();
            Transaction transaction = new Transaction();
            AccountService accountService = new AccountService();
            Main main=new Main();
            ArrayList<Account> accountArray = Account.createAccountArrayList(customer);
            ArrayList<Transaction> transactionArray = Transaction.createTransactionArrayList(customer);

            System.out.println("You are at the dashboard.  Type one of the options below:\n");
            System.out.println("Open Account");
            System.out.println("Make Deposit");
            System.out.println("Make Withdrawal");
            System.out.println("Check Balance");
            System.out.println("Update Profile");
            System.out.println("Calculator");
            System.out.println("Sign Out");

            choice = MScanner.getInputToLower();

            switch(choice) {
                case "open account":
                    account.checkForOpenAccount(customer, accountArray, account);
                    accountService.openAccount(customer, account);
                    break;
                case "make deposit":
                    hasAccount = account.checkForOpenAccount(customer, accountArray, account);
                    while (hasAccount == true) {
                        account.checkAccountNumber(customer, accountArray, account);
                        transaction.makeDeposit(customer, account,transaction, transactionArray);
                        break;
                    }
                    break;
                case "make withdrawal":
                    hasAccount = account.checkForOpenAccount(customer, accountArray, account);
                    while (hasAccount == true) {
                        account.checkAccountNumber(customer, accountArray, account);
                        transaction.makeWithdrawal(customer, account, transaction, transactionArray);
                        break;
                    }
                    break;
                case "check balance":
                    account.checkForOpenAccount(customer, accountArray, account);
                    break;
                case "calculator":
                    main.doOperation();
                    break;
                case "update profile":
                    customer.createOrUpdateProfile(customer);
                    customer.pushProfileToDatabase(customer);
                    break;
                case "sign out":
                    isLoggedIn = false;
                    signOut(customer);
                    break;
                default:
                    System.out.println("I'm sorry.Please try again.\n");
            }
        }
    }

    public static void signOut(Customer customer) {
        System.out.println("Thanks for using Shiro Bank today!");

        DBconnection.closeDbConnection();
        MScanner.closeScanner();
        System.exit(0);
    }
}