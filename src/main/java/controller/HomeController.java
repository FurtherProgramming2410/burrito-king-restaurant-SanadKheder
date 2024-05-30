package controller;
import java.awt.Panel;
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
import dao.UserDaoImpl;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class HomeController {
	@FXML
	private CheckBox username_checkBox,id_checkBox,date_checkBox,status_checkBox,price_checkBox;	
	
	
	
	@FXML
	private TableView<Order> table_order;
	@FXML
	private TableColumn<Order,Integer> col_id;
	@FXML
	private TableColumn<Order,String> col_date;
	@FXML
	private TableColumn<Order,String> col_price;
	@FXML
	private TableColumn<Order,String> col_status;
	@FXML
	private TableColumn<Order,String> col_username;
	@FXML
	ObservableList<Order> listM;
	private int orderid;
	int index = -1;
	Connection conn=null;
	ResultSet rs=null;
	PreparedStatement pst =null;
	String select;
	@FXML
	private Pane checkVip, pane1,export_Page;
	@FXML
	private Label message1, welcome, message;
	private Model model;
	private Stage stage, parentStage;
	@FXML
	private Button Refresh, apply, menu, account, updateUser,logout,cancel,collect,export,save,close;
	@FXML
	private TextField email,path,name;
	//////////////////////////////////////
	public HomeController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

/////update the orders records//////////////////	
	public void updateRecords() throws SQLException {
		Database.setCurrentuser(model.getCurrentUser().getUsername());
		col_id.setCellValueFactory(new PropertyValueFactory<Order,Integer>("id"));
		col_date.setCellValueFactory(new PropertyValueFactory<Order,String>("date"));
		col_price.setCellValueFactory(new PropertyValueFactory<Order,String>("price"));
		col_status.setCellValueFactory(new PropertyValueFactory<Order,String>("status"));
		col_username.setCellValueFactory(new PropertyValueFactory<Order,String>("username"));
		listM= Database.getDataOrders();
		table_order.setItems(listM);
	}
	
//////Selecting from order records	to change status
	@FXML
	void getSelected (MouseEvent event) {
		index = table_order.getSelectionModel().getSelectedIndex();
		if (index<=-1) {
			return;}
		///////////////////////////////
		orderid=col_id.getCellData(index);
		 select =col_status.getCellData(index).toString();
		if (select.equals("Collected")) {
			cancel.setVisible(false);
			collect.setVisible(false);	}
		////////////////////////////////////
		else if(select.equals("Await for collection")) {
			cancel.setVisible(true);
			collect.setVisible(true);}
	///////////////////////////////////////////
        else if(select.equals("Cancelled")) {
			cancel.setVisible(false);
			collect.setVisible(false);}
		}
	//////////////////////////////////////////////////////
	
	@FXML
	public void initialize() throws SQLException {

		updateRecords();
		checkVip.setVisible(false);
		
//////Say welcome and Check if user VIP or not		
		welcome.setText("Welcome " + model.getCurrentUser().getFname() + " " + model.getCurrentUser().getLname() + " "
				+ model.getCurrentUser().getVip());
		if (model.getCurrentUser().getVip().equals("non-VIP")) {
			checkVip.setVisible(true);
		}
			
/////Export Button //////////////////////////////////			
			export.setOnAction(event -> {export_Page.setVisible(true);});		
///Close button ///////////////////////////////////////			
            close.setOnAction(event -> {export_Page.setVisible(false);});	
			


/////Export Button //////////////////////////////////			
		save.setOnAction(event -> {
			
			String pathLocation=path.getText()+name.getText()+".csv";
		    Database.exportOrdersToCSV(pathLocation,id_checkBox.isSelected(),status_checkBox.isSelected(),price_checkBox.isSelected(),
		    		username_checkBox.isSelected(),date_checkBox.isSelected());
		   message.setText("Orders have been exported to CSV successfully.");
	           
		});	
			
			
			
			
			
			
			
			
			
			
			
//Cancel  Button//////////////////////////////////////////
		cancel.setOnAction(event -> {
			try (Connection connection = Database.getConnection();
					Statement stmt = connection.createStatement();) {
				String sql = "UPDATE orders SET status = 'Cancelled' WHERE id ='"+orderid+ "'";
				stmt.executeUpdate(sql);
				updateRecords();
			} catch (SQLException e) {e.printStackTrace();}});
		
//Collect button ////////////////////////////////////		
		collect.setOnAction(event -> {
			try (Connection connection = Database.getConnection();
					Statement stmt = connection.createStatement();) {
				String sql = "UPDATE orders SET status = 'Collected' WHERE id ='"+orderid+ "'";
				stmt.executeUpdate(sql);
				updateRecords();
			} catch (SQLException e) {e.printStackTrace();}});
//Apply button //////////////////////////////////////		
			apply.setOnAction(event -> {
				if (!email.getText().isEmpty()) {
					try (Connection connection = Database.getConnection();
							Statement stmt = connection.createStatement();) {
						String sql = "UPDATE users " + "SET vip = 'VIP' ,email = '" + email.getText()
								+ "'  WHERE username  = '" + model.getCurrentUser().getUsername() + "'";
						stmt.executeUpdate(sql);
						model.getCurrentUser().setVip("VIP");
						checkVip.setVisible(false);
					} catch (SQLException e) {e.printStackTrace();}
				} else {
					message.setText("Please Enter Your Email");
					message.setTextFill(Color.RED);}});	
//Account button //////////////////////////////////////////
		account.setOnAction(event -> {	

		    
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateUserView.fxml"));
				// Customize controller instance
				UpdateUserController updateUserController = new UpdateUserController(stage, model);
				loader.setController(updateUserController);
				 VBox root = loader.load();
				updateUserController.showStage(root);
				welcome.setText(
						"Welcome " + model.getCurrentUser().getFname() + " " + model.getCurrentUser().getLname());
				stage.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	});
//Menu buuton /////////////////////////////////////////////////
		menu.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Menu.fxml"));
				// Customize controller instance
				MenuController menuController = new MenuController(stage, model);
				loader.setController(menuController);
				VBox root = loader.load();
				menuController.showStage(root);
				welcome.setText("Welcome " + model.getCurrentUser().getFname() + " " + model.getCurrentUser().getLname());
				stage.close();
			} catch (IOException e) {e.printStackTrace();}});
		
//Refresh button ///////////////////////////////////////////////		
		Refresh.setOnAction(event -> {
				try {
	    updateRecords();} catch (SQLException e) {
	    	e.printStackTrace();}});
		
//Logout buuton //////////////////////////////////////////////		
		logout.setOnAction(event -> {
			try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
			// Customize controller instance
			LoginController loginController = new LoginController(stage, model);
			loader.setController(loginController);
			VBox root = loader.load();
			loginController.showStage(root);
			} catch (IOException e) {e.printStackTrace();}});
	}
	
	
	public void showStage(Pane root) throws SQLException {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Home");
		stage.show();}}
