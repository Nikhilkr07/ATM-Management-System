package com.customer.transaction;

import com.customer.connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Banking {
    DataBaseConnection connection = new DataBaseConnection();

    public String withdrawAmount(long atmNo, int pin, int cusId, double amount) throws Exception {
        Connection con = null;
        try {
            con = connection.getCon();

            //Validate the ATM PIN
            String validatePinQuery = "SELECT atmPin, totalAmount FROM customerAtmCard WHERE atmNo = ? AND cusId = ?";
            PreparedStatement psValidatePin = con.prepareStatement(validatePinQuery);
            psValidatePin.setLong(1, atmNo);
            psValidatePin.setInt(2, cusId);
            ResultSet rsPin = psValidatePin.executeQuery();

            if (rsPin.next()) {
                int storedPin = rsPin.getInt("atmPin");
                double atmCardBalance = rsPin.getDouble("totalAmount");

                if (storedPin != pin) {
                    return "Invalid PIN";
                }

                //Check if the customer has sufficient balance
                String checkCustomerBalance = "SELECT totalAmount FROM customer WHERE cusId = ?";
                PreparedStatement psCheckCustomerBalance = con.prepareStatement(checkCustomerBalance);
                psCheckCustomerBalance.setInt(1, cusId);
                ResultSet rsCustomer = psCheckCustomerBalance.executeQuery();

                if (rsCustomer.next()) {
                    double customerBalance = rsCustomer.getDouble("totalAmount");

                    if (customerBalance >= amount && atmCardBalance >= amount) {
                        // Update the customer balance
                        String updateCustomerBalance = "UPDATE customer SET totalAmount = totalAmount - ? WHERE cusId = ?";
                        PreparedStatement psUpdateCustomerBalance = con.prepareStatement(updateCustomerBalance);
                        psUpdateCustomerBalance.setDouble(1, amount);
                        psUpdateCustomerBalance.setInt(2, cusId);
                        psUpdateCustomerBalance.executeUpdate();

                        // Update the ATM card balance
                        String updateAtmCardBalance = "UPDATE customerAtmCard SET totalAmount = totalAmount - ? WHERE cusId = ? AND atmNo = ?";
                        PreparedStatement psUpdateAtmCardBalance = con.prepareStatement(updateAtmCardBalance);
                        psUpdateAtmCardBalance.setDouble(1, amount);
                        psUpdateAtmCardBalance.setInt(2, cusId);
                        psUpdateAtmCardBalance.setLong(3, atmNo);
                        psUpdateAtmCardBalance.executeUpdate();

                        // Insert the transaction record
                        String insertTransaction = "INSERT INTO transaction (cusId, atmNo, transactionType, transactionAmount, remainingBalance) "
                                + "VALUES (?, ?, 'withdraw', ?, ?)";
                        PreparedStatement psInsertTransaction = con.prepareStatement(insertTransaction);
                        psInsertTransaction.setInt(1, cusId);
                        psInsertTransaction.setLong(2, atmNo);
                        psInsertTransaction.setDouble(3, amount);
                        psInsertTransaction.setDouble(4, customerBalance - amount);
                        psInsertTransaction.executeUpdate();

                        return "Withdrawal successful! Withdrawn: " + amount;
                    } else {
                        return "Insufficient balance.";
                    }
                } else {
                    return "Customer not found.";
                }
            } else {
                return "Invalid ATM card or customer ID.";
            }
        } catch (SQLException e) {
            throw new Exception("An error occurred during withdrawal: " + e.getMessage());
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }


    public String addAmount(long atmNo, int pin, int cusId, double amount) throws Exception {
        Connection con = null;
        try {
            con = connection.getCon();

            // Step 1: Validate the ATM PIN
            String validatePinQuery = "SELECT atmPin FROM customerAtmCard WHERE atmNo = ? AND cusId = ?";
            PreparedStatement psValidatePin = con.prepareStatement(validatePinQuery);
            psValidatePin.setLong(1, atmNo);
            psValidatePin.setInt(2, cusId);
            ResultSet rsPin = psValidatePin.executeQuery();

            if (rsPin.next()) {
                int storedPin = rsPin.getInt("atmPin");

                if (storedPin != pin) {
                    return "Invalid PIN";
                }

                //Update the customer balance
                String updateCustomerBalance = "UPDATE customer SET totalAmount = totalAmount + ? WHERE cusId = ?";
                PreparedStatement psUpdateCustomerBalance = con.prepareStatement(updateCustomerBalance);
                psUpdateCustomerBalance.setDouble(1, amount);
                psUpdateCustomerBalance.setInt(2, cusId);
                psUpdateCustomerBalance.executeUpdate();

                //Update the ATM card balance
                String updateAtmCardBalance = "UPDATE customerAtmCard SET totalAmount = totalAmount + ? WHERE cusId = ? AND atmNo = ?";
                PreparedStatement psUpdateAtmCardBalance = con.prepareStatement(updateAtmCardBalance);
                psUpdateAtmCardBalance.setDouble(1, amount);
                psUpdateAtmCardBalance.setInt(2, cusId);
                psUpdateAtmCardBalance.setLong(3, atmNo);
                psUpdateAtmCardBalance.executeUpdate();

                //Insert the transaction record
                String insertTransaction = "INSERT INTO transaction (cusId, atmNo, transactionType, transactionAmount, remainingBalance) "
                        + "VALUES (?, ?, 'deposit', ?, (SELECT totalAmount FROM customer WHERE cusId = ?))";
                PreparedStatement psInsertTransaction = con.prepareStatement(insertTransaction);
                psInsertTransaction.setInt(1, cusId);
                psInsertTransaction.setLong(2, atmNo);
                psInsertTransaction.setDouble(3, amount);
                psInsertTransaction.setInt(4, cusId);  // For remainingBalance
                psInsertTransaction.executeUpdate();

                return "Deposit successful! Deposited: " + amount;
            } else {
                return "Invalid ATM card or customer ID.";
            }
        } catch (SQLException e) {
            throw new Exception("An error occurred during deposit: " + e.getMessage());
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }


    public String changePin(long atmNo, int currentPin) throws Exception {
        Scanner sc = new Scanner(System.in);
        Connection con = null;
        try {
            con = connection.getCon();
            String validatePinQuery = "SELECT atmPin FROM customerAtmCard WHERE atmNo = ? AND atmPin = ?";
            PreparedStatement psValidatePin = con.prepareStatement(validatePinQuery);
            psValidatePin.setLong(1, atmNo);
            psValidatePin.setInt(2, currentPin);
            ResultSet rs = psValidatePin.executeQuery();

            if (rs.next()) {
                System.out.print("Enter New Pin: ");
                int newPin = sc.nextInt();
                System.out.print("Confirm Pin: ");
                int confirmPin = sc.nextInt();

                if (newPin == confirmPin && String.valueOf(newPin).length() == 4) {
                    // Update the PIN in the database
                    String updatePinQuery = "UPDATE customerAtmCard SET atmPin = ? WHERE atmNo = ? AND atmPin = ?";
                    PreparedStatement psUpdatePin = con.prepareStatement(updatePinQuery);
                    psUpdatePin.setInt(1, newPin);
                    psUpdatePin.setLong(2, atmNo);
                    psUpdatePin.setInt(3, currentPin);
                    System.out.println("You are Sure: ");
                    System.out.println("1.yes     2.NO");
                    int sure = sc.nextInt();
                    if(sure == 1) {
                        psUpdatePin.executeUpdate();
                        return "PIN changed successfully!";
                    }
                    else {
                        return "Trasaction failed pin not change!";
                    }

                } else {
                    return "New PIN and confirmation PIN do not match or invalid PIN length.";
                }
            } else {
                return "Invalid ATM number or PIN.";
            }
        } catch (SQLException e) {
            throw new Exception("An error occurred during PIN change: " + e.getMessage());
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public String transactionDetails(long atmNo, int pin) throws Exception {
        Connection con = connection.getCon();
        System.out.println("call");
        // Validate ATM number and PIN
        String validateQuery = "SELECT totalAmount FROM customerAtmCard WHERE atmNo = ? AND atmPin = ?";
        PreparedStatement psValidate = con.prepareStatement(validateQuery);
        psValidate.setLong(1, atmNo);
        psValidate.setInt(2, pin);
        ResultSet rsValidate = psValidate.executeQuery();

        if (!rsValidate.next()) {
            return "Invalid ATM number or PIN.";
        }

        // Retrieve transaction details
        StringBuilder transactionHistory = new StringBuilder();
        String transactionQuery = "SELECT transactionId, transactionType, transactionAmount, transactionDate, remainingBalance, receiverAtmNo "
                + "FROM transaction WHERE atmNo = ?";
        PreparedStatement psTransaction = con.prepareStatement(transactionQuery);
        psTransaction.setLong(1, atmNo);
        ResultSet rsTransaction = psTransaction.executeQuery();

        transactionHistory.append("Transaction History for ATM No: ").append(atmNo).append("\n");
        transactionHistory.append("-----------------------------------------------------------\n");
        transactionHistory.append("ID | Type      | Amount   | Date       | Balance   | Receiver ATM\n");
        transactionHistory.append("-----------------------------------------------------------\n");

        while (rsTransaction.next()) {
            int transactionId = rsTransaction.getInt("transactionId");
            String transactionType = rsTransaction.getString("transactionType");
            double transactionAmount = rsTransaction.getDouble("transactionAmount");
            String transactionDate = rsTransaction.getString("transactionDate");
            double remainingBalance = rsTransaction.getDouble("remainingBalance");
            long receiverAtmNo = rsTransaction.getLong("receiverAtmNo");

            transactionHistory.append(transactionId)
                    .append(" | ")
                    .append(transactionType)
                    .append(" | ")
                    .append(transactionAmount)
                    .append(" | ")
                    .append(transactionDate)
                    .append(" | ")
                    .append(remainingBalance)
                    .append(" | ")
                    .append(receiverAtmNo == 0 ? "N/A" : receiverAtmNo)
                    .append("\n");
        }

        if (transactionHistory.length() == 0) {
            return "No transactions found for ATM No: " + atmNo;
        }

        return transactionHistory.toString();
    }


}


