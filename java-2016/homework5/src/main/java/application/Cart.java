package application;

import java.util.List;


public class Cart   {
	
   
	private List<CartEntry> lCartEntry;
	private float totalPrice;
	
	public Cart(List<CartEntry> lCartEntry, float totalPrice) {
	
		this.lCartEntry = lCartEntry;
		this.totalPrice = totalPrice;
	}
	
	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<CartEntry> getlCartEntry() {
		return lCartEntry;
	}

	public void setlCartEntry(List<CartEntry> lCartEntry) {
		this.lCartEntry = lCartEntry;
	}

}
