package lib;

public class Yaris extends Toyota {

	// specific to Yaris
	private float mediumFuelConsumption = 5.5f;

	public Yaris(float currentFuelAmmount, String chasseNumber) {
		super(currentFuelAmmount, chasseNumber);
	}

	public Yaris(String brand, String model, String cHASSENUMBER, FuelType fuelType, int year, float price) {
		super(brand,model, cHASSENUMBER, fuelType, year, price);
	}
	public Yaris(String brand, String model, FuelType fuelType, int year, float price) {
		super(brand,model, fuelType, year, price);
	}

	@Override
	public float getMediumFuelConsumption() {
		return mediumFuelConsumption;
	}

}