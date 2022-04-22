package HP;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Customer.Customer;
import Customer.CD;
import Utility.MScanner;
import Utility.DBconnection;
import Customer.CustomerC;
public class MScreen {
    private static ResultSet resultSet;
    private static PreparedStatement stmt;
    public static void main(String[] args) {
        try {
            DBconnection.getConnection();
            MScanner.openScanner();
            System.out.println("Hello, and welcome to Shiro Bank!\n");
            startup();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void startup() {
        Customer customer = new Customer();
        boolean isRunning = true;
        CustomerC customerC= new CustomerC.Builder().firstname("Arsen")
                .lastname("Ishan")
                .email("arsenishan@mail.ru")
                .addressLine("AITU")
                .city("Nur-sultan")
                .phone("+7751417394")
                .zip("010000")
                .state("Akmola")
                .build();
        while (isRunning) {
            System.out.println("Type \"Sign in\" to access your account.");
            System.out.println("Type \"Create account\" to create a new account.");
            System.out.println("Type \"Example\" your account.");
            System.out.println("Type \"Reset password\" to change your password.\n");
            System.out.println("Type \"Home\" at any time to return to this menu.\n");
            System.out.println("Capitalization doesn't matter when typing your choice");
            String choice = MScanner.getInputToLower();
            switch (choice) {
                case "sign in":
                    isRunning = false;
                    signIn(customer);
                    break;
                case "create account":
                    isRunning = false;
                    createAccount(customer);
                    break;
                case "reset password":
                    isRunning = false;
                    resetPassword(customer);
                    break;
                case "example":
                    System.out.println(customerC);
                    break;
                default:
                    System.out.println("I'm sorry, I didn't understand that.  Please try again.\n");
            }
        }
    }

    public static void signIn(Customer customer) {
        customer.setUsername(getUsernameFromDb(customer));
        customer = getPasswordFromDb(customer);
        CD.dashboardMenu(customer);
    }

    public static void resetPassword(Customer customer) {
        customer.setUsername(getUsernameFromDb(customer));
        customer = getPasswordFromDb(customer);
        updatePassword(customer);
    }
    //retrieve entered username from database and if it does not exist, redirect to home page.
    public static String getUsernameFromDb(Customer customer) {
        System.out.println("Please enter your username.");

        String input = MScanner.getInputToLower();

        try {
            stmt = DBconnection.prepareStatement("select username from customer where username = ?");
            stmt.setString(1, input);
            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                System.out.println("That username does not exist in our system.\n");
                startup();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return input;
    }
    //retrieve database password and compare to entered password.  If they do not match, return to home page.
    public static Customer getPasswordFromDb(Customer customer) {
        System.out.println("Please enter your password.");

        String password = MScanner.getInput();
        String dbPassword;

        try {
            stmt = DBconnection.prepareStatement("select password from customer where username = ?");
            stmt.setString(1, customer.getUsername());
            resultSet = stmt.executeQuery();
            resultSet.next();

            dbPassword = resultSet.getString("password");

            if (!password.equals(dbPassword)) {
                System.out.println("The password you entered does not match our records.Please try again.\n");
                startup();
            }
            customer.pullProfileFromDatabase(customer);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    //Update SQL database with new user-supplied password
    public static void updatePassword(Customer customer) {
        System.out.println("Please enter a new password.");

        String newPassword = MScanner.getInput();

        try {
            stmt = DBconnection.prepareStatement("Update customer set password = ? where username = ?");
            stmt.setString(1, newPassword);
            stmt.setString(2, customer.getUsername());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Your password has been updated.Please login again.\n");

        signIn(customer);
    }
    //create new customer profile
    public static void createAccount(Customer customer) {
        boolean usernameIsValid = false;

        while (!usernameIsValid) {
            System.out.println("Please enter a username.");

            customer.setUsername(MScanner.getInputToLower());

            try {
                stmt = DBconnection.prepareStatement("select username from customer where username = ?");
                stmt.setString(1, customer.getUsername());
                resultSet = stmt.executeQuery();

                if (resultSet.next()) {
                    System.out.println("That username already exists.\n");
                }
                else {
                    usernameIsValid = true;
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Please create a password for your account.");

        customer.setPassword(MScanner.getInput());

        try {
            stmt = DBconnection.prepareStatement("Insert into customer (username, password) values (?,?)");
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getPassword());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        customer.createOrUpdateProfile(customer);
        customer.pushProfileToDatabase(customer);

        System.out.println("Your account has been created.\n");

        signIn(customer);
    }
}