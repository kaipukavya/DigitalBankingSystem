package service;

public interface ATMService {

    void createAccount();

    boolean login();

    void checkBalance();

    void deposit();

    void withdraw();

    void transfer();

    void changePin();

    void viewProfile();

    void updateProfile();

    void miniStatement();

    void deleteAccount();

}