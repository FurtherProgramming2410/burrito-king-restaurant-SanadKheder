package controller;
import java.time.YearMonth;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
import p.Burrito;
import p.Fries;
import p.Order;
import p.Restaurant;
import p.Sodas;

public class MenuController {

	static Restaurant restaurant = new Restaurant();
	@FXML
	private Button close,removefries,removeburrito,sodasadd,burritoadd,friesadd,addToOrder,removesodas,removemeal;
	@FXML
	private TextField burritoorder,sodasorder,friesorder,cardnumber,cardname,mm,yy,cvv;
	@FXML
	private Label burrito,fries,sodas,SodasPrice,BurritoPrice,FriesPrice,message,totalprice,Time,mealqty;
	@FXML
	private Button MF,PF,PS,MS,PB,MB,pay,addMore,addmeal;
	@FXML
	private Pane cardPage,orderPage,burritocheckout,sodascheckout,friescheckout,mealcheckout,meal;
	private Stage stage,parentStage;
	private Model model;
	private int qtyf,qtyS,qtyB=0;
	
	public MenuController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		
	}

	
	@FXML
	public void initialize() {
		
		if(model.getCurrentUser().getVip().equals("VIP")) {
			meal.setVisible(true);
		}
		else {
			meal.setVisible(false);
		}
		
		PF.setOnAction(event -> {	
			qtyf = qtyf+1;
			friesorder.setText(Integer.toString(qtyf));
		});
		MF.setOnAction(event -> {	
			qtyf = qtyf-1;
			if(qtyf<1) {
				qtyf = 0;}
			friesorder.setText(Integer.toString(qtyf));
		});
	////////////////////////////////////	
	
		
		PS.setOnAction(event -> {	
			qtyS = qtyS+1;
			sodasorder.setText(Integer.toString(qtyS));
		});
		MS.setOnAction(event -> {	
			qtyS = qtyS-1;
			if(qtyS<1) {
				qtyS = 0;}
			sodasorder.setText(Integer.toString(qtyS));
		});
	/////////////////////////////////////
		PB.setOnAction(event -> {	
			qtyB = qtyB+1;
			burritoorder.setText(Integer.toString(qtyB));
		});
		MB.setOnAction(event -> {	
			qtyB = qtyB-1;
			if(qtyB<1) {
				qtyB = 0;}
			burritoorder.setText(Integer.toString(qtyB));
		});
		
		

		sodasadd.setOnAction(event -> {
			updateTotal();
			if (qtyS>0) {
			sodascheckout.setVisible(true);}
			sodas.setText(sodasorder.getText());
			});
		
		burritoadd.setOnAction(event -> {	
			updateTotal();
			if (qtyB>0) {
			burritocheckout.setVisible(true);}
			burrito.setText(burritoorder.getText());	
			});
		
		friesadd.setOnAction(event -> {
			updateTotal();
			if (qtyf>0) {
			friescheckout.setVisible(true);}
			fries.setText(friesorder.getText());
			});
		
		addmeal.setOnAction(event -> {
			
			mealqty.setText("1");
			mealcheckout.setVisible(true);
			updateTotal();
			
			
			});
		
		
			addToOrder.setOnAction(event -> {	
				
				qtyB=qtyB+Integer.parseInt(mealqty.getText());
				qtyf=qtyf+Integer.parseInt(mealqty.getText());
                Time.setText(restaurant.addTime(qtyB,qtyf)+" Minutes");
				cardPage.setVisible(true);
				addMore.setVisible(true);
				addToOrder.setVisible(false);
				removeburrito.setVisible(false);
				removesodas.setVisible(false);
				removefries.setVisible(false);
		});
			
			addMore.setOnAction(event -> {		
				cardPage.setVisible(false);
				addMore.setVisible(false);
				addToOrder.setVisible(true);
				removeburrito.setVisible(true);
				removesodas.setVisible(true);
				removefries.setVisible(true);	
		});
			pay.setOnAction(event -> {		
				if (!cardnumber.getText().isEmpty() && !cardname.getText().isEmpty()&& !mm.getText().isEmpty()
						&& !yy.getText().isEmpty()&& !cvv.getText().isEmpty()&&cardnumber.getLength() == 16 &&cvv.getLength() == 3
						) {
						restaurant.CheckOut(qtyf,qtyS,qtyB,model.getCurrentUser().getUsername());
						stage.close();
						parentStage.show();
				}
				else {
					message.setText("Please Fill all boxes");
				}});
		
			
			
			
			
			
		//////////////
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
		
		
		close.setOnAction(event -> {
			stage.close();
			parentStage.show();});
	}
	
public void updateTotal() {
	totalprice.setText("$"+Restaurant.CalculatorPriceFries(Double.parseDouble(friesorder.getText())
			,Double.parseDouble(burritoorder.getText())
			,Double.parseDouble(sodasorder.getText()),Double.parseDouble(mealqty.getText())));
	
	
}

    
	public void showStage(Pane root) {
		
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Update");
		stage.show();
	}
}
