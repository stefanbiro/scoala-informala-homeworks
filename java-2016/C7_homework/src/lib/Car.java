package lib;

public abstract class Car implements Vehicle, Comparable<Car> {

	protected String brand;
	protected String model;
	private String CHASSENUMBER;
	protected float currentFuelAmount;
	private float initialFuelAmount;
	protected FuelType fuelType;
	protected float consumption;
	protected int year;
	protected float price;
	protected float rentPrice;
	CarDealerShip dealarShip = new CarDealerShip();

	public Car(float currentFuelAmount, String CHASSENUMBER) {
		this.CHASSENUMBER = CHASSENUMBER;
		this.currentFuelAmount = currentFuelAmount;
	}

	public Car(float currentFuelAmount, FuelType fuelType) {
		this.currentFuelAmount = currentFuelAmount;
		this.fuelType = fuelType;

	}

	// constructor for cars for CarDealerShip
	public Car(String brand, String model, String cHASSENUMBER, FuelType fuelType, int year, float price) {
		super();
		this.brand = brand;
		this.model = model;
		CHASSENUMBER = cHASSENUMBER;
		this.fuelType = fuelType;
		this.year = year;
		this.price = price;
	}

	// constructor for cars for CarDealerShip fleet management
	public Car(String brand, String model, FuelType fuelType, int year, float rentPrice) {
		super();
		this.brand = brand;
		this.model = model;
		this.fuelType = fuelType;
		this.year = year;
		this.rentPrice = rentPrice;
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

	public enum FuelType {

		PETROL, DIESEL, ELECTRIC, HIBRID;
	}

	public int getYear() {
		return year;
	}

	public float getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return brand + ", " + model + ", " + CHASSENUMBER + ", " + fuelType + ", " + year + ", " + price + " euro"
				+ "\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CHASSENUMBER == null) ? 0 : CHASSENUMBER.hashCode());
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + Float.floatToIntBits(consumption);
		result = prime * result + ((fuelType == null) ? 0 : fuelType.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + Float.floatToIntBits(price);
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (CHASSENUMBER == null) {
			if (other.CHASSENUMBER != null)
				return false;
		} else if (!CHASSENUMBER.equals(other.CHASSENUMBER))
			return false;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (Float.floatToIntBits(consumption) != Float.floatToIntBits(other.consumption))
			return false;
		if (fuelType != other.fuelType)
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	public String getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	@Override
	public int compareTo(Car obj) {
		int value = 0;
		for (int i = 0; i < dealarShip.availbleCarsForFleet.size(); i++) {

			if (dealarShip.availbleCarsForFleet.get(i).year < obj.year) {
				value = -1;
			} else if (dealarShip.availbleCarsForFleet.get(i).year > obj.year) {
				value = 1;
			} else
				value = 0;
		}
		return value;
	}

}
