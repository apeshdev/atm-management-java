package org.jsp.atm;

import java.util.Scanner;

public class Bank_Driver {
    // Super Admin credentials
    private static final String SUPER_ADMIN_USERNAME = "admin";
    private static final String SUPER_ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean atmSession = true;

        while (atmSession) {
            System.out.println("Welcome to the ATM System!");

            System.out.println("Enter bank name to open account:");
            String bname = sc.nextLine();  // Changed to nextLine() to handle spaces
            System.out.println("Enter address of the bank:");
            String loc = sc.nextLine();  // Changed to nextLine() to handle spaces
            System.out.println("Enter bank IFSC code:");
            String ifsc = sc.nextLine();  // Changed to nextLine() to handle spaces
            Bank b = new Bank(bname, loc, ifsc);

            boolean option = true;
            while (option) {
            	System.out.println("Enter your choice:");
                System.out.println("1. Super Admin: Create account");
                System.out.println("2. Super Admin: Delete account");
                System.out.println("3. Check account details");
                System.out.println("4. Deposit money");
                System.out.println("5. Withdraw money");
                System.out.println("6. View transaction history");
                System.out.println("7. Exit");


                int choice = sc.nextInt();
                sc.nextLine(); // Consume the remaining newline character

                switch (choice) {
                    case 1: {
                        // Super Admin Authentication for Account Creation
                        System.out.println("Enter Super Admin Username:");
                        String username = sc.nextLine();
                        System.out.println("Enter Super Admin Password:");
                        String password = sc.nextLine();

                        if (authenticateSuperAdmin(username, password)) {
                            System.out.println("Authentication Successful! Proceed to create an account.");

                            System.out.println("Enter full name:");
                            String name = sc.nextLine();  // Use nextLine() to capture full name
                            System.out.println("Enter dob (YYYY-MM-DD):");
                            String dob = sc.nextLine();  // Changed to nextLine() to handle spaces
                            System.out.println("Enter pin:");
                            String pin = sc.nextLine();  // Changed to nextLine() to handle spaces
                            System.out.println("Enter money to deposit:");
                            double money = sc.nextDouble();
                            sc.nextLine(); // Consume the remaining newline character
                            System.out.println("Enter the account number:");
                            long account_number = sc.nextLong();
                            sc.nextLine(); // Consume the remaining newline character

                            b.createAccountInDB(new Account(name, dob, pin, money, account_number));
                            System.out.println("================================");
                        } else {
                            System.out.println("Authentication failed. Only Super Admin can create accounts.");
                        }
                    }
                    break;
                    case 2: {
                        // Super Admin Authentication for Account Deletion
                        System.out.println("Enter Super Admin Username:");
                        String username = sc.nextLine();
                        System.out.println("Enter Super Admin Password:");
                        String password = sc.nextLine();

                        if (authenticateSuperAdmin(username, password)) {
                            System.out.println("Authentication Successful! Proceed to delete an account.");

                            System.out.println("Enter the account number to delete:");
                            long account_number = sc.nextLong();
                            sc.nextLine(); // Consume the remaining newline character

                            boolean deleted = b.deleteAccountFromDB(account_number);
                            if (deleted) {
                                System.out.println("Account deleted successfully.");
                            } else {
                                System.out.println("No account found with this account number.");
                            }
                            System.out.println("================================");
                        } else {
                            System.out.println("Authentication failed. Only Super Admin can delete accounts.");
                        }
                    }
                    break;
                    case 3: {
                        System.out.println("Enter your account number:");
                        long accountNumber = sc.nextLong();
                        sc.nextLine(); // Consume the remaining newline character

                        Account account = b.getAccountFromDB(accountNumber);
                        if (account != null) {
                            account.accountDetails();
                        } else {
                            System.out.println("No account found with this account number.");
                        }
                        System.out.println("=================================");
                    }
                    break;
                    case 4: {
                        System.out.println("Enter account number to deposit money:");
                        long ano1 = sc.nextLong();
                        System.out.println("Enter how much money you want to deposit:");
                        double money1 = sc.nextDouble();
                        sc.nextLine(); // Consume the remaining newline character

                        Account account = b.getAccountFromDB(ano1);
                        if (account != null) {
                            account.money += money1;
                            b.updateBalanceInDB(ano1, account.money);
                        } else {
                            System.out.println("Account not found.");
                        }
                        System.out.println("=================================");
                    }
                    break;
                    case 5: {
                        System.out.println("Enter account number to withdraw money:");
                        long ano1 = sc.nextLong();
                        System.out.println("Enter how much money you want to withdraw:");
                        double money1 = sc.nextDouble();
                        sc.nextLine(); // Consume the remaining newline character

                        Account account = b.getAccountFromDB(ano1);
                        if (account != null) {
                            if (account.money >= money1) {
                                account.money -= money1;
                                b.updateBalanceInDB(ano1, account.money);
                            } else {
                                System.out.println("Insufficient balance.");
                            }
                        } else {
                            System.out.println("Account not found.");
                        }
                        System.out.println("=================================");
                    }
                    break;
                    case 6: {
                        System.out.println("Enter your account number to view transaction history:");
                        long accNumber = sc.nextLong();
                        b.displayTransactionHistory(accNumber);
                        System.out.println("================================");
                    }
                    break;

                    case 7: {
                        System.out.println("Thank you for using " + bname + "!");
                        option = false;
                    }
                    break;
                    default: {
                        System.out.println("Enter a valid choice.");
                    }
                    break;
                }
            }

            System.out.println("Would you like to enter into the ATM again? (yes/no):");
            String continueATM = sc.nextLine().trim().toLowerCase();
            if (!continueATM.equals("yes")) {
                atmSession = false;
                System.out.println("Goodbye!");
            }
        }

        sc.close();
    }

    // Method to authenticate Super Admin
    private static boolean authenticateSuperAdmin(String username, String password) {
        return SUPER_ADMIN_USERNAME.equals(username) && SUPER_ADMIN_PASSWORD.equals(password);
    }
}
