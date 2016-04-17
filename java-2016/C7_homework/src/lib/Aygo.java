package lib;



public class Aygo extends Toyota {
	
	

	// specific to Aygo
	private float mediumFuelConsumption = 4.5f;

	public Aygo(float currentFuelAmmount, String chasseNumber) {
		super(currentFuelAmmount, chasseNumber);
	}

	public Aygo(String brand , String model ,String cHASSENUMBER, FuelType fuelType, int year,float price) {
		super(brand,model,cHASSENUMBER, fuelType, year,price);
	}
	public Aygo(String brand , String model , FuelType fuelType, int year,float price) {
		super(brand,model, fuelType, year,price);
	}

	@Override
	public float getMediumFuelConsumption() {
		return mediumFuelConsumption;
	}

}