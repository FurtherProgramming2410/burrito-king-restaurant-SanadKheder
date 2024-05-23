package controller;
import java.awt.Panel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.Database;
import dao.UserDaoImpl;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

public class HomeController {
	
	@FXML
	private TableView<Order> table_order;
	
	
	
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
	
	int index = -1;
	Connection conn=null;
	ResultSet rs=null;
	PreparedStatement pst =null;
	
	@FXML
	private Pane checkVip, pane1;
	@FXML
	private Label message1, welcome, message;
	private Model model;
	private Stage stage, parentStage;
	@FXML
	private Button Refresh, apply, menu, account, updateUser,logout;
	@FXML
	private TextField email;
	public HomeController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	
	public void updateRecords() throws SQLException {
		Database.setCurrentuser(model.getCurrentUser().getUsername());
		col_date.setCellValueFactory(new PropertyValueFactory<Order,String>("date"));
		col_price.setCellValueFactory(new PropertyValueFactory<Order,String>("price"));
		col_status.setCellValueFactory(new PropertyValueFactory<Order,String>("status"));
		col_username.setCellValueFactory(new PropertyValueFactory<Order,String>("username"));
		listM= Database.getDataOrders();
		table_order.setItems(listM);
	}
	@FXML
	public void initialize() throws SQLException {

		updateRecords();

		checkVip.setVisible(false);
		welcome.setText("Welcome " + model.getCurrentUser().getFname() + " " + model.getCurrentUser().getLname() + " "
				+ model.getCurrentUser().getVip());
		if (model.getCurrentUser().getVip().equals("non-VIP")) {
			checkVip.setVisible(true);
			apply.setOnAction(event -> {
				if (!email.getText().isEmpty()) {
					try (Connection connection = Database.getConnection();
							Statement stmt = connection.createStatement();) {
						String sql = "UPDATE users " + "SET vip = 'VIP' ,email = '" + email.getText()
								+ "'  WHERE username  = '" + model.getCurrentUser().getUsername() + "'";
						stmt.executeUpdate(sql);
						model.getCurrentUser().setVip("VIP");
						checkVip.setVisible(false);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				} else {
					message.setText("Please Enter Your Email");
					message.setTextFill(Color.RED);
				}});	}

		account.setOnAction(event -> {
			MenuController.restaurant.Report();
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

		menu.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Menu.fxml"));
				// Customize controller instance
				MenuController menuController = new MenuController(stage, model);
				loader.setController(menuController);
				
				VBox root = loader.load();
				menuController.showStage(root);
				welcome.setText(
						"Welcome " + model.getCurrentUser().getFname() + " " + model.getCurrentUser().getLname());
				stage.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}});
		
		
		Refresh.setOnAction(event -> {
			try {
				updateRecords();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		
		logout.setOnAction(event -> {
			try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));

			// Customize controller instance
			LoginController loginController = new LoginController(stage, model);

			loader.setController(loginController);

			VBox root = loader.load();

			loginController.showStage(root);
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}});
	}
	public void showStage(Pane root) throws SQLException {
		
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Home");
		stage.show();}}
