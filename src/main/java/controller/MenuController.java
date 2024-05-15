package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
	private int friQty ,sodasQty,burrQty,time;
	@FXML
	private Button close;
	@FXML
	private Button sodasadd;
	@FXML
	private Button burritoadd;
	@FXML
	private Button friesadd;
	@FXML
	private Button addToOrder;
	
	@FXML
	private Button removesodas;
	@FXML
	private Button removeburrito;
	@FXML
	private Button removefries;
	
	

	
	@FXML
	private TextField friesorder;
	@FXML
	private TextField burritoorder;
	@FXML
	private TextField sodasorder;
	
	@FXML
	private Label sodas;
	@FXML
	private Label burrito;
	@FXML
	private Label fries;
	@FXML
	private Button MF;
	@FXML
	private Button PF;
	@FXML
	private Button MB;
	@FXML
	private Button PB;
	@FXML
	private Button MS;
	@FXML
	private Button PS;
	
	private Stage stage;
	private Stage parentStage;
	private Model model;

	private int qtyf=1;
	private int qtyB=1;
	private int qtyS=1;
	
	public MenuController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	@FXML
	public void initialize() {
	
			
		
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
			sodas.setText(sodasorder.getText());
		
			
			
		});
		burritoadd.setOnAction(event -> {	
			burrito.setText(burritoorder.getText());
			
		});
		friesadd.setOnAction(event -> {
			fries.setText(friesorder.getText());
		});
		
		
			addToOrder.setOnAction(event -> {	
			
			
		});
		
		
		removefries.setOnAction(event -> {	fries.setText("0");
			
		});
		removesodas.setOnAction(event -> {	sodas.setText("0");	
		});
		removeburrito.setOnAction(event -> { burrito.setText("0");
		});
		
		
		
		close.setOnAction(event -> {
			stage.close();
			parentStage.show();});
	}
	
	
/////add Burritons 
	 public void addburritons (int burrQty) {
          ////add time/////////// 
      	// if (burritoadd.getText()>2) {	 
      		 for(int i=burrQty; i>0; i=i-2) { time=time+9;}}
	    	//  else if(burrQty>0&&burrQty<=2){ time=time+9; } }

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Update");
		stage.show();
	}
}
