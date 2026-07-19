package model;

public class Customer extends Person {

    private int accountNo;
    private int pin;
    private double balance;
    private String accountType;

    // Default Constructor
    public Customer() {

    }

    // Parameterized Constructor
    public Customer(int accountNo, String name, String phone,
                    String email, String address,
                    int pin, double balance, String accountType) {

        super(name, phone, email, address);

        this.accountNo = accountNo;
        this.pin = pin;
        this.balance = balance;
        this.accountType = accountType;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public void displayProfile() {

        System.out.println("\n----- Customer Details -----");
        System.out.println("Account No : " + accountNo);
        System.out.println("Name       : " + name);
        System.out.println("Phone      : " + phone);
        System.out.println("Email      : " + email);
        System.out.println("Address    : " + address);
        System.out.println("Balance    : " + balance);
        System.out.println("Account    : " + accountType);
    }
}