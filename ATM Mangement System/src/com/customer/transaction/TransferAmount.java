package com.customer.transaction;

import com.customer.connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransferAmount {
    DataBaseConnection connection = new DataBaseConnection();
    public String transferAmount(long senderAtm, int pin, long receiverAtm, double amount) throws Exception {
        Connection con = connection.getCon();

        // Validate sender's ATM and PIN
        String checkSender = "SELECT cac.totalAmount, cac.cusId FROM customerAtmCard cac WHERE atmNo = ? AND atmPin = ?";
        PreparedStatement psSender = con.prepareStatement(checkSender);
        psSender.setLong(1, senderAtm);
        psSender.setInt(2, pin);
        ResultSet rsSender = psSender.executeQuery();

        if (rsSender.next()) {
            double senderBalance = rsSender.getDouble("totalAmount");
            int senderCusId = rsSender.getInt("cusId");

            // Check if sender has enough balance
            if (senderBalance < amount) {
                return "Insufficient balance.";
            }

            // Validate receiver's ATM
            String checkReceiver = "SELECT cac.totalAmount, cac.cusId FROM customerAtmCard cac WHERE atmNo = ?";
            PreparedStatement psReceiver = con.prepareStatement(checkReceiver);
            psReceiver.setLong(1, receiverAtm);
            ResultSet rsReceiver = psReceiver.executeQuery();

            if (rsReceiver.next()) {
                double receiverBalance = rsReceiver.getDouble("totalAmount");
                int receiverCusId = rsReceiver.getInt("cusId");

                // Update sender's balance in customerAtmCard
                String updateSenderAtm = "UPDATE customerAtmCard SET totalAmount = totalAmount - ? WHERE atmNo = ?";
                PreparedStatement psUpdateSenderAtm = con.prepareStatement(updateSenderAtm);
                psUpdateSenderAtm.setDouble(1, amount);
                psUpdateSenderAtm.setLong(2, senderAtm);
                psUpdateSenderAtm.executeUpdate();

                // Update receiver's balance in customerAtmCard
                String updateReceiverAtm = "UPDATE customerAtmCard SET totalAmount = totalAmount + ? WHERE atmNo = ?";
                PreparedStatement psUpdateReceiverAtm = con.prepareStatement(updateReceiverAtm);
                psUpdateReceiverAtm.setDouble(1, amount);
                psUpdateReceiverAtm.setLong(2, receiverAtm);
                psUpdateReceiverAtm.executeUpdate();

                // Update sender's balance in customer table
                String updateSenderCustomer = "UPDATE customer SET totalAmount = totalAmount - ? WHERE cusId = ?";
                PreparedStatement psUpdateSenderCustomer = con.prepareStatement(updateSenderCustomer);
                psUpdateSenderCustomer.setDouble(1, amount);
                psUpdateSenderCustomer.setInt(2, senderCusId);
                psUpdateSenderCustomer.executeUpdate();

                // Update receiver's balance in customer table
                String updateReceiverCustomer = "UPDATE customer SET totalAmount = totalAmount + ? WHERE cusId = ?";
                PreparedStatement psUpdateReceiverCustomer = con.prepareStatement(updateReceiverCustomer);
                psUpdateReceiverCustomer.setDouble(1, amount);
                psUpdateReceiverCustomer.setInt(2, receiverCusId);
                psUpdateReceiverCustomer.executeUpdate();

                // Log sender's transaction
                String logSender = "INSERT INTO transaction (cusId, atmNo, transactionType, transactionAmount, remainingBalance, receiverAtmNo) "
                        + "VALUES (?, ?, 'transfer', ?, ?, ?)";
                PreparedStatement psLogSender = con.prepareStatement(logSender);
                psLogSender.setInt(1, senderCusId);
                psLogSender.setLong(2, senderAtm);
                psLogSender.setDouble(3, amount);
                psLogSender.setDouble(4, senderBalance - amount);
                psLogSender.setLong(5, receiverAtm);
                psLogSender.executeUpdate();

                // Log receiver's transaction
                String logReceiver = "INSERT INTO transaction (cusId, atmNo, transactionType, transactionAmount, remainingBalance, receiverAtmNo) "
                        + "VALUES (?, ?, 'received', ?, ?, ?)";
                PreparedStatement psLogReceiver = con.prepareStatement(logReceiver);
                psLogReceiver.setInt(1, receiverCusId);
                psLogReceiver.setLong(2, receiverAtm);
                psLogReceiver.setDouble(3, amount);
                psLogReceiver.setDouble(4, receiverBalance + amount);
                psLogReceiver.setLong(5, senderAtm);
                psLogReceiver.executeUpdate();

                return "Transfer successful.";
            } else {
                return "Receiver ATM number is invalid.";
            }
        } else {
            return "Invalid sender ATM number or PIN.";
        }
    }


}
