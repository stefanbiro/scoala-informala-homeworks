
package ro.stefan.testBooking;

import java.util.Calendar;
import java.util.Date;

import ro.stefan.booking.data.Accommodation;
import ro.stefan.booking.data.Customer;
import ro.stefan.booking.data.RoomFair;
import ro.stefan.booking.data.Season;
import ro.stefan.booking.data.Accommodation.AccommodationType;
import ro.stefan.booking.data.Season.SeasonType;

/**
 * This class provide common cod for test classes
 * in fact create objects from needed classes
 * 
 * @author stefan1
 *
 */
public class AbstractTestBooking {

	public Accommodation createNewRoom(AccommodationType type, RoomFair roomFair1) {
		Accommodation room = new Accommodation();
		room.setType(type);
		room.setFair(roomFair1);
		return room;
	}

	public RoomFair createRoomFair(double price, Season season) {
		RoomFair roomf = new RoomFair();
		roomf.setValue(price);
		roomf.setSeason(season);
		return roomf;
	}

	public Season createSeason(SeasonType type, Date from, Date to) {
		Season season = new Season();
		season.setType(type);
		season.setFrom(from);
		season.setTo(to);
		return season;
	}

	public Customer createObjectFromCustomer(String fname, String lName, String mail, String phone) {
		Customer customer = new Customer();
		customer.setfName(fname);
		customer.setlName(lName);
		customer.setEmail(mail);
		customer.setPhone(phone);
		return customer;
	}

	public Date getDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);
		return c.getTime();
	}
}
