package Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Utility.DBconnection;
import Utility.MScanner;

public class Customer {
    private int customerID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String addressLine;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String email;
    private static ResultSet resultSet;
    private static PreparedStatement stmt;

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
//Create or Update account input information client
    public void createOrUpdateProfile(Customer customer) {

        customer.checkFirstName(customer);

        System.out.println("Last Name: ");
        customer.setLastName(MScanner.getInput());

        System.out.println("Address (Line ): ");
        customer.setAddressLine(MScanner.getInput());


        System.out.println("City: ");
        customer.setCity(MScanner.getInput());

        customer.checkStateAbbreviation(customer);

        customer.checkZipCode(customer);

        customer.checkPhoneNumber(customer);

        customer.checkEmailAddress(customer);
    }
//if client update his/her account this update data in db
    public void pushProfileToDatabase(Customer customer) {
        try {
            stmt = DBconnection.prepareStatement("Update customer set first_name = ?, last_name = ?, address_line = ?, city = ?, state = ?, zip = ?, phone = ?, email = ? where username = ?");
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getAddressLine());
            stmt.setString(4, customer.getCity());
            stmt.setString(5, customer.getState());
            stmt.setString(6, customer.getZip());
            stmt.setString(7, customer.getPhone());
            stmt.setString(8, customer.getEmail());
            stmt.setString(9, customer.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Your profile has been updated.\n");
    }
//
    public void pullProfileFromDatabase(Customer customer) {
        try {
            stmt = DBconnection.prepareStatement("Select first_name, last_name, address_line, city, state, zip, phone, email, customer_id from customer where username = ?");
            stmt.setString(1, customer.getUsername());
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                customer.setCustomerID(resultSet.getInt("customer_id"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setAddressLine(resultSet.getString("address_line"));
                customer.setCity(resultSet.getString("city"));
                customer.setState(resultSet.getString("state"));
                customer.setZip(resultSet.getString("zip"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//first name checker
    public void checkFirstName(Customer customer) {
        boolean isValidFirstName = false;
        String enteredFirstName;

        while (!isValidFirstName) {
            System.out.println("First Name: ");

            enteredFirstName = MScanner.getInput();

            if (enteredFirstName.isEmpty() || enteredFirstName.contains(" ")) {
                System.out.println("First name is required and can not contain spaces.  Please try again.\n");
            } else {
                for (int i = 0; i < enteredFirstName.length(); i++) {
                    if (!Character.isLetter(enteredFirstName.charAt(i))) {
                        System.out.println("First name can not contain numbers or symbols.  Please try again.\n");
                        break;
                    } else {
                        customer.setFirstName(enteredFirstName);
                        isValidFirstName = true;
                    }
                }
            }
        }
    }
//First letters in name and surname
    public void checkStateAbbreviation(Customer customer) {
        boolean isValidStateAbbreviation = false;
        String stateAbbreviation;

        while (!isValidStateAbbreviation) {
            System.out.println("State (two letter abbreviation): ");

            stateAbbreviation = MScanner.getInput();

            if (stateAbbreviation.isEmpty()) {
                break;
            } else if (stateAbbreviation.length() == 2 && Character.isLetter(stateAbbreviation.charAt(0)) && Character.isLetter(stateAbbreviation.charAt(1))) {
                customer.setState(stateAbbreviation);
                isValidStateAbbreviation = true;
            } else {
                System.out.println("This field accepts only two-letter abbreviations (or you may leave it blank).\n");
            }
        }
    }
    //zip cheker if this use number and "+" symbol
    public void checkZipCode(Customer customer) {
        boolean isValidZipCode = false;
        String zip;

        while (!isValidZipCode) {
            System.out.println("ZIP Code (6 Digits, Numbers Only): ");

            zip = MScanner.getInput();

            if (zip.isEmpty()) {
                break;
            } else if (zip.length() == 6 && zip.matches("[0-9]+")) {
                customer.setZip(zip);
                isValidZipCode = true;
            } else {
                System.out.println("This field accepts only a six digit zip code.\n");
            }
        }
    }
//phone cheker if this use number and "+" symbol
    public void checkPhoneNumber(Customer customer) {
        boolean isValidPhoneNumber = false;
        String phoneNumber;

        while (!isValidPhoneNumber) {
            System.out.println("Phone (11 Digits, Numbers Only): ");

            phoneNumber = MScanner.getInput();

            if (phoneNumber.isEmpty()) {
                break;
            } else if (phoneNumber.length() == 11 && phoneNumber.matches("[0-9]+")) {
                customer.setPhone(phoneNumber);
                isValidPhoneNumber = true;
            } else {
                System.out.println("This field accepts only a ten digit phone number without any spaces or special characters.\n");
            }
        }
    }
//checker email address client if he/she wrote correctly
    public void checkEmailAddress(Customer customer) {
        boolean isValidEmailAddress = false;
        String emailAddress;

        while (!isValidEmailAddress) {
            System.out.println("Email Address: ");

            emailAddress = MScanner.getInputToLower();

            if (emailAddress.isEmpty()) {
                break;
            } else if (!emailAddress.contains("@")) {
                System.out.println("Please enter a valid email address.\n");
            } else {
                customer.setEmail(emailAddress);
                isValidEmailAddress = true;
            }
        }
    }
}
