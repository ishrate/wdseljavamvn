package com.ei.pwwdseljava.onlineinvoice.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ei.pwwdseljava.onlineinvoice.config.ConfigProperties;

public class OracleDBUtil {

    public static Connection getConnection() throws SQLException {
    	 System.setProperty("oracle.net.tns_admin", "C:\\3PP\\Wallet_AUTSELWEBJAVEXT");
        String url = ConfigProperties.getProperty("db.url");
        String username = ConfigProperties.getProperty("db.username");
        String password = ConfigProperties.getProperty("db.password");
        return DriverManager.getConnection(url, username, password);
    }

    public static List<String> executeQuery(String query) {
        List<String> results = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                results.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
    
    public static List<String> executeInvoiceQuery(String query) {
        List<String> results = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
            	 String status = resultSet.getString("Invoice_Status");
                 double amount = resultSet.getDouble("Invoice_Amount");
                 
                 System.out.println("Invoice Status: " + status);
                 System.out.println("Invoice Amount: " + amount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
    
    public static List<String> getInvoiceDataById(int invoiceId) {
        List<String> result = new ArrayList<>();
        String query = "SELECT Invoice_Status, Invoice_Amount FROM Invoice WHERE Invoice_ID = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the Invoice ID in the query
            stmt.setInt(1, invoiceId);

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String invoiceStatus = rs.getString("Invoice_Status");
                    double invoiceAmount = rs.getDouble("Invoice_Amount");

                    // Add results to list
                    result.add("Invoice Status: " + invoiceStatus);
                    result.add("Invoice Amount: " + invoiceAmount);
                } else {
                    result.add("No invoice found with ID: " + invoiceId);
                }
                connection.close();  // Close connection
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}

