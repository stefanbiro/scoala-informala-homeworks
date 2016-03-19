package lib;
public class Aygo extends Toyota {

	// specific to Aygo
	private float mediumFuelConsumption = 4.5f; 

	public Aygo(float currentFuelAmmount, String chasseNumber) {
		super(currentFuelAmmount, chasseNumber);
	}

	@Override
	public float getMediumFuelConsumption() {
		return mediumFuelConsumption;
	}

}