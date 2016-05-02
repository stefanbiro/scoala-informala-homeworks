package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.Accommodation;
import application.Accommodation.AccommodationType;
import application.Booking;
import application.BookingPeriod;
import application.Customer;
import application.RoomFair;
import application.Season;
import application.Season.SeasonType;
import application.StatusType;

public class TestCancelBooking extends AbstractBookingTest {

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
