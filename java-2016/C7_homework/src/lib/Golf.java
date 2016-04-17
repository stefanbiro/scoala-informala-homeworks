package lib;

public class Golf extends Wolkswagen {

	// specific to Golf
	// private FuelType fuelType;

	private float mediumFuelConsumption = 5.5f;

	public Golf(float currentFuelAmmount, String chasseNumber) {
		super(currentFuelAmmount, chasseNumber);
	}

	public Golf(float currentFuelAmmount, FuelType fuelType) {
		super(currentFuelAmmount, fuelType);
	}

	public Golf(String brand, String model, String cHASSENUMBER, FuelType fuelType, int year, float price) {
		super(brand, model, cHASSENUMBER, fuelType, year, price);

	}

	public Golf(String brand, String model, FuelType fuelType, int year, float price) {
		super(brand, model, fuelType, year, price);

	}

	@Override
	public float getMediumFuelConsumption() {
		return mediumFuelConsumption;
	}

}