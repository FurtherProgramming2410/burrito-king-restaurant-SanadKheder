package dao;


import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import RestaurantStock.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;
public class Database {
    // URL pattern for database
	private static Model model;
	static String username =null;
    private static final String DB_URL = "jdbc:sqlite:application.db";


    public static Connection getConnection() throws SQLException {
        // DriverManager is the basic service for managing a set of JDBC drivers
        return DriverManager.getConnection(DB_URL);}

    
   //// Get data from database for orders///////////////// 
    public static ObservableList<Order> getDataOrders() throws SQLException {
    	ObservableList<Order> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM orders where username ='"+username+"'";  // Make sure the table name is correct
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                String date = rs.getString("date");
                String price = rs.getString("price");
                String status = rs.getString("status");
                String username = rs.getString("username");

                Order order = new Order(id, date, price, status, username);
                list.add(order);

               
            }
        } catch (SQLException e) {
            e.printStackTrace(); }
        
    
        return list;
    
        
    
    }
    
    
    
    
    public static void exportOrdersToCSV(String filePath,boolean id,boolean status,boolean price, boolean username,boolean date) {
        ObservableList<Order> order;
        try {
        	order = getDataOrders();
            FileWriter csvWriter = new FileWriter(filePath);

            // Write CSV header
            if(id) {  csvWriter.append("ID");}
            csvWriter.append(",");
            if(date) {  csvWriter.append("Date");}
            csvWriter.append(",");
            if(price) {  csvWriter.append("Price");}
            csvWriter.append(",");
            if(status) {   csvWriter.append("Status");}
            csvWriter.append(",");
            if(username) {   csvWriter.append("Username");}
            csvWriter.append("\n");

            // Write order data
            for (Order order1 : order) {
            if(id) {    csvWriter.append(String.valueOf(order1.getId()));}
                csvWriter.append(",");
                if(date) {   csvWriter.append(order1.getDate());}
                csvWriter.append(",");
                if(price) {    csvWriter.append(order1.getPrice());}
                csvWriter.append(",");
                if(status) {   csvWriter.append(order1.getStatus());}
                csvWriter.append(",");
                if(username) {   csvWriter.append(order1.getUsername());}
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
          
        } catch (SQLException | IOException e) {
        	System.out.print("Error");
            e.printStackTrace();
        }
    }

 

   
  

  //////////////////////////////  
    public static void setCurrentuser(String username1) {

    	    
    	    
    	username =username1; }}