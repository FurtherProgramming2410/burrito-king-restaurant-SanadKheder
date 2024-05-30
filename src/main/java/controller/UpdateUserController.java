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
	private Label message,fname,lname;
	@FXML
	private Button edit,save,close;
	@FXML
	private TextField vip,password,email,username,lname1,fname1;
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
// Get User details///////////////////////////// 		
		fname.setText(model.getCurrentUser().getFname());
		lname.setText(model.getCurrentUser().getLname());
		fname1.setText(model.getCurrentUser().getFname());
		lname1.setText(model.getCurrentUser().getLname());
		username.setText(model.getCurrentUser().getUsername());
		password.setText(model.getCurrentUser().getPassword());
		email.setText(model.getCurrentUser().getEmail());
		vip.setText(model.getCurrentUser().getVip());
		
//Save button //////////////////////////////////////
		save.setOnAction(event -> {
			if (!fname1.getText().isEmpty() && !password.getText().isEmpty()&& !lname1.getText().isEmpty()) {
			try (Connection connection = Database.getConnection();
					Statement stmt = connection.createStatement();) {
				String sql = "UPDATE users " +
			            "SET fname = '"+fname1.getText()+"'"
			            		+ " ,lname = '"+lname1.getText()+"' ,"
			            		+ " password = '"+password.getText()+"'"
			            + "WHERE username  = '"+model.getCurrentUser().getUsername()+"'";
				stmt.executeUpdate(sql);
             	model.getCurrentUser().setFname(fname1.getText());
				model.getCurrentUser().setLname(lname1.getText());
				model.getCurrentUser().setPassword(password.getText());
				////Hid buttion after save
				fname1.setDisable(true);
				lname1.setDisable(true);
				password.setDisable(true);
				save.setVisible(false);
				edit.setVisible(true);
		} catch (SQLException e) {
	         e.printStackTrace(); }}
			 else {}});	
			
//Edit Button to Show button after save///////////////////////////////////
		edit.setOnAction(event -> {
		edit.setVisible(false);
		fname1.setDisable(false);
		lname1.setDisable(false);
		password.setDisable(false);
		save.setVisible(true);});	
		
//close button//////////////////////////////
		close.setOnAction(event -> {
			stage.close();
			parentStage.show();});		
	}

	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Update User");
		stage.show();}}
