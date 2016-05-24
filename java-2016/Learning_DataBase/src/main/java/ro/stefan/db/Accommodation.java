package ro.stefan.db;

public class Accommodation {

	private String description;
	private int maxGuests;
	private RoomFair fair;
	private String roomId;
	private float price;

	// intern enums
	private BedType bedType;
	private AccommodationType type;

	public Accommodation() {
		super();
	}

	public Accommodation(String roomFromFile, AccommodationType type2, String roomDescription, int maxGuest,
			BedType bedType2, float price) {
		this.roomId = roomFromFile;
		this.type = type2;
		this.description = roomDescription;
		this.maxGuests = maxGuest;
		this.bedType = bedType2;
		this.price = price;
	}

	public AccommodationType getType() {
		return type;
	}

	public void setType(AccommodationType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMaxGuests() {
		return maxGuests;
	}

	public void setMaxGuests(int maxGuests) {
		this.maxGuests = maxGuests;
	}

	public BedType getBedType() {
		return bedType;
	}

	public void setBedType(BedType bedType) {
		this.bedType = bedType;
	}

	public RoomFair getFair() {
		return fair;
	}

	public void setFair(RoomFair fair) {
		this.fair = fair;
	}

	public enum AccommodationType {

		SINGLE, DOUBLE, SUITE, ROYAL, PENTHOUSE,APART;
	}

	public enum BedType {
		SINGLE, QUEEN_SIZE, KING_SIZE,NORMAL_SIZE,VIP_SIZE,DOUBLE;
	}
}
