package model;

public class User {
	private String username;
	private String password;
	private String fname;
	private String lname;
	private String email;
	private String vip="false";

	public User() {
	}
	

	public User(String username, String password, String fname, String lname, String email, String vip) {
		super();
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.vip = vip;
	}



	public String getEmail() {
		return email;
	}






	public void setEmail(String email) {
		this.email = email;
	}








	public String getVip() {
		return vip;
	}






	public void setVip(String vip) {
		this.vip = vip;
	}






	public String getFname() {
		return fname;
	}


	public void setFname(String fname) {
		this.fname = fname;
	}


	public String getLname() {
		return lname;
	}


	public void setLname(String lname) {
		this.lname = lname;
	}


	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
