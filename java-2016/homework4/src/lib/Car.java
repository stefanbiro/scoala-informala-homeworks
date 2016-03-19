package lib;

public abstract class Car implements Vehicle {

	private String CHASSENUMBER;
	protected float currentFuelAmount;
	private float initialFuelAmount;
	private final int FUEL_TANK_CAPACITY = 60;
	protected FuelType fuelType;
	private final int NR_OF_GEARS = 5;
	protected float consumption;

	public Car(float currentFuelAmount, String CHASSENUMBER) {
		this.CHASSENUMBER = CHASSENUMBER;
		this.currentFuelAmount = currentFuelAmount;
	}

	// from interface
	@Override
	public void start() {
		initialFuelAmount = currentFuelAmount;
		consumption = 0;
	}

	@Override
	public abstract void drive(float km);

	@Override
	public void stop() {
	 initialFuelAmount -= currentFuelAmount;
	}

	public abstract void shiftGear(int gears);

	public abstract float getAvailableFuel();

	public abstract float getAverageFuelConsumption();

	enum FuelType {

		PETROL, DIESEL, ELECTRIC, HIBRID;
	}

	public FuelType getFuelType() {
		return fuelType;
	}

}
