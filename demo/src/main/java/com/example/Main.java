package com.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting the Bank Account application...");
        
        BankAccount account = new BankAccount(1000.0);
        System.out.println("Account created with initial balance: " + account.getBalance());
        
        try {
            account.deposit(500.0);
            System.out.println("Successful deposit. New balance: " + account.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Error depositing: " + e.getMessage());
        }
        
        try {
            account.withdraw(300.0);
            System.out.println("Withdrawal successful. New balance: " + account.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Error when withdrawing: " + e.getMessage());
        }
        
        BankAccount account2 = new BankAccount(200.0);
        System.out.println("Second account created with balance: " + account2.getBalance());
        
        try {
            account.transfer(account2, 400.0);
            System.out.println("Successful transfer:");
            System.out.println("Origin account balance: " + account.getBalance());
            System.out.println("Destination account balance: " + account2.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Transfer error: " + e.getMessage());
        }
    }
}