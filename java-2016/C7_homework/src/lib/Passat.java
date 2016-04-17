package lib;

public class Passat extends Wolkswagen {
	
	
	// specific to Passat
	private float mediumFuelConsumption = 6.5f;

	public Passat(float currentFuelAmmount, String chasseNumber) {
		super(currentFuelAmmount, chasseNumber);
	}

	public Passat(String brand ,String model ,String cHASSENUMBER, FuelType fuelType, int year, float price) {
		super(brand,model,cHASSENUMBER, fuelType, year, price);

	}
	public Passat(String brand ,String model , FuelType fuelType, int year, float price) {
		super(brand,model, fuelType, year, price);

	}
	@Override
	public float getMediumFuelConsumption() {
		return mediumFuelConsumption;
	}

}
