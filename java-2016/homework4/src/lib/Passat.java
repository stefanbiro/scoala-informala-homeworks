package lib;

public class Passat extends Wolkswagen {

	// specific to Passat
	private float mediumFuelConsumption = 6.5f; 

	public Passat(float currentFuelAmmount, String chasseNumber) {
		super(currentFuelAmmount, chasseNumber);
	}

	@Override
	public float getMediumFuelConsumption() {
		return mediumFuelConsumption;
	}

}
