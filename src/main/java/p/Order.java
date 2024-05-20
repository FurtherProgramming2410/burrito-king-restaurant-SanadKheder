package p;
import java.time.LocalDateTime; 

public class Order  {
	private LocalDateTime myObj = LocalDateTime.now();
	private int orderId;
	private String Report;
	static int nextID=1;
	
	public Order() {
		
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