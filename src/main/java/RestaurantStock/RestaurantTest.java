package RestaurantStock;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import RestaurantStock.Restaurant;
public class RestaurantTest {
         private Restaurant  restaurant;
		@Before
		public void setUp() {
			Restaurant restaurant = new Restaurant();}
		@After
		public void tearDown() {
			restaurant = null;}
//Test Discount		
		@Test
		public void Test_Discount_TotalPrice() {
			int burrQTY =0 ;
			int FriQTY=1;
			int SodasQTY=0;
			double meal=0;
			double discount=1;
		assertEquals(6,restaurant.CalculatorPrice(burrQTY,FriQTY,SodasQTY,meal,discount),0.0);	}
		
///Test 2X Fries			
		@Test
		public void Test_Fries_2X_TotalPrice() throws IllegalArgumentException{
			int burrQTY =0 ;
			int FriQTY=2;
			int SodasQTY=0;
			double meal=0;
			double discount=0;
		assertEquals(14,restaurant.CalculatorPrice(burrQTY,FriQTY,SodasQTY,meal,discount),0.0);
		}
////Test Meal		
		@Test
		public void Test_Meal_TotalPrice() throws IllegalArgumentException{
			int burrQTY =0 ;
			int FriQTY=0;
			int SodasQTY=0;
			double meal=1;
			double discount=0;
		assertEquals(10.5,restaurant.CalculatorPrice(burrQTY,FriQTY,SodasQTY,meal,discount),0.0);}
	
////Test 2x sodas		
		@Test
		public void Test_Sodas_2X_TotalPrice() throws IllegalArgumentException{
			int burrQTY =0 ;
			int FriQTY=0;
			int SodasQTY=2;
			double meal=0;
			double discount=0;
		assertEquals(5,restaurant.CalculatorPrice(burrQTY,FriQTY,SodasQTY,meal,discount),0.0);}	
//// Test Time		
		@Test
		public void Test_Time_TotalPrice() throws IllegalArgumentException{
			int burrQTY =1 ;
			int FriQTY=44;
		assertEquals(65,restaurant.addTime(burrQTY,FriQTY),0);}
	

		
	
	}

