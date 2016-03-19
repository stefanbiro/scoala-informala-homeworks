package application;

import lib.*;

public class TestAplication {

	public TestAplication() {

		// Car car = new Car(); Car should be a base class.

		int currentFuelAmmount = 27;
		String chasseNumber = "oiqe0934hkkadsn";

		// Logan can extend from Dacia, while Dacia extends from Car
		Car car = new Golf(currentFuelAmmount, chasseNumber);

		car.start();
		car.shiftGear(1);
		car.drive(0.01f);// drives 0.01 Km
		car.shiftGear(2);
		car.drive(0.02f);
		car.shiftGear(3);
		car.drive(0.5f);
		car.shiftGear(4);
		car.drive(0.5f);
		car.shiftGear(4);
		car.drive(0.5f);
		car.shiftGear(5);
		car.drive(10);
		car.shiftGear(4);
		car.drive(0.5f);
		car.shiftGear(3);
		car.drive(0.1f);
		car.stop();

		float availableFuel = car.getAvailableFuel();
		System.out.println("Available fuel for Bandis car :  " + availableFuel);
		// this value must be smaller than the initial value passed in the
		// constructor

		float fuleConsumedPer100Km = car.getAverageFuelConsumption();

		System.out.println("Bandis car consumed " + fuleConsumedPer100Km + " l/100 km ");

		Vehicle vehicle = new Golf(30, "1987ddkshik289"); // available fuel and
															// chassis number

		vehicle.start();
		vehicle.drive(1);
		vehicle.stop();

		Car car2 = (Car) vehicle; // downcast

		availableFuel = car2.getAvailableFuel();
		System.out.println("The second car have  " + availableFuel + "l of fuel");
		float fuleConsumedPer100Km2 = car2.getAverageFuelConsumption();
		System.out.println("And have " + fuleConsumedPer100Km2 + " l/100km");

		// My cod for the second brand

		Car mycar = new Yaris(60, "909309430iui");
		Vehicle myCar = new Aygo(20, "9090090po");

		mycar.start();
		mycar.shiftGear(1);
		mycar.drive(0.1f);
		System.out.println(" ");
		System.out.println("Istvan car have " + mycar.getAvailableFuel() + " l of fuel");
		System.out.println("And consume : " + mycar.getAverageFuelConsumption() + " l/100km");

	}

	public static void main(String[] args) {

		TestAplication t = new TestAplication();

	}

}
