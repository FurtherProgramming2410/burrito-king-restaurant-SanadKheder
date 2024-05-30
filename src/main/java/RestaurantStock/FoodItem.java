package RestaurantStock;

abstract class FoodItem {
	
	
private int quantity ;
private String itemName;
private int itemId;
private double price;
static int nextID=1;


public FoodItem(String itemName, double price) {
	super();
	this.itemName = itemName;
	itemId= nextID++;
	this.price = price;
}
public FoodItem(int quantity, String itemName, double price) {
	super();
	this.quantity = quantity;
	this.itemName = itemName;
	itemId= nextID++;
	this.price = price;
}

public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public String getItemName() {
	return itemName;
}
public void setItemName(String itemName) {
	this.itemName = itemName;
}
public int getItemId() {
	return itemId;
}
public void setItemId(int itemId) {
	this.itemId = itemId;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
@Override
public String toString() {
	return  itemId + "- Qty:" + quantity + " " + itemName +  " $" + price +" AU";
}
	
	
	
}
