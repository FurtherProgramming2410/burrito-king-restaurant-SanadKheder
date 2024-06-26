package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class UserDaoImpl implements UserDao {
	private final String TABLE_NAME = "users";
	
	
	
	public UserDaoImpl() {}
	
	
////////////////////////////////////
	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			//database for users
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username VARCHAR(10) NOT NULL,"
					+ "password VARCHAR(8) NOT NULL," + "fname VARCHAR(10) NOT NULL,"+"lname VARCHAR(10) NOT NULL," +"email VARCHAR(10) NOT NULL," 
					+"vip VARCHAR(10) NOT NULL," +"credit	INTEGER,"+"PRIMARY KEY (username)"+")";
			///database for orders
			String sql1 = "CREATE TABLE IF NOT EXISTS orders (id	INTEGER NOT NULL,"+"date VARCHAR(8) NOT NULL," 
			+"price VARCHAR(8) NOT NULL," +"status VARCHAR(8) NOT NULL," +"username VARCHAR(8) NOT NULL,"+" PRIMARY KEY (id AUTOINCREMENT))";		
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);
		} }

	
//////////////////////////////////	
	@Override
	public User getUser(String username, String password,String fname ,String lname,String email,String vip ) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ? ";
		try (Connection connection = Database.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setFname(rs.getString("fname"));
					user.setLname(rs.getString("lname"));
					user.setEmail(rs.getString("email"));
					user.setVip(rs.getString("vip"));
					return user;
				}
				return null;
			} }}

/////////////////////////////////////////////
	@Override
	public User createUser(String username, String password,String fname ,String lname,String email,String vip ) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?,?,?,?)";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, fname);
			stmt.setString(4, lname);
			stmt.setString(5, email);
			stmt.setString(6, vip);
			stmt.setString(7, "0");
			stmt.executeUpdate();
			return new User(username, password, fname, lname, email, vip);
		} }}
	