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

public class UpdateUserController {

	
	
	@FXML
	private Label message;

	@FXML
	private TextField username;

	@FXML
	private TextField password;
	@FXML
	private TextField fname;
	@FXML
	private TextField lname;
	@FXML
	private Button close;
	@FXML
	private Button updateUser;

	private Stage stage;
	private Stage parentStage;
	private Model model;

	public UpdateUserController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	@FXML
	public void initialize() {
	
		
		fname.setText(model.getCurrentUser().getFname());
		lname.setText(model.getCurrentUser().getLname());
		username.setText(model.getCurrentUser().getUsername());
		password.setText(model.getCurrentUser().getPassword());
		
		updateUser.setOnAction(event -> {
			
			if (!fname.getText().isEmpty() && !password.getText().isEmpty()&& !lname.getText().isEmpty()) {
			
				
				
			try (Connection connection = Database.getConnection();
					Statement stmt = connection.createStatement();) {
				String sql = "UPDATE users " +
			            "SET fname = '"+fname.getText()+"'"
			            		+ " ,lname = '"+lname.getText()+"' ,"
			            		+ " password = '"+password.getText()+"'"
			            + "WHERE username  = '"+model.getCurrentUser().getUsername()+"'";
				stmt.executeUpdate(sql);
			
				
				model.getCurrentUser().setFname(fname.getText());
				model.getCurrentUser().setLname(lname.getText());
				model.getCurrentUser().setPassword(password.getText());
				

				
		} catch (SQLException e) {
	         e.printStackTrace();
	      }
			
			}
			 else {
				 message.setText("Empty Firstname or LastName or password");
				
				}
			
			
		});	
		
		
		
		close.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});	
		
		
	}

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Update");
		stage.show();
	}
}
