package model;

import java.sql.SQLException;

import dao.UserDao;
import dao.UserDaoImpl;

public class Model {
	private UserDao userDao;
	private User currentUser; 
	

	public UserDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(UserDao orderDao) {
		this.orderDao = orderDao;
	}

	private UserDao orderDao;
	
	public Model() {
		userDao = new UserDaoImpl();
	}
	
	public void setup() throws SQLException {
		userDao.setup();
	}
	public UserDao getUserDao() {
		return userDao;
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public void setCurrentUser(User user) {
		currentUser = user;
	}
}
