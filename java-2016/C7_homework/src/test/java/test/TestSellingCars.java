package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lib.Aygo;
import lib.BankAcount;
import lib.Car;
import lib.Car.FuelType;
import lib.CarDealerShip;
import lib.CarNotInTheListException;
import lib.Client;
import lib.FoundsNotEnoughException;
import lib.NoBankAcountException;
import lib.NoClientAcountException;
import lib.Yaris;

/*
 * This testClass test several functionality of Car delarship activity
 * i try to reach high cod coverage level
 * 
 * 1. If the dealer has a valid list for selling cars 
 * 2. If the car chosen by the client is in the available car list for sell 
 * 3. If the client has enough funds to by the car 
 * 4. If the bayed car is no more in the available car list (its important !) 
 * 5. If the money transfer take place (other words : the amount decrease after the client buy the car)
 * 6. If after the sell of the car take place, is created a clientEntry(account) 
 *     6.1 If the data form clientEntry are correct
 *     6.2 If the dealer can create more than one account (i create two accounts)
 *     6.3 If by some mistake the entries are not the same 
 *     6.4 If after a clientEntry(account) is created is not empty
 *7. If after buyback the car is not in the list of bayed cars
 *8. If after buyback the car is again in available car list for sell
 *9. If the amount of client is increased with price of buyback car 
 *10. For fleet management i try to arrange cars in increasing order of year 
 *    but because i don't understand to apply correctly the compareTo() method 
 *    i don't succeeded , so here i don't have test
 *11. For a client that has no account i run 2 tests (positive and negative flow)
 *
 *
 *
 * For this tests i create a useful constructor in the Car class with all that i need
 * (brand,model,chassinumber, fuelType,year,price) . 
 * 
 * For fleet managmeant i create a new constructor (with no chassinumber but with rentPrice)  
 * 
 * In this class (Car) i implement the hashcode and equals methods because this class provide all the
 * objects that i need .
 * 
 * I load several object (cars) in available car list and to fleet in init()
 * 
 * @author stefan1
 *
 */
public class TestSellingCars {

	CarDealerShip dealer = new CarDealerShip();

	@Before
	public void init() {
		dealer.addCarsToAvailbeCarsList();
		dealer.addCarsToFleet();
	}

	// the AvailableCarsList cannot be empty
	@Test
	public void testAvailableCarsList() {

		assertNotNull(dealer.availbleCarsList);
		// view available cars for sell
		dealer.viewAvailableCarsList();

	}

	// test if the chosen car is in the available car list
	@Test
	public void testCarList() {
		Car wishCar = new Aygo("Toyota", "Aygo", "1234", FuelType.PETROL, 2000, 10000);
		try {
			assertTrue(dealer.cheekCarInList(wishCar));
		} catch (CarNotInTheListException e) {
			System.out.println("The car that you want to buy is not in the available car list");
		}

	}

	// test if for the chosen car they are enough money in account
	@Test
	public void testEnoughMoney() {

		Car wishCar = new Aygo("Toyota", "Aygo", "1234", FuelType.PETROL, 2000, 10000);
		Client client = new Client("biro", "stefan", 1234567, new BankAcount(12000));
		try {
			assertTrue(dealer.cheekAcount(wishCar, client));
		} catch (FoundsNotEnoughException e) {
			System.out.println("Not enough money to buy this car");
		}

	}

	// here is tested that after a client buy a car, the car is not anymore in
	// available car list for sell
	@Test
	public void testBuyingCar() {
		Car wishCar = new Aygo("Toyota", "Aygo", "1234", FuelType.PETROL, 2000, 10000);
		Client client = new Client("biro", "stefan", 1234567, new BankAcount(12000));
		try {
			try {
				dealer.selCar(wishCar, client);
			} catch (FoundsNotEnoughException e) {
				System.out.println("Not enough money to buy this car");
			}
		} catch (CarNotInTheListException e) {
			System.out.println("The car that you want to buy is not in the available car list");
		}

		// cheek if sealed car is removed from available car list for sale
		boolean f = false;
		if (dealer.availbleCarsList.contains(wishCar)) {
			f = true;
		}
		assertFalse(f);
	}

	// here is tested that after a client buy a car the amount of his account is
	// decreased
	@Test
	public void testDecresingMoneyAmaount() {

		Car wishCar = new Aygo("Toyota", "Aygo", "1234", FuelType.PETROL, 2000, 10000);
		Client client = new Client("biro", "stefan", 1234567, new BankAcount(12000));
		try {
			try {
				dealer.selCar(wishCar, client);
			} catch (FoundsNotEnoughException e) {
				System.out.println("Not enough money to buy this car");
			}
		} catch (CarNotInTheListException e) {
			System.out.println("The car that you want to buy is not in the available car list");
		}

		// the car is 10000 euro
		assertEquals(2000, client.getAcount().getAmount(), 0);
	}

	// test if after buy a car a clientEntry is created
	@Test
	public void testClientEntry() {

		// ----------------------------FIRST-CLIENT------------------------------------//

		// create objects
		Car wishCar1 = new Yaris("Toyota", "Yaris", "1234", FuelType.PETROL, 2015, 10000);
		Client client1 = new Client("Biro", "Stefan", 1234567, new BankAcount(12000));

		// create an entry for first client
		dealer.createClientEntry(client1, wishCar1);

		// recovery of id (because the id update automatically for every new
		// client )
		int clientId1 = dealer.clientId;

		// verify if in the client with clientId1, Biro really buy a Yaris
		assertEquals("Yaris", dealer.clientEntry.get(clientId1).getlBuyedcars().get(0).getModel());
		assertEquals("Biro", dealer.clientEntry.get(clientId1).getClient().getfName());

		// --------------------------------SECOND-CLIENT---------------------------------//

		// create new client
		Car wishCar2 = new Aygo("Toyota", "Aygo", "1234", FuelType.PETROL, 2000, 10000);
		Client client2 = new Client("Bunea", "Alexandru", 1234567, new BankAcount(12000));

		// create an entry for a second client
		dealer.createClientEntry(client2, wishCar2);

		// recovery of id
		int clientId2 = dealer.clientId;

		// verify if in the client with clientId2, Bunea really buy a Aygo
		assertEquals("Aygo", dealer.clientEntry.get(clientId2).getlBuyedcars().get(0).getModel());
		assertEquals("Bunea", dealer.clientEntry.get(clientId2).getClient().getfName());

		// so the map clientEntry cannot be empty
		assertFalse(dealer.clientEntry.isEmpty());

		// test that the entry's in the clientEntry map are different
		// you cannot have two identical entry's
		assertNotSame(dealer.clientEntry.get(1).getlBuyedcars(), dealer.clientEntry.get(2).getlBuyedcars());
	}

	// this test cheek the buyBack activity
	@Test
	public void testBuyBack() {

		// car for first buy
		Car firstBuyCar = new Aygo("Toyota", "Aygo", "1234", FuelType.PETROL, 2000, 10000);
		Client clientBuy = new Client("Bunea", "Alexandru", 1234567, new BankAcount(12000));

		// buying the car
		try {
			dealer.selCar(firstBuyCar, clientBuy);
		} catch (CarNotInTheListException e) {
			System.out.println("The car that you want to buy is not in the available car list");

		} catch (FoundsNotEnoughException e) {
			System.out.println("Not enough money to buy this car");
		}

		// recovery of client id
		int clientId = dealer.clientId;

		// car for buyBack
		Car buyBackCar = new Aygo("Toyota", "Aygo", "1234", FuelType.PETROL, 2000, 10000);
		Client clientSell = new Client("Bunea", "Alexandru", 1234567, new BankAcount(20000));

		int yearOfBuyBack = 2013;
		float km = 60000; // we assume that he buy his car new - 0 km

		try {
			dealer.buyBack(buyBackCar, clientSell, yearOfBuyBack, km, clientId);
		} catch (NoBankAcountException e) {
			e.printStackTrace();
		}

		// test if the buyBackCar is not anymore in lBuyedCars of the client in
		// Client Account
		// the client remain but the car is removed
		assertFalse(dealer.clientEntry.get(clientId).getlBuyedcars().contains(buyBackCar));

		// test if the buyBackCar is added to availableCarList for sell again
		assertTrue(dealer.availbleCarsList.contains(buyBackCar));

		// test if the amount is increased after buyback (money transfer from
		// dealer to client)
		assertEquals(26000, clientSell.getAcount().getAmount(), 0);
	}

	// no test - something goes wrong in arranging objects in fleet list
	@Test
	public void testFleet() {

		dealer.fleetManagement();

	}

	// test if a client with account have an account (positive flow)
	@Test
	public void testNoAcountClientPositiveFlow() {

		// adding clients to clientEntry (the clientEntry cannot be empty if we
		// want to search in it ) so i add 4 clients

		// client 1
		Car wishCar1 = new Yaris("Toyota", "Yaris", "1234", FuelType.PETROL, 2015, 10000);
		Client client1 = new Client("Muresan", "Gabriel", 12345673, new BankAcount(1200000));
		dealer.createClientEntry(client1, wishCar1);

		// client 2
		Car wishCar2 = new Yaris("Toyota", "Yaris", "1234", FuelType.PETROL, 2015, 10000);
		Client client2 = new Client("Panin", "Cristian", 12345674, new BankAcount(1200000));
		dealer.createClientEntry(client2, wishCar2);

		// client 3
		Car wishCar3 = new Yaris("Toyota", "Yaris", "1234", FuelType.PETROL, 2015, 10000);
		Client client3 = new Client("Ricardo", "Cadu", 12345678, new BankAcount(1200000));
		dealer.createClientEntry(client3, wishCar3);

		// client 4
		Car wishCar4 = new Yaris("Toyota", "Yaris", "1234", FuelType.PETROL, 2015, 10000);
		Client client4 = new Client("Trica", "Eugen", 1234567, new BankAcount(1200000));
		dealer.createClientEntry(client4, wishCar4);

		// search if the new client has account (via cnp)
		Client newClient = new Client("Trica", "Eugen", 1234567, new BankAcount(1200000));

		try {
			dealer.noAcountClient(newClient);
		} catch (NoClientAcountException e) {
			System.out.println("Acces denied , you dont have an acount");
		}

		// test if the new client with 1234567 cnp has account or not
		try {
			assertTrue(dealer.noAcountClient(newClient));
		} catch (NoClientAcountException e) {
			System.out.println("Acces denied , you dont have an acount");
		}

	}

	// test if a client that has no account what happens (negative flow)
	// this give a message error
	@Test
	public void testNoAcountClientNegativeFlow() {

		// adding clients to clientEntry (the clientEntry cannot be empty if we
		// want to
		// search in it ) add 4 clients

		// client 1
		Car wishCar1 = new Yaris("Toyota", "Yaris", "1234", FuelType.PETROL, 2015, 10000);
		Client client1 = new Client("Muresan", "Gabriel", 12345673, new BankAcount(1200000));
		dealer.createClientEntry(client1, wishCar1);

		// client 2
		Car wishCar2 = new Yaris("Toyota", "Yaris", "1234", FuelType.PETROL, 2015, 10000);
		Client client2 = new Client("Panin", "Cristian", 12345674, new BankAcount(1200000));
		dealer.createClientEntry(client2, wishCar2);

		// client 3
		Car wishCar3 = new Yaris("Toyota", "Yaris", "1234", FuelType.PETROL, 2015, 10000);
		Client client3 = new Client("Ricardo", "Cadu", 12345678, new BankAcount(1200000));
		dealer.createClientEntry(client3, wishCar3);

		// client 4
		Car wishCar4 = new Yaris("Toyota", "Yaris", "1234", FuelType.PETROL, 2015, 10000);
		Client client4 = new Client("Trica", "Eugen", 1234567, new BankAcount(1200000));
		dealer.createClientEntry(client4, wishCar4);

		// this new client never has an account at delearship
		Client newClient = new Client("Biro", "Stefan", 12347189, new BankAcount(12000));

		try {
			dealer.noAcountClient(newClient);
		} catch (NoClientAcountException e) {
			System.out.println("Acces denied , you dont have an acount");
		}

		// test if the new client with 1234567 cnp has account or not
		try {
			assertFalse(dealer.noAcountClient(newClient));
		} catch (NoClientAcountException e) {
			System.out.println("Acces denied , you dont have an acount");
		}

	}

	@After
	public void destroy() {
		dealer.addCarsToAvailbeCarsList().clear();
		dealer.addCarsToFleet().clear();
	}

}