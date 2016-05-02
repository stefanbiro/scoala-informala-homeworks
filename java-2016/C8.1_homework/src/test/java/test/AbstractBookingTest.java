package test;

import java.util.Calendar;
import java.util.Date;

import application.Accommodation;
import application.Accommodation.AccommodationType;
import application.Customer;
import application.RoomFair;
import application.Season;
import application.Season.SeasonType;

/**
 * Abstract class to hold builder methods common to all tests.
 * 
 * @author alex
 *
 */
public abstract class AbstractBookingTest {
	protected Accommodation createObjectFromAccomodation(AccommodationType type, RoomFair roomFair1) {
		Accommodation room = new Accommodation();
		room.setType(type);
		room.setFair(roomFair1);
		return room;
	}

	protected RoomFair createRoomFair(double price, Season season) {
		RoomFair roomf = new RoomFair();
		roomf.setValue(price);
		roomf.setSeason(season);
		return roomf;
	}

	protected Customer createObjectFromCustomer(String fname, String lName, String mail, String phone) {
		Customer customer = new Customer();
		customer.setfName(fname);
		customer.setlName(lName);
		customer.setEmail(mail);
		customer.setPhone(phone);
		return customer;
	}

	protected Accommodation createNewRoom(AccommodationType type, RoomFair fair) {
		Accommodation accomodation = new Accommodation();
		accomodation.setType(type);
		accomodation.setFair(fair);
		return accomodation;
	}

	protected RoomFair createRoomFair(int i, Season season) {
		RoomFair roomFair = new RoomFair();
		roomFair.setSeason(season);
		roomFair.setValue(i);
		return roomFair;
	}

	protected Season createSeason(SeasonType type, Date from, Date to) {
		Season season = new Season();
		season.setFrom(from);
		season.setTo(to);
		season.setType(type);
		return season;
	}

	protected Date getDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);

		return c.getTime();
	}
}
