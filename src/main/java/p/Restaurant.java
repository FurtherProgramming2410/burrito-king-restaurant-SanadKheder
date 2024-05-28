package p;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
	private int friQty ,sodasQty,burrQty;
	private static int time=0;
	private static	double total;	
	private ArrayList<Order> order ;
	private static double meal1;
	private static  Burrito item1 = new Burrito("Burrito",7.00);
	private static Fries  item2 = new Fries(9,"Fries",4.00);
	private static Sodas  item3 = new Sodas(99,"Sodas",2.50);
	
public static double CalculatorPriceFries (double fqty, double bqty,double sqty,double meal ) {
	meal1=meal;
	 total = (fqty*getItem2().getPrice())+(bqty*getItem1().getPrice())+(sqty*getItem3().getPrice())+( meal*10.5);
	return total;
}
	public Restaurant() {
		order = new ArrayList<Order>(); }

	 ////Show sales report/////////////////////////////////////////////////////////////
	 public void CheckOut(int friQty,int sodasQty,int burrQty,String username) {	
		 String sql = "INSERT INTO orders (date,price,status,username) VALUES ( ? ,?, ?,?)";
		 try (Connection connection = Database.getConnection();
					PreparedStatement stmt = connection.prepareStatement(sql);) {
			 
				stmt.setString(1, String.valueOf(LocalDateTime.now()));
				stmt.setString(2, "$ "+total);
				if (time==0) {
					stmt.setString(3, "Collected");
				}
				else {
				stmt.setString(3, "Await for collection");
				}
				stmt.setString(4,  username);	
				stmt.executeUpdate();	
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    	    burrQty=0;
		    	    friQty=0;
		    	    sodasQty=0;
		    	    total=0;
		    	    time=0;  }
 
/////add Burritons 
	 public static int addTime (int burrQty,int friQty) {
 ////burritos/////////// 
		 time = 0;
      	 if (burrQty>2) {	 
      		 for(int i=burrQty; i>0; i=i-2) { time=time+9;}}
	    	  else if(burrQty>0&&burrQty<=2){ time=time+9; }
 ////////sodas
      	if(friQty>getItem2().getQuantity()) {		
	       	 int friOrder=friQty-getItem2().getQuantity();
	       while(friOrder>0) { time=time+8;
	   	  friOrder=friOrder-5; }
	       		getItem2().setQuantity(0);	}
	       	else {int nextOrder = getItem2().getQuantity()-friQty;
	       		getItem2().setQuantity(getItem2().getQuantity()-friQty);} 
      	return time;
	 }
	 

	public static Fries getItem2() {
		return item2;
	}

	public void setItem2(Fries item2) {
		this.item2 = item2;
	}
	public static Burrito getItem1() {
		return item1;
	}
	public static void setItem1(Burrito item1) {
		Restaurant.item1 = item1;
	}
	public static Sodas getItem3() {
		return item3;
	}
	public static void setItem3(Sodas item3) {
		Restaurant.item3 = item3;
	}
	 
	 
	 
}
