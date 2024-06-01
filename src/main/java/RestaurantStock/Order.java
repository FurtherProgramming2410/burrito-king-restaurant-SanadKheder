package RestaurantStock;
import java.time.LocalDateTime; 

public class Order  {
	private LocalDateTime myObj = LocalDateTime.now();
	private int id;
	private String username,date,price, status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Order(int id, String date, String price, String status, String username) {
		super();
		this.id = id;
		this.username = username;
		this.date = date;
		this.price = price;
		this.status = status;
	}
}