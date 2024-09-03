package org.jsp.atm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Bank {
	String bname;
	String loc;
	String ifsc;
	Account a;
	public Bank(String bname, String loc, String ifsc) {
		super();
		this.bname = bname;
		this.loc = loc;
	
		this.ifsc = ifsc;
	}
	
	// to create new Account
	public void createAccountInDB(Account account) {
        String sql = "INSERT INTO accounts (account_number, account_holder_name, dob, pin, balance) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = TestConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, account.ano);
            stmt.setString(2, account.account_holder_name);
            stmt.setString(3, account.dob);
            stmt.setString(4, account.pin);
            stmt.setDouble(5, account.money);
            
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Account created successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to read account details from DB
    public Account getAccountFromDB(long accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        
        try (Connection conn = TestConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String name = rs.getString("account_holder_name");
                String dob = rs.getString("dob");
                String pin = rs.getString("pin");
                double balance = rs.getDouble("balance");
                
                return new Account(name, dob, pin, balance, accountNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to update account balance in DB
    public void updateBalanceInDB(long accountNumber, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        
        try (Connection conn = TestConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, newBalance);
            stmt.setLong(2, accountNumber);
            
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Account balance updated successfully!");
                System.out.println("Total balance :" +newBalance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // method to log old transactions
    public void logTransaction(long accountNumber, String transactionType, double amount) {
        try (Connection connection = TestConnection.getConnection()) {
            String query = "INSERT INTO transactions (account_number, transaction_type, amount, timestamp) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, accountNumber);
            statement.setString(2, transactionType);
            statement.setDouble(3, amount);
            statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Transaction recorded successfully!");
            } else {
                System.out.println("Failed to record the transaction.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    public void displayTransactionHistory(long accountNumber) {
        try (Connection connection = TestConnection.getConnection()) {
            String query = "SELECT account_number, transaction_type, amount, timestamp FROM transactions WHERE account_number = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();

            boolean hasTransactions = false;
            System.out.println("Transaction History for Account Number: " + accountNumber);
            System.out.println("======================================");

            while (resultSet.next()) {
                hasTransactions = true;
                long accNum = resultSet.getLong("account_number");
                String transactionType = resultSet.getString("transaction_type");
                double amount = resultSet.getDouble("amount");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");

                Transaction transaction = new Transaction(accNum, transactionType, amount, timestamp);
                transaction.displayTransaction();
            }

            if (!hasTransactions) {
                System.out.println("No transactions found for account number: " + accountNumber);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    // Method to retrieve transaction history for an account
    public List<Transaction> getTransactionHistory(long accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = TestConnection.getConnection()) {
            String query = "SELECT * FROM transactions WHERE account_number = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, accountNumber);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long accountNum = resultSet.getLong("account_number");
                String transactionType = resultSet.getString("transaction_type");
                double amount = resultSet.getDouble("amount");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");

                Transaction transaction = new Transaction(accountNum, transactionType, amount, timestamp);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // Method to delete an account from DB
    public boolean deleteAccountFromDB(long accountNumber) {
        String sql = "DELETE FROM accounts WHERE account_number = ?";
        
        try (Connection conn = TestConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, accountNumber);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Account deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
    }


    public void Withdraw(long ano, double money) {
        if (a.ano == ano) {
            if (a.money >= money) {
                a.money = a.money - money;
                System.out.println("Withdrawn amount: " + money);
                System.out.println("Total Balance: " + a.money);
                
                // Log the transaction in the database
                logTransaction(ano, "Withdraw", money);
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Enter a valid account number.");
        }
    }

	
	public void Deposit(long ano, double money) {
	    if (a.ano == ano) {
	        if (money != 0) {
	            a.money = a.money + money;
	            System.out.println("Deposited amount: " + money);
	            System.out.println("Total Balance: " + a.money);
	            
	            // Log the transaction in the database
	            logTransaction(ano, "Deposit", money);
	        } else {
	            System.out.println("Enter a valid amount to deposit.");
	        }
	    } else {
	        System.out.println("Enter a valid account number.");
	    }
	}

}




