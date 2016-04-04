package test;
/*
 * This test is a cheek for a new customer for a book 
 * When he want to book a room in a period, the application chek if the room is free 
 * in that period (both conditions) 
 * */

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

public class TestBooking {

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

	/*
	 * This method even he is not so elegant , he makes his job very well it was
	 * tested in multiple ways!
	 */
	private boolean testMultipleCondition(Booking newBooking, Date in, Date out) {

		// the in.getDate() was deprecated and i find this solution (not a short
		// solution but
		// works! )

		// I create 4 calendar instance
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		Calendar cal3 = Calendar.getInstance();
		Calendar cal4 = Calendar.getInstance();

		// i set time on them
		cal1.setTime(bookings.get(0).getBookingPeriod().getFrom());
		cal2.setTime(bookings.get(0).getBookingPeriod().getFrom());
		cal3.setTime(in);
		cal4.setTime(out);

		// i carried out the day (because for comparation i need ints ! not Date
		// objects)
		int inReservedDay = cal1.get(Calendar.DAY_OF_MONTH);
		int outReservedDay = cal2.get(Calendar.DAY_OF_MONTH);
		int chekIn = cal3.get(Calendar.DAY_OF_MONTH);
		int chekOut = cal4.get(Calendar.DAY_OF_MONTH);

		boolean f = false;

		if (bookings.get(0).getRoom().getType().equals(newBooking.getRoom().getType()) && inReservedDay > chekIn
				&& inReservedDay > chekOut) {
			f = true;

		} else if (bookings.get(0).getRoom().getType().equals(newBooking.getRoom().getType()) && outReservedDay < chekIn
				&& outReservedDay < chekOut) {

			f = true;

		} else if (!(bookings.get(0).getRoom().getType().equals(newBooking.getRoom().getType()))
				|| (outReservedDay < chekIn && outReservedDay < chekOut)) {

			f = true;

		}

		return f;

	}

	@Test
	public void testPositiveBooking() {

		// new customer
		Date from = getDate(2016, 5, 1);
		Date to = getDate(2016, 8, 27);
		Season season = createSeason(SeasonType.HIGH, from, to);
		RoomFair roomFair2 = createRoomFair(100d, season);

		// new customer dates (chekin , chekout , room)

		Date chekIn = getDate(2016, 7, 1); // the reserved in is 10
		Date chekOut = getDate(2016, 7, 9); // the reserved out is 17
		AccommodationType chosenRoom = AccommodationType.DOUBLE; // the reserved
																	// room is
																	// Double

		BookingPeriod bookingPeriod = new BookingPeriod(chekIn, chekOut);
		Booking newBooking = new Booking(createObjectFromAccomodation(chosenRoom, roomFair2), bookingPeriod);

		// we test if the chosen room in the chosen period of new customer is
		// not already taken

		// if this is true - the room in the chosen period is free!!!
		assertTrue("Period or room are booked", testMultipleCondition(newBooking, chekIn, chekOut));

	}

	@Test
	public void testNegativeBooking() {
		// new customer
		Date from = getDate(2016, 5, 1);
		Date to = getDate(2016, 8, 27);
		Season season = createSeason(SeasonType.HIGH, from, to);
		RoomFair roomFair2 = createRoomFair(100d, season);

		// negative(wrong) inputs from customer
		int yearIn = 2015;
		int monthIn = 0;
		int dayIn = 3; // wrong ! you can t book for a day before current day

		int yearOut = 2016;
		int monthOut = 4;
		int dayOut = 8;
		AccommodationType type = AccommodationType.DOUBLE;

		assertFalse(chekYears(yearIn, yearOut));
		assertFalse(chekMonts(monthIn, monthOut));
		assertFalse(chekDays(dayIn, dayOut));

		// if the dates is correct your book can take place
		if (chekYears(yearIn, yearOut) && chekMonts(monthIn, monthOut) && chekDays(dayIn, dayOut)) {

			Date chekIn = getDate(yearIn, monthIn, dayIn);
			Date chekOut = getDate(yearOut, monthOut, dayOut);

			AccommodationType chosenRoom = AccommodationType.DOUBLE;
			// the reserved room is Double

			BookingPeriod bookingPeriod = new BookingPeriod(chekIn, chekOut);
			Booking newBooking = new Booking(createObjectFromAccomodation(chosenRoom, roomFair2), bookingPeriod);

			assertTrue(testMultipleCondition(newBooking, chekIn, chekOut));

		}
	}

	private boolean chekDays(int dayIn, int dayOut) {
		boolean f = false;
		int[] availableDays = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
				25, 26, 27, 28, 29, 30 }; // for simplicity lets say that every
											// month has 30 days

		for (int i = 0; i < availableDays.length; i++) {
			if (dayIn == availableDays[i] && dayOut == availableDays[i]) {
				f = true;
			}
		}
		Calendar cal = Calendar.getInstance();
		int currentDay = cal.get(Calendar.DAY_OF_MONTH);
		if (dayIn >= currentDay && dayOut >= currentDay) {
			f = true;
		}

		return f;
	}

	// this method cheek if the the months are valid months
	private boolean chekMonts(int monthIn, int monthOut) {
		boolean f = false;
		int[] availableMonths = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		for (int i = 0; i < availableMonths.length; i++) {
			if (monthIn == availableMonths[i] && monthOut == availableMonths[i]) {
				f = true;
			}
		}

		Calendar cal = Calendar.getInstance();
		int currentMonth = cal.get(Calendar.MONTH);
		if (monthIn >= currentMonth && monthOut >= currentMonth) {
			f = true;
		}
		return f;
	}

	// this method cheek if the years are smaller than the current Year
	private boolean chekYears(int yearIn, int yearOut) {
		boolean f = false;
		Calendar cal = Calendar.getInstance();
		int currentyear = cal.get(Calendar.YEAR);
		if (yearIn >= currentyear && yearOut >= currentyear) {
			f = true;
		}
		return f;
	}

	@After
	public void destroyBooking() {
		bookings = null;
	}

}
