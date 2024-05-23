package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;
import p.Order;
public class Database {
    // URL pattern for database
	private static Model model;
	static String username =null;
    private static final String DB_URL = "jdbc:sqlite:application.db";


    public static Connection getConnection() throws SQLException {
        // DriverManager is the basic service for managing a set of JDBC drivers
        return DriverManager.getConnection(DB_URL);
    }

    public static ObservableList<Order> getDataOrders() throws SQLException {
    	ObservableList<Order> list = FXCollections.observableArrayList();
    	
    	
        String sql = "SELECT * FROM orders where username ='"+username+"'";  // Make sure the table name is correct

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Order(
                        rs.getString("date"),
                        rs.getString("price"),
                        rs.getString("status"),
                        rs.getString("username")
                ));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally show a message to the user
            // JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

        return list;
    }
    public static void setCurrentuser(String username1) {
    	username =username1;
    }
}