package test;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import application.*;
import application.Accommodation.AccommodationType;
import application.BookingPeriod;
import application.IllegalFormatInputDateException;
import application.Season.SeasonType;

/**
 * This class is o modified version of the original files , where the periods
 * are readied from file for test availability
 */

public class TestAvailability extends AbstractBookingTest {

	List<Accommodation> rooms;
	List<BookingPeriod> periods;

	List<Booking> bookings;

	PeriodLoader periodLoader;

	@Before
	public void init() {

		rooms = new ArrayList<Accommodation>();

		Date from = getDate(2016, 5, 1);
		Date to = getDate(2016, 8, 27);

		Season season = createSeason(SeasonType.HIGH, from, to);

		RoomFair roomFair1 = createRoomFair(100, season);

		Accommodation room1 = createNewRoom(AccommodationType.ROYAL, roomFair1);

		rooms.add(room1);

		periods = new ArrayList<BookingPeriod>();

		periodLoader = new PeriodLoader();
		bookings = new ArrayList<Booking>();

		try {
			periodLoader.readFile();
		} catch (IllegalFormatInputDateException e) {

			System.out.println("Date from file not correct");
		}
		// reading periods from file , periods already booked (we have 4 booked
		// periods in file)

		for (int i = 0; i < 4; i++) {

			periods.add(new BookingPeriod(periodLoader.dateIn.get(i), periodLoader.dateOut.get(i)));

			// make bookings with periods readied from file
			bookings.add(new Booking(room1, periods.get(i)));
		}
	}

	private boolean findAccomodationTypeByPeriod(AccommodationType type, String periodToTest) {

		boolean f = false;

		Date from = null;
		try {
			from = periodLoader.createObjectfromDateIn(periodToTest.trim());
		} catch (IllegalFormatInputDateException e) {
			System.out.println("Date from file not correct");
		}
		Date to = null;
		try {
			to = periodLoader.createObjectfromDateOut(periodToTest.trim());
		} catch (IllegalFormatInputDateException e) {
			System.out.println("Date from file not correct");
		}

		for (Booking booking : bookings) {

			if (booking.getRoom().getType().equals(type) && booking.getBookingPeriod().getFrom().before(from)
					&& booking.getBookingPeriod().getTo().after(to)) {
				f = true;
				break;
			}

		}

		return f;
	}

	@Test
	public void testPeriod() {

		// testing a new period from a new client
		String periodToTest = "2016-05-13,2016-05-17"; // from 13-17 the room is
														// free

		assertFalse(findAccomodationTypeByPeriod(AccommodationType.ROYAL, periodToTest));

	}

}
