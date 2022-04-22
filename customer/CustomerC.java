package Customer;
//example class for client of bank
public class CustomerC {
    private final String firstName;
    private final String lastName;
    private final String addressLine;
    private final String city;
    private final String state;
    private final String zip;
    private final String phone;
    private final String email;
    //use Builder design pattern
    private CustomerC(Builder builder) {
        this.addressLine = builder.addressLine;
        this.city = builder.city;
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.phone = builder.phone;
        this.zip = builder.zip;
        this.state = builder.state;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
    public String toString() {
        return "User: " + this.firstName + ", " + this.lastName + ", " + this.zip + ", " + this.phone + ", " + this.city + ", " + this.addressLine + ", " + this.email;
    }
    public static class Builder {
        private String firstName;
        private String lastName;
        private String addressLine;
        private String city;
        private String state;
        private String zip;
        private String phone;
        private String email;

        public Builder firstname(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder lastname( String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder email( String email) {
            this.email = email;
            return this;
        }
        public Builder phone(String phone) {
            this.phone = phone;
            return this;

        }
        public Builder zip(String zip) {
            this.zip = zip;
            return this;
        }
        public Builder state(String state) {
            this.state = state;
            return this;
        }
        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder addressLine(String addressLine) {
            this.addressLine = addressLine;
            return this;
        }
        public CustomerC build() {
            CustomerC customerC =  new CustomerC(this);
            return customerC;
        }

    }
}