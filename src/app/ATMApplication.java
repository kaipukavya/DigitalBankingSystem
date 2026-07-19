package app;

import java.util.Scanner;

import service.ATMService;
import service.ATMServiceImpl;

public class ATMApplication {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ATMService atm = new ATMServiceImpl();

        while (true) {

            System.out.println("\n=================================");
            System.out.println("       ATM MANAGEMENT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();

            switch (choice) {

            case 1:
                atm.createAccount();
                break;

            case 2:

                boolean status = atm.login();

                if (status) {

                    while (true) {

                        System.out.println("\n=================================");
                        System.out.println("            ATM MENU");
                        System.out.println("=================================");
                        System.out.println("1. Check Balance");
                        System.out.println("2. Deposit");
                        System.out.println("3. Withdraw");
                        System.out.println("4. Transfer");
                        System.out.println("5. Change PIN");
                        System.out.println("6. View Profile");
                        System.out.println("7. Update Profile");
                        System.out.println("8. Mini Statement");
                        System.out.println("9. Delete Account");
                        System.out.println("10. Logout");
                        System.out.print("Enter Choice : ");

                        int ch = sc.nextInt();

                        switch (ch) {

                        case 1:
                            atm.checkBalance();
                            break;

                        case 2:
                            atm.deposit();
                            break;

                        case 3:
                            atm.withdraw();
                            break;

                        case 4:
                            atm.transfer();
                            break;

                        case 5:
                            atm.changePin();
                            break;

                        case 6:
                            atm.viewProfile();
                            break;

                        case 7:
                            atm.updateProfile();
                            break;

                        case 8:
                            atm.miniStatement();
                            break;

                        case 9:

                            atm.deleteAccount();

                            if (((ATMServiceImpl) atm).getCurrentCustomer() == null) {
                                break;
                            }

                            break;

                        case 10:

                        	System.out.println("Logged Out Successfully");

                        	((ATMServiceImpl)atm).setCurrentCustomer(null);

                        	break;

                        default:
                            System.out.println("Invalid Choice");
                        }

                        if (ch == 10 || ((ATMServiceImpl) atm).getCurrentCustomer() == null) {
                            break;
                        }
                    }
                }

                break;

            case 3:
                System.out.println("Thank You...");
                System.exit(0);

            default:
                System.out.println("Invalid Choice");
            }

        }

    }

}