package controller;

import java.awt.Panel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import dao.Database;
import dao.UserDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
	private Label welcome;
	@FXML
	private Pane checkVip;
	@FXML
	private Label message1;
	@FXML
	private Label message;
	private Model model;
	private Stage stage;
	private Stage parentStage;
	@FXML
	private Button apply;
	@FXML
	private Button home;
	@FXML
	private Button account;
	@FXML
	private TextField email;
	
	@FXML
	private Pane pane1;

	@FXML
	private Button updateUser; // Corresponds to the Menu item "viewProfile" in HomeView.fxml

	public HomeController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;

	}
	
	
	

	@FXML
	public void initialize() {
		checkVip.setVisible(false);
		welcome.setText("Welcome " + model.getCurrentUser().getFname() + " " + model.getCurrentUser().getLname() +" "+model.getCurrentUser().getVip()) ;
		
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
				}
			});
		}

		
		
	      
		
	
		
		
		account.setOnAction(event -> {
	
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateUserView.fxml"));
				// Customize controller instance
				UpdateUserController updateUserController = new UpdateUserController(stage, model);
				loader.setController(updateUserController);
				VBox root;
				
				root = loader.load();
				updateUserController.showStage(root);
				welcome.setText(
						"Welcome " + model.getCurrentUser().getFname() + " " + model.getCurrentUser().getLname());
				stage.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	public void showStage(Pane root) {

		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Home");

		stage.show();
	}

}
