package lib;

import java.util.List;

public class ClientAcount {

	private Client client;
	private List<Car> lBuyedcars;

	public ClientAcount(Client client, List<Car> lBuyedcars) {
		super();
		this.client = client;
		this.lBuyedcars = lBuyedcars;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Car> getlBuyedcars() {
		return lBuyedcars;
	}

	public void setlBuyedcars(List<Car> lBuyedcars) {
		this.lBuyedcars = lBuyedcars;
	}

}
