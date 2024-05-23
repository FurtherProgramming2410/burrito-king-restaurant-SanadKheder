package p;
import java.time.LocalDateTime; 

public class Order  {
	private LocalDateTime myObj = LocalDateTime.now();
	private int orderId;
	private String Report;
	static int nextID=1;
	private String username,date,price, status;
	
	
	public Order(String date, String price, String status,String username ) {
		super();
		this.username = username;
		this.date = date;
		this.price = price;
		this.status = status;
	}
	public Order() {
		
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
	public Order(String report) {
		Report = report;
		orderId= nextID++;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getReport() {
		return Report;
	}
	public void setReport(String report) {
		Report = report;
	}
	public int getOrderId() {
		return orderId;
	}
	@Override
	public String toString() {
		return "Order #" + orderId + "\n" + Report ;
	}

	
	
}