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
	private String status;
	private static  Burrito item1 = new Burrito("Burrito",7.00);
	private static Fries  item2 = new Fries(9,"Fries",4.00);
	private static Sodas  item3 = new Sodas(99,"Sodas",2.50);
	
public static double CalculatorPriceFries (double fqty, double bqty,double sqty ) {
	double total1 = (fqty*getItem2().getPrice())+(bqty*getItem1().getPrice())+(sqty*getItem3().getPrice());
	return total1;
}



	public Restaurant() {
		order = new ArrayList<Order>(); }

	 ////Show sales report/////////////////////////////////////////////////////////////
	 public void CheckOut(int friQty,int sodasQty,int burrQty,String username) {	
		 total=total+ (friQty*getItem2().getPrice()+ burrQty*item1.getPrice()+sodasQty*item3.getPrice());
		 String sql = "INSERT INTO orders VALUES ( ?, ?, ?,?)";
		 try (Connection connection = Database.getConnection();
					PreparedStatement stmt = connection.prepareStatement(sql);) {
			 
				stmt.setString(1, String.valueOf(LocalDateTime.now()));
				stmt.setString(2, "$ "+total);
				if (time==0) {
					stmt.setString(3, "collected");
				}
				else {
				stmt.setString(3, "await for collection");
				}
				stmt.setString(4,  username);	
				stmt.executeUpdate();	
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 String checkout =(
		    	    "--------------------------------- \n"+
		    	    "Total Sales:\n"+
		    	    "Burritos: "+burrQty+"   $"+ burrQty*item1.getPrice()+"\n"+
		    	    "fries: "+friQty +"   $"+friQty*getItem2().getPrice()+"\n"+
		    	    "sodas: "+sodasQty +"   $"+sodasQty*item3.getPrice()+"\n"+
		    	    "---------------------------------"+"\n"+
		    	    "        "+(burrQty+friQty+sodasQty)+"      $"+total+"\n");
		    	    Order c = new Order(checkout);
		    	 order.add(c); 
		    	    System.out.println("time "+ time);
		    	    burrQty=0;
		    	    friQty=0;
		    	    sodasQty=0;
		    	    total=0;
		    	    time=0;  }
	 ////Show sales report/////////////////////////////////////////////////////////////
	 public  void Report() {	
		    for (Order e:order) {
			      System.out.println(e); } }
 
	 
	 
	 
	 
	 
/////add Burritons 
	 public int addTime (int burrQty,int friQty) {
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
	 
	
	 
	 
	 
	 
	 
//// Add meal	 
	 public void meal() { 
	   		// burrQty=1;   addburritons(burrQty);
	    //	    friQty=1;   addfries(burrQty);
	    //	    sodasQty=1;    addsodas(burrQty);
	            // Add discount
	   		 total=total-3;
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
