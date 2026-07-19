package service;
import java.util.Scanner;
import util.AccountGenerator;
import dao.CustomerDAO;
import model.Customer;
import dao.TransactionDAO;
public class ATMServiceImpl implements ATMService {
	 private int loggedInAccountNo;
	 private Customer currentCustomer;

    @Override
    public void createAccount() {

        Scanner sc = new Scanner(System.in);

        Customer customer = new Customer();

        int accountNo = AccountGenerator.generateAccountNumber();

        customer.setAccountNo(accountNo);

        System.out.println("--------------------------------");
        System.out.println("Generated Account Number : " + accountNo);
        System.out.println("--------------------------------");

        System.out.println("Enter Name:");
        customer.setName(sc.nextLine());

        System.out.println("Enter Phone:");
        customer.setPhone(sc.nextLine());

        System.out.println("Enter Email:");
        customer.setEmail(sc.nextLine());

        System.out.println("Enter Address:");
        customer.setAddress(sc.nextLine());

        System.out.println("Enter PIN:");
        customer.setPin(sc.nextInt());

        System.out.println("Enter Opening Balance:");
        customer.setBalance(sc.nextDouble());
        sc.nextLine(); 

        System.out.println("Enter Account Type (SAVINGS/CURRENT):");
        customer.setAccountType(sc.nextLine());

        CustomerDAO dao = new CustomerDAO();

        boolean status = dao.createAccount(customer);

        if (status) {
            System.out.println("Account Created Successfully...");
        } else {
            System.out.println("Account Creation Failed...");
        }
    }

    @Override
    public boolean login() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Account Number : ");
        int accountNo = sc.nextInt();

        System.out.print("Enter PIN : ");
        int pin = sc.nextInt();

        CustomerDAO dao = new CustomerDAO();

        currentCustomer = dao.loginCustomer(accountNo, pin);

        if (currentCustomer != null) {

            System.out.println("\nLogin Successful...");
            System.out.println("Welcome " + currentCustomer.getName());

            return true;

        } else {

            System.out.println("Invalid Account Number or PIN");
            return false;
        }
    }

    public void setCurrentCustomer(Customer customer) {

        this.currentCustomer = customer;

    }
    
    public Customer getCurrentCustomer() {
        return currentCustomer;
    }
    
    @Override
    public void checkBalance() {

        CustomerDAO dao = new CustomerDAO();

        double balance = dao.checkBalance(currentCustomer.getAccountNo());

        currentCustomer.setBalance(balance);

        System.out.println("----------------------------------");
        System.out.println("Account Number : " + currentCustomer.getAccountNo());
        System.out.println("Account Holder : " + currentCustomer.getName());
        System.out.println("Current Balance : ₹" + balance);
        System.out.println("----------------------------------");

    }

    @Override
    public void deposit() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Amount : ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid Amount");
            return;
        }

        CustomerDAO cdao = new CustomerDAO();

        boolean status = cdao.deposit(currentCustomer.getAccountNo(), amount);

        if (status) {

            double balance = cdao.checkBalance(currentCustomer.getAccountNo());

            currentCustomer.setBalance(balance);

            TransactionDAO tdao = new TransactionDAO();

            tdao.addTransaction(currentCustomer.getAccountNo(),
                                "DEPOSIT",
                                amount,
                                balance);

            System.out.println("Amount Deposited Successfully");
            System.out.println("Current Balance : " + balance);

        } else {

            System.out.println("Deposit Failed");

        }

    }

    
    
    @Override
    public void withdraw() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Amount : ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid Amount");
            return;
        }

        CustomerDAO cdao = new CustomerDAO();

        double balance = cdao.checkBalance(currentCustomer.getAccountNo());

        if (amount > balance) {

            System.out.println("Insufficient Balance");
            return;

        }

        boolean status = cdao.withdraw(currentCustomer.getAccountNo(), amount);

        if (status) {

            balance = cdao.checkBalance(currentCustomer.getAccountNo());

            currentCustomer.setBalance(balance);

            TransactionDAO tdao = new TransactionDAO();

            tdao.addTransaction(currentCustomer.getAccountNo(),
                                "WITHDRAW",
                                amount,
                                balance);

            System.out.println("Withdrawal Successful");
            System.out.println("Current Balance : " + balance);

        } else {

            System.out.println("Withdrawal Failed");

        }

    }
    
    
    @Override
    public void transfer() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Receiver Account Number : ");
        int receiver = sc.nextInt();

        System.out.print("Enter Amount : ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid Amount");
            return;
        }

        CustomerDAO cdao = new CustomerDAO();

        if (!cdao.accountExists(receiver)) {

            System.out.println("Receiver Account Not Found");
            return;

        }

        double balance = cdao.checkBalance(currentCustomer.getAccountNo());

        if (amount > balance) {

            System.out.println("Insufficient Balance");
            return;

        }

        boolean status = cdao.transfer(currentCustomer.getAccountNo(),
                                       receiver,
                                       amount);

        if (status) {

            double senderBalance = cdao.checkBalance(currentCustomer.getAccountNo());

            currentCustomer.setBalance(senderBalance);

            TransactionDAO tdao = new TransactionDAO();

            tdao.addTransaction(currentCustomer.getAccountNo(),
                                "TRANSFER",
                                amount,
                                senderBalance);

            double receiverBalance = cdao.checkBalance(receiver);

            tdao.addTransaction(receiver,
                                "RECEIVED",
                                amount,
                                receiverBalance);

            System.out.println("Transfer Successful");
            System.out.println("Current Balance : " + senderBalance);

        } else {

            System.out.println("Transfer Failed");

        }

    }
    
    @Override
    public void changePin() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Old PIN : ");
        int oldPin = sc.nextInt();

        if (oldPin != currentCustomer.getPin()) {

            System.out.println("Incorrect Old PIN");
            return;

        }

        System.out.print("Enter New PIN : ");
        int newPin = sc.nextInt();

        if (newPin < 1000 || newPin > 9999) {

            System.out.println("PIN must be 4 digits");
            return;

        }

        System.out.print("Confirm New PIN : ");
        int confirmPin = sc.nextInt();

        if (newPin != confirmPin) {

            System.out.println("PIN Mismatch");
            return;

        }

        CustomerDAO dao = new CustomerDAO();

        boolean status = dao.changePin(currentCustomer.getAccountNo(), newPin);

        if (status) {

            currentCustomer.setPin(newPin);

            System.out.println("PIN Changed Successfully");

        } else {

            System.out.println("PIN Change Failed");

        }

    }
    
    @Override
    public void viewProfile() {

        CustomerDAO dao = new CustomerDAO();

        Customer customer =
                dao.getCustomer(currentCustomer.getAccountNo());

        if (customer != null) {

            System.out.println("\n========== PROFILE ==========");

            System.out.println("Account No   : " + customer.getAccountNo());
            System.out.println("Name         : " + customer.getName());
            System.out.println("Phone        : " + customer.getPhone());
            System.out.println("Email        : " + customer.getEmail());
            System.out.println("Address      : " + customer.getAddress());
            System.out.println("Balance      : " + customer.getBalance());
            System.out.println("Account Type : " + customer.getAccountType());

        } else {

            System.out.println("Customer Not Found");

        }

    }

    @Override
    public void updateProfile() {
    	Scanner sc=new Scanner(System.in);

        Customer customer = new Customer();

        customer.setAccountNo(currentCustomer.getAccountNo());

        sc.nextLine();

        System.out.print("Enter New Name : ");
        customer.setName(sc.nextLine());

        System.out.print("Enter New Phone : ");
        customer.setPhone(sc.nextLine());

        System.out.print("Enter New Email : ");
        customer.setEmail(sc.nextLine());

        System.out.print("Enter New Address : ");
        customer.setAddress(sc.nextLine());

        CustomerDAO dao = new CustomerDAO();

        boolean status = dao.updateProfile(customer);

        if (status) {
            currentCustomer = dao.getCustomer(currentCustomer.getAccountNo());
            System.out.println("Profile Updated Successfully");
        } else {
            System.out.println("Profile Update Failed");
        }
    }

    @Override
    public void miniStatement() {

        TransactionDAO dao = new TransactionDAO();

        dao.miniStatement(currentCustomer.getAccountNo());

    }

    @Override
    public void deleteAccount() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Are you sure you want to delete your account? (YES/NO): ");
        String choice = sc.next();

        if (!choice.equalsIgnoreCase("YES")) {

            System.out.println("Account Deletion Cancelled");
            return;

        }

        CustomerDAO dao = new CustomerDAO();

        boolean status = dao.deleteAccount(currentCustomer.getAccountNo());

        if (status) {

            System.out.println("Account Deleted Successfully");

            currentCustomer = null;

        } else {

            System.out.println("Account Deletion Failed");

        }

    }

}