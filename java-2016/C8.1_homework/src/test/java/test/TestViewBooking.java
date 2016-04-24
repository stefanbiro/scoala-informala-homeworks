package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.*;
import application.Accommodation.AccommodationType;
import application.Season.SeasonType;

public class TestViewBooking {

	List<Booking> bookings;
	Accommodation room;
	BookingPeriod period;
	Customer customer;
	Scanner s = new Scanner(System.in);

	@Before
	public void init() {
		bookings = new ArrayList<>();
		Date from = getDate(2016, 5, 1);
		Date to = getDate(2016, 8, 27);
		customer = createObjectFromCustomer("Biro", "Stefan", "hjhj@yahoo", "0747003892");
		Season season = createSeason(SeasonType.HIGH, from, to);
		RoomFair roomFair1 = createRoomFair(100d, season);
		room = createObjectFromAccomodation(AccommodationType.DOUBLE, roomFair1);
		period = new BookingPeriod(getDate(2016, 7, 1), getDate(2016, 7, 5));
		// here is missing the add of Booking object to bookings List
		// we add the object(in a test method) only after a customer
		// cheek the dates
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

	// at positive testing, the application after run assertions show the input
	// data to customer and if the input data are correct ,the booking process
	// can take place
	// at customer request
	@Test
	public void testDateBeforeBookingPositive() {
		// date for cheek
		int yearIn = 2016;
		int monthIn = 7;
		int dayIn = 1;

		int yearOut = 2016;
		int monthOut = 7;
		int dayOut = 5;
		AccommodationType type = AccommodationType.DOUBLE;

		Date in = getDate(yearIn, monthIn, dayIn);
		Date out = getDate(yearOut, monthOut, dayOut);

		assertEquals(in, (period.getFrom()));
		assertEquals(out, (period.getTo()));
		assertEquals(type, room.getType());
		// view book data and at customer request take place the booking
		viewBookingData(in, out, type);

	}

	@Test
	public void testDateBeforeBookingNegative() {
		// date for cheek
		int yearIn = 2016;
		int monthIn = 7;
		int dayIn = 1;

		int yearOut = 2016;
		int monthOut = 7;
		int dayOut = 5;
		AccommodationType type = AccommodationType.DOUBLE;

		Date in = getDate(yearIn, monthIn, dayIn);
		Date out = getDate(yearOut, monthOut, dayOut);

		// wrong aplication data from aplication
		// wrong chekIn
		Date wrongIn = getDate(2015, 5, 7);
		period.setFrom(wrongIn);
		Date wrongDateFrom = period.getFrom();
		assertNotSame(in, wrongDateFrom);

		// wrong chekOut
		Date wrongOut = getDate(2016, 2, 9);
		period.setFrom(wrongOut);
		Date wrongDateTo = period.getFrom();
		assertNotSame(in, wrongDateTo);

	}

	private void viewBookingData(Date in, Date out, AccommodationType type) {
		char response;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(in);
		cal2.setTime(out);

		System.out.println("Your data are : \n");
		System.out.println("Your cheekIn is : " + cal1.getTime());
		System.out.println("Your cheekOut is : " + cal2.getTime());
		System.out.println("Your room is : " + type.DOUBLE);
		System.out.println("Your date is corect ?  , i want to book ? y/n");

		response = s.next().charAt(0);
		if (response == 'y') {
			bookings.add(new Booking(room, period, customer, 123, 100, StatusType.APROVED));
		} else
			System.exit(0);

	}

	@After
	public void destroyObjects() {
		bookings = null;

	}

}
