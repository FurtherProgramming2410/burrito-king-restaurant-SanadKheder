package RestaurantStock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import dao.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Model;

public class Restaurant {
	private static int time = 0;
	private static double total;
	private ArrayList<Order> order;
	private static Burrito item1 = new Burrito("Burrito", 7.00);
	private static Fries item2 = new Fries(9, "Fries", 4.00);
	private static Sodas item3 = new Sodas(99, "Sodas", 2.50);

	
	
//Calculator Price////////// ////////////////////////////
	public static double CalculatorPrice(double fqty, double bqty, double sqty, double meal,double discount) {	
		total = (fqty * getItem2().getPrice()) + (bqty * getItem1().getPrice()) + (sqty * getItem3().getPrice())
				+ (meal * 10.5)-discount;
		return total;}

	//////////////////////////////////////////////////////////
	public Restaurant() {
		order = new ArrayList<Order>();}
	//////////////////////////////////////////////
	 

	
/////checkout/////////////////////////////////////////
	public void CheckOut(int friQty, int sodasQty, int burrQty, String username,double credit,String VIP) {
/////check if user is VIP or No to add credit		
		if(VIP.equals("VIP")) {
		credit=total/100+credit;
	 String sql2 = "UPDATE users SET credit = '"+credit+"' WHERE username = '"+username+ "'";
	 try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql2);) {
			stmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}} 
///insert data of order to database	 
		String sql = "INSERT INTO orders (date,price,status,username) VALUES ( ? ,?, ?,?)";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, String.valueOf(LocalDateTime.now()));
			stmt.setString(2, String.valueOf(total));
			if (time == 0) {
				stmt.setString(3, "Collected");
			} else {
				stmt.setString(3, "Await for collection");
			}
			stmt.setString(4, username);
			stmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		total = 0;
		time = 0;
	}

	
	
	
/////adding time 
	public static int addTime(int burrQty, int friQty) {
		//// burritos///////////
		time = 0;
		if (burrQty > 2) {
			for (int i = burrQty; i > 0; i = i - 2) {
				time = time + 9;}
		} else if (burrQty > 0 && burrQty <= 2) {
			time = time + 9;}
		//////// sodas
		if (friQty > getItem2().getQuantity()) {
			int friOrder = friQty - getItem2().getQuantity();
			while (friOrder > 0) {
				time = time + 8;
				friOrder = friOrder - 5;	}
			getItem2().setQuantity(0);
		} else {
			int nextOrder = getItem2().getQuantity() - friQty;
			getItem2().setQuantity(getItem2().getQuantity() - friQty);
		}
		return time;}



	public static Fries getItem2() {
		return item2;}
	public static Burrito getItem1() {
		return item1;}
	public static Sodas getItem3() {
		return item3;}

}
