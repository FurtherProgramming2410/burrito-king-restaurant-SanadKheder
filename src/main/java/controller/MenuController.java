package controller;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import RestaurantStock.Burrito;
import RestaurantStock.Fries;
import RestaurantStock.Order;
import RestaurantStock.Restaurant;
import RestaurantStock.Sodas;
import dao.Database;
import dao.UserDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class MenuController {

	static Restaurant restaurant = new Restaurant();
	@FXML
	private Button close,removefries,removeburrito,sodasadd,burritoadd,friesadd,addToOrder,removesodas,removemeal,yes,no,ok;
	@FXML
	private TextField burritoorder,sodasorder,friesorder,cardnumber,cardname,mm,yy,cvv,Enter_credit;
	@FXML
	private Label burrito,fries,sodas,SodasPrice,BurritoPrice,FriesPrice,message,totalprice,Time,mealqty,credit_available;
	@FXML
	private Button MF,PF,PS,MS,PB,MB,pay,addMore,addmeal;
	@FXML
	private Pane cardPage,orderPage,burritocheckout,sodascheckout,friescheckout,mealcheckout,meal,creditmessage,creditenter;
	private Stage stage,parentStage;
	private Model model;
	private int qtyf,qtyS,qtyB=0;
	private Double credit =0.0;
	private String VIP="";
	private double discount =0 ;
	public MenuController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;	
	}

	
	@FXML
	public void initialize() {
/////show meal option if user VIP		
		if(model.getCurrentUser().getVip().equals("VIP")) {
			meal.setVisible(true);}
		else {meal.setVisible(false);}
		
		
		
//+1 QTY Fries //////////////////////////////////////////////		
		PF.setOnAction(event -> {qtyf = qtyf+1;
			friesorder.setText(Integer.toString(qtyf));});
//-1 QTY Fries ////////////////////////////////////		
		MF.setOnAction(event -> {qtyf = qtyf-1;
			if(qtyf<1) {qtyf = 0;}
			friesorder.setText(Integer.toString(qtyf));});
		
//+1 QTY Sodas ///////////////////////		
		PS.setOnAction(event -> {qtyS = qtyS+1;
			sodasorder.setText(Integer.toString(qtyS));});	
//-1 QTY sodas/////////////////////////////////////////		
		MS.setOnAction(event -> {qtyS = qtyS-1;
			if(qtyS<1) {qtyS = 0;}
			sodasorder.setText(Integer.toString(qtyS));});
		
//+1 QTY Burrtos//////////////////////////////////
		PB.setOnAction(event -> {qtyB = qtyB+1;
			burritoorder.setText(Integer.toString(qtyB));});
//-1 QTY Burrtos//////////////////////////////////	
		MB.setOnAction(event -> {	qtyB = qtyB-1;
			if(qtyB<1) {qtyB = 0;}
			burritoorder.setText(Integer.toString(qtyB));});
		
		
//Add buttons ///////////////////////////////////////////
		sodasadd.setOnAction(event -> {
			updateTotal();
			if (qtyS>0) {
			sodascheckout.setVisible(true);}
			sodas.setText(sodasorder.getText());});
		//////////////////////////////////////////////////
		burritoadd.setOnAction(event -> {	
			updateTotal();
			if (qtyB>0) {
			burritocheckout.setVisible(true);}
			burrito.setText(burritoorder.getText());});
		///////////////////////////////////////////////////
		friesadd.setOnAction(event -> {
			updateTotal();
			if (qtyf>0) {
			friescheckout.setVisible(true);}
			fries.setText(friesorder.getText());});
		/////////////////////////////////////////////////////
		addmeal.setOnAction(event -> {
			mealqty.setText("1");
			mealcheckout.setVisible(true);
			updateTotal();});
		
		
		
//Add to checkout//////////////////////////	/////////////////	
			addToOrder.setOnAction(event -> {	
				qtyB=qtyB+Integer.parseInt(mealqty.getText());
				qtyf=qtyf+Integer.parseInt(mealqty.getText());
                Time.setText(restaurant.addTime(qtyB,qtyf)+" Minutes");
				cardPage.setVisible(true);
				addMore.setVisible(true);
				addToOrder.setVisible(false);
				removeburrito.setVisible(false);
				removesodas.setVisible(false);
				removemeal.setVisible(false);
				removefries.setVisible(false);
				///This for VIP users , Collecting credits for all orders
				 VIP= model.getCurrentUser().getVip();
				if(VIP.equals("VIP")) {
					String sql = "select credit   from users where username= '"+model.getCurrentUser().getUsername()+"'";
					try (Connection connection = Database.getConnection();
							PreparedStatement stmt = connection.prepareStatement(sql);
						ResultSet rs = stmt.executeQuery()) {
						 credit = rs.getDouble("credit");
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();}
					
					credit_available.setText(""+credit);
					creditmessage.setVisible(true);
					
					
				}});
//close check out and Add more	//////////////////////////////////////		
			addMore.setOnAction(event -> {		
				cardPage.setVisible(false);
				addMore.setVisible(false);
				addToOrder.setVisible(true);
				removeburrito.setVisible(true);
				removesodas.setVisible(true);
				removefries.setVisible(true);});
//Checkout and pay////////////////////////////////////////////////////			
			pay.setOnAction(event -> {			
				if (!cardnumber.getText().isEmpty() && !cardname.getText().isEmpty()&& !mm.getText().isEmpty()
						&& !yy.getText().isEmpty()&& !cvv.getText().isEmpty()&&cardnumber.getLength() == 16 &&cvv.getLength() == 3) {
					
					
		// update Credit ///////////////////////////////////////////
					if(VIP.equals("VIP")) {
					credit = credit-Double.parseDouble(Enter_credit.getText()) ;
					 String sql2 = "UPDATE users SET credit = '"+credit+"' WHERE username = '"+model.getCurrentUser().getUsername()+ "'";
					 try (Connection connection = Database.getConnection();
								PreparedStatement stmt = connection.prepareStatement(sql2);) {
							stmt.executeUpdate();
						} catch (SQLException e) {e.printStackTrace();}
					}
					//send order to resturent /////////////////////////////////////////
						restaurant.CheckOut(qtyf,qtyS,qtyB,model.getCurrentUser().getUsername(),credit,VIP);
						stage.close();
						parentStage.show();}
				else {message.setText("Please Fill all boxes");}});
			
///Yes Button /////////////////////////////////////////////		
			yes.setOnAction(event -> {
				creditmessage.setVisible(false);
				creditenter.setVisible(true);});
///No Button ///////////////////////////////////////////////
			no.setOnAction(event -> {creditmessage.setVisible(false);});
//ok Button /////////////////////////////////////////////////////		
			ok.setOnAction(event -> {
				//check if user enter correct input then upadte price
				if (Enter_credit.getText().isEmpty()) {
					message.setText("Please enter any value ");
				}
				else if (Double.parseDouble(Enter_credit.getText()) <= credit &&Double.parseDouble(Enter_credit.getText()) >=0 ){
	message.setText("Done! Credit add $"+Enter_credit.getText());
	creditmessage.setVisible(false);
	creditenter.setVisible(false);
	discount =Double.parseDouble(Enter_credit.getText());
	updateTotal();} 
else {message.setText("Please enter less then $"+credit);}});
			
			
			
//Remove Buttons//////////////
		removefries.setOnAction(event -> {
				fries.setText("0");	friesorder.setText("0"); updateTotal();
		friescheckout.setVisible(false);	});
		//////////////////////
		removesodas.setOnAction(event -> {
			 sodas.setText("0"); sodasorder.setText("0");	 updateTotal();
		sodascheckout.setVisible(false);});
		///////////////////////
		removeburrito.setOnAction(event -> {
		burrito.setText("0");burritoorder.setText("0"); updateTotal();
		burritocheckout.setVisible(false);});
		//////
		removemeal.setOnAction(event -> {
			mealqty.setText("0");
			qtyB=qtyB-1;
			qtyf=qtyf-1;
			updateTotal();
			mealcheckout.setVisible(false);});
		
//Close button	/////////////////////////////////////////////	
		close.setOnAction(event -> {
			stage.close();
			parentStage.show();});}

	
	
///update Total Price////////////////////////////
public void updateTotal() {
	totalprice.setText("$"+Restaurant.CalculatorPrice(Double.parseDouble(friesorder.getText())
			,Double.parseDouble(burritoorder.getText())
			,Double.parseDouble(sodasorder.getText()),Double.parseDouble(mealqty.getText()),discount));	
}


    
	public void showStage(Pane root) {	
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Menu");
		stage.show();
	}
}
