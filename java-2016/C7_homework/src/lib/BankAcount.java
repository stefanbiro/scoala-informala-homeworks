package lib;

public class BankAcount {

	private int acountNumber;
	private float amount; // euro

	public BankAcount(float amount) {

		this.amount = amount;
	}

	public int getAcountNumber() {
		return acountNumber;
	}

	public float getAmount() {
		return amount;
	}

	public void decreaseCountAmount(float carPrice) {
		amount -= carPrice;
	}

	public void increseCountAmount(float buyBackPrice) {
		amount += buyBackPrice ;
	}

}
