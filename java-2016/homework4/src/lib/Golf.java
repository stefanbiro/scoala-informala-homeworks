package lib;

public class Golf extends Wolkswagen {

	// specific to Golf
	private float mediumFuelConsumption = 5.5f;

	public Golf(float currentFuelAmmount, String chasseNumber) {
		super(currentFuelAmmount, chasseNumber);
	}

	@Override
	public float getMediumFuelConsumption() {
		return mediumFuelConsumption;
	}

}