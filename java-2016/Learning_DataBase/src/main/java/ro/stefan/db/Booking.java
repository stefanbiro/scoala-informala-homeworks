package ro.stefan.db;

public class Booking {

	private Accommodation room;
	private BookingPeriod bookingPeriod;

	Customer c;
	int id;
	double price;
	StatusType statusType;

	// constructor for check availability
	public Booking(Accommodation room, BookingPeriod bookingPeriod) {
		this.setRoom(room);
		this.setBookingPeriod(bookingPeriod);
	}
    // constructor for book
	public Booking(Accommodation room, BookingPeriod bookingPeriod, Customer c, int id, double price,
			StatusType statusType) {
		this(room, bookingPeriod);
		this.c = c;
		this.id = id;
		this.price = price;
		this.statusType = statusType;
	}

	public Accommodation getRoom() {
		return room;
	}

	public void setRoom(Accommodation room) {
		this.room = room;
	}

	public BookingPeriod getBookingPeriod() {
		return bookingPeriod;
	}

	public void setBookingPeriod(BookingPeriod bookingPeriod) {
		this.bookingPeriod = bookingPeriod;
	}
	public int getId() {
		return id;
	}
	
}
