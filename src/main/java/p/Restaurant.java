package p;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Restaurant {
	private int friQty ,sodasQty,burrQty;
	private static int time;
	private static	double total;	
	private static ArrayList<Order> order ;

	
	private static  Burrito item1 = new Burrito("Burrito",7.00);
	private static Fries  item2 = new Fries(9,"Fries",4.00);
	private static Sodas  item3 = new Sodas(5,"Sodas",2.50);
	
public static double CalculatorPriceFries (double fqty, double bqty,double sqty ) {
	double total1 = (fqty*getItem2().getPrice())+(bqty*getItem1().getPrice())+(sqty*getItem3().getPrice());
	return total1;
}



	public Restaurant() {
		order = new ArrayList<Order>(); 
	}
	
	

	 
 ////checkout/////////////////////////////////////////////////////////////
	 public void checkout() {	
		 Scanner input = new Scanner(System.in);
	     total=total+ (friQty*getItem2().getPrice()+ burrQty*item1.getPrice()+sodasQty*item3.getPrice());
	     if(total>0) {
	    boolean paid = true; 
	    double money=0;
	    while (paid) {
		     System.out.println("----------------Checkout--------------");
		     System.out.println("Total for "+burrQty +" Burritos and "+friQty+" fries is $"+total);
		     System.out.println("Please enter money: ");
		      try {  money = input.nextInt();} catch (Exception e) {System.out.println("Invalid User Input."); }
		      input.nextLine();
		      if(money<total) {
		    	  System.out.println("Sorry, that’s not enough to pay for order"); }
		      else if(money>=total){
		    	  System.out.println("Change returned $" +(money-total));  
		    	  System.out.println("Time taken: "+time+" minutes"); 
		    	  paid = false;
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
		    	    burrQty=0;
		    	    friQty=0;
		    	    sodasQty=0;
		    	    total=0;
		    	    time=0;
		      }}}
	          else{System.out.println("Please add to order and try again ");} }
	 
	 ////Show sales report/////////////////////////////////////////////////////////////
	 
	 public void CheckOut(int friQty,int sodasQty,int burrQty) {	
		 
		 total=total+ (friQty*getItem2().getPrice()+ burrQty*item1.getPrice()+sodasQty*item3.getPrice());
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
		 System.out.println("bhbh");
		    for (Order e:order) {
			      System.out.println("bhbh"+e); }}

	//////Update Items///////////////////////// 
	 public void update() {
		 double newPrice=0;
		 int select=0;
		 	System.out.println("Select the food item to update the price\n"+
	  	    	      "1) Burrito    \n" +    
	  	    	      "2) Fries              \n"+              
	  	    	      "3) Soda       \n"+    
	  	    	      "4) Exit");
	        	Scanner input3 = new Scanner(System.in);	
	        		System.out.println("Please select:");
	        		try { select = input3.nextInt();}catch (Exception e) {System.out.println("Invalid User Input."); }
	        		/////////////////////////////
	        		if (select ==1) {
	        			System.out.println("Please enter new price: ");
		        		try {  newPrice = input3.nextInt();} catch (Exception e) {System.out.println("Invalid User Input."); }
		        		item1.setPrice(newPrice); 
		        		System.out.println("The unit price of burrito is updated to $" +newPrice);}
	        		///////////////////////////////////
	        		else if(select==2) {
	        			System.out.println("Please enter new price: ");
		        		try {  newPrice = input3.nextInt();}  catch (Exception e) {System.out.println("Invalid User Input."); }
		        		getItem2().setPrice(newPrice);
		        		System.out.println("The unit price of Ffries is updated to $" +newPrice);}
	        		//////////////////////////////////////
	        		else if(select==3) {
	        			System.out.println("Please enter new price: ");
	        		try {  newPrice = input3.nextInt();}catch (Exception e) {System.out.println("Invalid User Input."); }
	        		item3.setPrice(newPrice);
	        		System.out.println("The unit price of Sodas is updated to $" +newPrice);}
	        		/////////////////////////////////////
	        		else if(select==4) {}
	        		else {System.out.println("Try Again");} }
	 

///add fries////////////////////////////////////////////// 
	 public void addfries(int friQty ) {
		 
		
	       	if(friQty>getItem2().getQuantity()) {
	       		System.out.println(friQty-getItem2().getQuantity()+" left for cook and ready "+getItem2().getQuantity());
	    ///////////add time if need to cook /////////// 		
	       	 int friOrder=friQty-getItem2().getQuantity();
	       while(friOrder>0) { time=time+8;
	   	  friOrder=friOrder-5; }
	       		getItem2().setQuantity(0);	}
	       	else {int nextOrder = getItem2().getQuantity()-friQty;
	       		System.out.println("Cooking fries; please be patient "+ nextOrder+" serves of fries left for next order");
	       		getItem2().setQuantity(getItem2().getQuantity()-friQty);} }
	 
////add Sodas
	 public void addsodas(int sodasQty){

			if (sodasQty>item3.getQuantity()){
	       		System.out.println("Sorry we have only " +item3.getQuantity());
	       		sodasQty=0;}
	       	else {item3.setQuantity(item3.getQuantity()-sodasQty);	}}

	 
/////add Burritons 
	 public void addburritons (int burrQty) {
          ////add time/////////// 
      	 if (burrQty>2) {	 
      		 for(int i=burrQty; i>0; i=i-2) { time=time+9;}}
	    	  else if(burrQty>0&&burrQty<=2){ time=time+9; } }
	 
//// Add meal	 
	 public void meal() { 
		 String option="a";
		 boolean flag = true;
		 Scanner input = new Scanner(System.in);
		  while (flag) {
		 System.out.println("Burrito King Restaurant\n------------------------\n"+
					     "a)\n Burrito:1x \n fries:1x \n sodas:1x \n $3 discount  \n \n"+
				         "b) Exit");
		  System.out.print("Choose an option: ");
	      try {  option = input.next();} 
	      catch (Exception e) {System.out.println("Invalid User Input."); }
	      input.nextLine();
	      switch (option) {
	        case "a":
	   		 burrQty=1;   addburritons(burrQty);
	    	    friQty=1;   addfries(burrQty);
	    	    sodasQty=1;    addsodas(burrQty);
	            // Add discount
	   		 total=total-3;
	          break;
	        case "b":
	        	flag = false;
		          break;
	        default:
		          System.out.println("Invalid option.");
	      }}}




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
