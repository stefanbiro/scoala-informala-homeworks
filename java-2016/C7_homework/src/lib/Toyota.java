package lib;



public abstract class Toyota extends Car {
	
	

	/**
	 * gear factor --> the consumption is influenced also by the gear. Biggest
	 * is the gear, lowest is the fuel consumption.I assume that is specific for
	 * every brand This value will be different for every gear and he will
	 * provide diffrent consumption in drive
	 */
	private float gearFact = 1;
	/**
	 * driveDistance --> a variable that holds the hole distance form start and
	 * stop of the car
	 */

	private float driveDistance = 0;

	public Toyota(float currentFuelAmount, String chasseNumber) {
		super(currentFuelAmount, chasseNumber);
	}

	public Toyota(String brand , String model ,String cHASSENUMBER, FuelType fuelType, int year,float price) {
		super(brand,model ,cHASSENUMBER, fuelType, year,price);
	}
	public Toyota(String brand , String model , FuelType fuelType, int year,float price) {
		super(brand,model , fuelType, year,price);
	}

	public abstract float getMediumFuelConsumption();

	@Override
	public void shiftGear(int gears) {
		gearFact = 1; // reset of the gearFact
		if (gears == 1) {
			gearFact *= 1;
		} else if (gears == 2) {
			gearFact *= 0.9;
		} else if (gears == 3) {
			gearFact *= 0.8;
		} else if (gears == 4) {
			gearFact *= 0.7;
		} else {
			gearFact *= 0.6;
		}

	}

	@Override
	public void drive(float km) {
		consumption += gearFact * (getMediumFuelConsumption() * km) / 100;
		driveDistance += km;
	}

	@Override
	public float getAvailableFuel() {

		return currentFuelAmount - consumption;
	}

	@Override
	public float getAverageFuelConsumption() {
		float consumedFuel = currentFuelAmount - getAvailableFuel();
		float averageFuelConsumption = (consumedFuel * 100) / driveDistance;
		return averageFuelConsumption;
	}

}
