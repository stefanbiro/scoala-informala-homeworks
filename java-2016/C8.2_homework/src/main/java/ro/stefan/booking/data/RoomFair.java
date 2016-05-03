package ro.stefan.booking.data;
/**
 * this class provide a room price according to season
 * 
 * @author stefan1
 * */
public class RoomFair {

	private double value;
	private Season season;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}
}
