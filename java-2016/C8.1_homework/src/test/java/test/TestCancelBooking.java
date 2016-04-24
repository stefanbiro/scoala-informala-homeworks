package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.*;
import application.Accommodation.AccommodationType;
import application.Season.SeasonType;

public class TestCancelBooking {

	List<Booking> bookings;

	@Before
	public void init() {
		bookings = new ArrayList<>();
		Date from = getDate(2016, 5, 1);
		Date to = getDate(2016, 8, 27);
		Customer customer = createObjectFromCustomer("Biro", "Stefan", "hjhj@yahoo", "0747003892");
		Season season = createSeason(SeasonType.HIGH, from, to);
		RoomFair roomFair1 = createRoomFair(100d, season);
		Accommodation room = createObjectFromAccomodation(AccommodationType.DOUBLE, roomFair1);
		BookingPeriod period = new BookingPeriod(getDate(2016, 7, 10), getDate(2016, 7, 17));

		// a reserved room
		bookings.add(new Booking(room, period, customer, 123, 100, StatusType.APROVED));

	}

	private Accommodation createObjectFromAccomodation(AccommodationType type, RoomFair roomFair1) {
		Accommodation room = new Accommodation();
		room.setType(type);
		room.setFair(roomFair1);
		return room;
	}

	private RoomFair createRoomFair(double price, Season season) {
		RoomFair roomf = new RoomFair();
		roomf.setValue(price);
		roomf.setSeason(season);
		return roomf;
	}

	private Season createSeason(SeasonType type, Date from, Date to) {
		Season season = new Season();
		season.setType(type);
		season.setFrom(from);
		season.setTo(to);
		return season;
	}

	private Customer createObjectFromCustomer(String fname, String lName, String mail, String phone) {
		Customer customer = new Customer();
		customer.setfName(fname);
		customer.setlName(lName);
		customer.setEmail(mail);
		customer.setPhone(phone);
		return customer;
	}

	private Date getDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);
		return c.getTime();
	}

	@Test
	public void testCancellingBookingPositive() {
		String customerDesire = "cancel my booking";
		int bookingId = 123;
		if (customerDesire.equals("cancel my booking")) {
			if (bookings.get(0).getId() == bookingId) {
				bookings.clear();
			}
		}
		assertTrue("the cancelation is failed", bookings.size() < 1);

	}

	@Test
	public void testCancellingBookingNegative() {

		String customerDesire = "cancel my booking";
		int bookingId = 124; // wrong idBooking
		if (customerDesire.equals("cancel my booking")) {
			if (bookings.get(0).getId() == bookingId) {
				bookings.clear();
			}
		}
		assertFalse(bookings.size() < 1);
	}

	@After
	public void destroy() {
		bookings = null;
	}
}
