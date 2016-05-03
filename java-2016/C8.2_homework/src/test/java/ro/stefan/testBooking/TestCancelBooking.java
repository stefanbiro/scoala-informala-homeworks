package ro.stefan.testBooking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ro.stefan.booking.data.Accommodation;
import ro.stefan.booking.data.Accommodation.AccommodationType;
import ro.stefan.booking.data.Booking;
import ro.stefan.booking.data.BookingPeriod;
import ro.stefan.booking.data.Customer;
import ro.stefan.booking.data.RoomFair;
import ro.stefan.booking.data.Season;
import ro.stefan.booking.data.Season.SeasonType;
import ro.stefan.booking.data.StatusType;

/**
 * This test is for canceling an existing book
 * 
 * @author stefan1
 */

public class TestCancelBooking extends AbstractTestBooking {

	List<Booking> bookings;

	@Before
	public void init() {
		bookings = new ArrayList<>();
		Date from = getDate(2016, 5, 1);
		Date to = getDate(2016, 8, 27);
		Customer customer = createObjectFromCustomer("Biro", "Stefan", "hjhj@yahoo", "0747003892");
		Season season = createSeason(SeasonType.HIGH, from, to);
		RoomFair roomFair1 = createRoomFair(100d, season);
		Accommodation room = createNewRoom(AccommodationType.DOUBLE, roomFair1);
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
