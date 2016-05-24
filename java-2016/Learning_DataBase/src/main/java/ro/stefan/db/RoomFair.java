package ro.stefan.db;

public class RoomFair {

	private float value;
	private Season.SeasonType season;
	
	

	public RoomFair(float value, Season.SeasonType season) {
		super();
		this.value = value;
		this.season = season;
	}

	public double getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Season.SeasonType getSeason() {
		return season;
	}

	public void setSeason(Season.SeasonType season) {
		this.season = season;
	}
	
}
