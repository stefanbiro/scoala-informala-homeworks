package lib;

/**
 * */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import lib.Car.FuelType;

public class CarDealerShip {

	public List<Car> availbleCarsList = new ArrayList<>();
	public List<Car> availbleCarsForFleet = new ArrayList<>();
	public List<Car> buyedCarsList;
	public Map<Integer, ClientAcount> clientEntry = new HashMap<>();
	public Map<Integer, Car> fleet = new TreeMap<>();;

	ClientAcount clientAcount;
	public int clientId = 0;

	// a clientEntry is mean an id and a ClientAcount
	public void createClientEntry(Client client, Car wishCar) {

		// provide new id for new client
		clientId = createNewId();
		Integer id = new Integer(clientId);

		// add wishCar to bayed car list
		buyedCarsList = new ArrayList<>(); // this list i have to initialize
											// inside the method
											// because every client has his own
											// buyedCarsList
		buyedCarsList.add(wishCar);

		// create an account
		clientAcount = new ClientAcount(client, buyedCarsList);

		// add a new entry
		clientEntry.put(id, clientAcount);
	}

	/*
	 * this method provide a new id for new client in (lastId + 1) fashion, in
	 * this way every client will have a different id and he enter in the map in
	 * Increasing order of id (1,2,3,4 ...)
	 */
	private int createNewId() {
		clientId += 1;
		return clientId;
	}

	// this method add cars to available car list
	public List<Car> addCarsToAvailbeCarsList() {

		availbleCarsList.add(new Aygo("Toyota", "Aygo", "1234", FuelType.PETROL, 2000, 10000));
		availbleCarsList.add(new Passat("Wolkswagen", "Passat", "1235", FuelType.DIESEL, 2014, 18000));
		availbleCarsList.add(new Yaris("Toyota", "Yaris", "1224", FuelType.PETROL, 2016, 13000));
		availbleCarsList.add(new Golf("Wolkswagen", "Golf", "1235", FuelType.PETROL, 2015, 16000));
		availbleCarsList.add(new Yaris("Toyota", "Yaris", "4224", FuelType.HIBRID, 2016, 28000));
		availbleCarsList.add(new Golf("Wolkswagen", "Golf", "1435", FuelType.PETROL, 2014, 12000));

		return availbleCarsList;
	}

	public void viewAvailableCarsList() {

		System.out.println("the available cars are : \n");
		for (Object o : availbleCarsList) {
			System.out.println(o.toString());
		}

	}

	/*
	 * this method cheek if the wished car is in the available carList we use
	 * HashSet because is more efficient for find an element in a collection
	 */
	public boolean cheekCarInList(Car wishCar) throws CarNotInTheListException {
		Set<Car> cars = new HashSet<>(availbleCarsList);
		boolean result = false;

		if (cars.contains(wishCar)) {
			result = true;
		} else
			throw new CarNotInTheListException("The car is not in the availale car list");

		return result;
	}

	/*
	 * this method cheek if is enough money in account to buy the car we want
	 * 
	 */
	public boolean cheekAcount(Car wishCar, Client client) throws FoundsNotEnoughException {

		boolean result = false;

		if (wishCar.getPrice() < client.getAcount().getAmount()) {
			result = true;
		} else
			throw new FoundsNotEnoughException("Found amount not enough !");

		return result;
	}

	/**
	 * the dealer can sell a car if both condition is ok the wised car is in the
	 * list and the client have enough money in account
	 */
	public void selCar(Car wishCar, Client client) throws CarNotInTheListException, FoundsNotEnoughException {

		if (cheekCarInList(wishCar) && cheekAcount(wishCar, client)) {

			// the sealed car is no more for sell
			removeCarFromAvailableList(wishCar);

			// the money transfer take place
			client.getAcount().decreaseCountAmount(wishCar.getPrice());

			// the dealer create a new account for the client
			createClientEntry(client, wishCar);

		}
	}

	/**
	 * This method will remove the bayed car from available car list we use
	 * LinkedList because is more efficient that ArrayList for removing elements
	 */
	public void removeCarFromAvailableList(Car wishCar) {

		List<Car> availbleCars = new LinkedList<>(availbleCarsList);
		if (availbleCars.contains(wishCar)) {
			for (int i = 0; i < availbleCars.size(); i++) {
				if (availbleCars.get(i).equals(wishCar)) {

					availbleCars.remove(i);
				}
			}
		}
		// we update the initial Arraylist (without wishCar)
		availbleCarsList = new ArrayList<>(availbleCars);
	}

	// this method model the buyback activity
	public void buyBack(Car buyBackCar, Client client, int yearOfBuyBack, float km, int id)
			throws NoBankAcountException {

		// remove buyBackcar from buyedCarsList
		clientEntry.get(id).getlBuyedcars().remove(buyBackCar);

		// add the buyBackCar to availableCarsList to sell again
		availbleCarsList.add(buyBackCar);

		// Transfer money to the bank account (from dealer to client)
		float buyBackPrice = computeBuyBackPrice(buyBackCar, yearOfBuyBack, km);

		if (client.getAcount() == null) {
			throw new NoBankAcountException("No bank acount , no money !");
		} else
			client.getAcount().increseCountAmount(buyBackPrice);
	}

	/**
	 * this method compute an aprox. value of the car at buyBack this method is
	 * open for improvements
	 */
	private float computeBuyBackPrice(Car buyBackCar, int yearOfBuyBack, float km) {

		float initialPrice = buyBackCar.price;
		float actualPrice;
		int carAge = yearOfBuyBack - buyBackCar.year;

		if (carAge <= 3 && km <= 20000) {
			initialPrice -= 2000;
		} else if ((carAge > 3 && carAge <= 6) || (km > 20000 && km <= 80000)) {
			initialPrice -= 4000;
		} else if ((carAge > 6 && carAge <= 15) || (km > 80000 && km <= 150000)) {
			initialPrice -= 8000;
		} else if (carAge > 15 && km > 150000) {
			initialPrice -= 9000;
		}
		actualPrice = initialPrice;
		return actualPrice;
	}

	// this method add cars to available car list for fleet
	// i create a special constructor in Car to get useful object that describe
	// car characteristics and the rent price of them
	public List<Car> addCarsToFleet() {

		availbleCarsForFleet.add(new Aygo("Toyota", "Aygo", FuelType.PETROL, 2000, 100));
		availbleCarsForFleet.add(new Passat("Wolkswagen", "Passat", FuelType.DIESEL, 2014, 180));
		availbleCarsForFleet.add(new Yaris("Toyota", "Yaris", FuelType.PETROL, 2016, 130));
		availbleCarsForFleet.add(new Golf("Wolkswagen", "Golf", FuelType.PETROL, 2015, 160));
		availbleCarsForFleet.add(new Yaris("Toyota", "Yaris", FuelType.HIBRID, 2011, 200));
		availbleCarsForFleet.add(new Golf("Wolkswagen", "Golf", FuelType.DIESEL, 2014, 160));
		availbleCarsForFleet.add(new Golf("Wolkswagen", "Golf", FuelType.PETROL, 2012, 160));
		availbleCarsForFleet.add(new Yaris("Toyota", "Yaris", FuelType.HIBRID, 2004, 200));
		availbleCarsForFleet.add(new Golf("Wolkswagen", "Golf", FuelType.PETROL, 2010, 160));

		return availbleCarsForFleet;
	}

	public void fleetManagement() {

		// Arrange object in arrayList in decreasing order

		// here something gone wrong the object are not ordered by increasing
		// year
		// i implement compareTo() in Car
		boolean flag;
		int j = availbleCarsForFleet.size() - 1;

		do {
			flag = false;
			for (int i = 0; i < j; i++) {
				int value = availbleCarsForFleet.get(i).compareTo(availbleCarsForFleet.get(i + 1));
				if (value == -1) { // Suppose otherwise (increasing order)
					int temp = availbleCarsForFleet.get(i + 1).year;
					availbleCarsForFleet.get(i + 1).year = availbleCarsForFleet.get(i).year;
					availbleCarsForFleet.get(i).year = temp;
					flag = true;
				}
			}
			j--;

		} while (flag);

	}

	// this method handle no account client case
	public boolean noAcountClient(Client newClient) throws NoClientAcountException {

		// verify if the client has account
		if (!(clientAcount.getClient().getCnp() == newClient.getCnp())) {
			throw new NoClientAcountException("You dont have acount");
		} else
			return true;

	}

}
