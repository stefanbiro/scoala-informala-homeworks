package lib;

public class Yaris extends Toyota {

	// specific to Yaris
	private float mediumFuelConsumption = 5.5f;

	public Yaris(float currentFuelAmmount, String chasseNumber) {
		super(currentFuelAmmount, chasseNumber);
	}

	@Override
	public float getMediumFuelConsumption() {
		return mediumFuelConsumption;
	}

}