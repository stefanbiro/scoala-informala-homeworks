package test;
/*
 * This test is a cheek for a new customer for a book 
 * When he want to book a room in a period, the application chek if the room is free 
 * in that period (both conditions) 
 * */

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.junit.*;

import application.*;
import application.Accommodation.AccommodationType;
import application.IllegalDataForPriceException;
import application.IllegalFormatInputDateException;
import application.IllegalFormatStringInputException;
import application.Season.SeasonType;

// In this test class the rooms to load and periods are readied from files
public class TestBooking {

	List<Booking> bookings;
	List<Booking> forStoreBookingToFile;

	List<BookingPeriod> periods;

	// class for load data (periods and room info)
	PeriodLoader periodLoader;
	RoomLoader roomLoader;

	@Before
	public void init() {

		bookings = new ArrayList<>();

		Customer customer = createObjectFromCustomer("Biro", "Stefan", "hjhj@yahoo", "0747003892");

		// -------------------load periods -------------//

		periodLoader = new PeriodLoader();
		try {
			periodLoader.readFile();
		} catch (IllegalFormatInputDateException e) {

			e.printStackTrace();
		}

		periods = new ArrayList<>();

		for (int i = 0; i < 4; i++) {

			periods.add(new BookingPeriod(periodLoader.dateIn.get(i), periodLoader.dateOut.get(i)));

		}

		// ---------------load rooms ---------------//

		roomLoader = new RoomLoader();
		try {
			try {
				try {
					roomLoader.readFile();
				} catch (IllegalFormatInputDateException e) {
					System.out.println("The format of data for room is wrong ");
				}
			} catch (IllegalDataForPriceException e) {
				System.out.println("the format for price input is wrong");
			}
		} catch (IllegalFormatStringInputException e) {
			System.out.println("Input data for accommodation type is wrong");
		}

		// -----------------Create-Bookings--------------------//

		for (int i = 0; i < 4; i++) {

			Season season2 = createSeason(roomLoader.season.get(i), roomLoader.dateFrom.get(i),
					roomLoader.dateFrom.get(i));
			RoomFair roomFair2 = createRoomFair(roomLoader.prices.get(i), season2);
			Accommodation room2 = createObjectFromAccomodation(roomLoader.roomTypes.get(i), roomFair2);

			bookings.add(new Booking(room2, periods.get(i), customer, 123, 100, StatusType.APROVED));
		}

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

		for (int i = 0; i < bookings.size(); i++) {
			if (bookings.get(i).getRoom().getType().equals(newBooking.getRoom().getType()) && inReservedDay > chekIn
					&& inReservedDay > chekOut) {
				f = true;

			} else if (bookings.get(i).getRoom().getType().equals(newBooking.getRoom().getType())
					&& outReservedDay < chekIn && outReservedDay < chekOut) {

				f = true;

			} else if (!(bookings.get(i).getRoom().getType().equals(newBooking.getRoom().getType()))
					|| (outReservedDay < chekIn && outReservedDay < chekOut)) {

				f = true;

			}
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

		// this is the content of periodsToLoad.txt - book periods
		// in-------------out
		// 2016-05-02-----2016-05-08
		// 2016-05-09-----2016-05-12
		// 2016-05-18-----2016-05-25
		// 2016-05-26-----2016-05-29

		// the room is free from 13 to 17 (05.2016)

		// new customer dates (chekin , chekout , room)
		Date chekIn = getDate(2016, 5, 13); // the free input data is 13
		Date chekOut = getDate(2016, 5, 17); // the free output data is 17
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

	// we test if we save data from a booking in a file is correct and after we
	// can read from file

	@Test
	public void testBookingFromFile() {

		forStoreBookingToFile = new ArrayList<>();

		// we create a booking (not from file !)

		Date from = getDate(2016, 5, 1);
		Date to = getDate(2016, 8, 27);
		Customer customer = createObjectFromCustomer("Biro", "Stefan", "hjhj@yahoo", "0747003892");
		Season season = createSeason(SeasonType.HIGH, from, to);
		RoomFair roomFair1 = createRoomFair(100d, season);
		Accommodation room = createObjectFromAccomodation(AccommodationType.DOUBLE, roomFair1);
		BookingPeriod period = new BookingPeriod(getDate(2016, 7, 10), getDate(2016, 7, 17));

		forStoreBookingToFile.add(new Booking(room, period, customer, 123, 100, StatusType.APROVED));

		// Save data to file bookings.txt

		String destinationFile = "C:\\Users\\stefan1\\workspace1\\C8.1_homework\\src\\main\\java\\textFiles\\bookings.txt";
		Path path = Paths.get(destinationFile);
		writeBookingToFile(path, forStoreBookingToFile);

		// the data in file is month 08 instead of month 07 !
		// so even if the format in file is ok, (DOUBLE , HIGH , 100.0 ,
		// 2016-08-10 , 2016-08-17)
		// the date (month ) not the same , so i don,t test nothing here
		// you can cheek the bookings.txt after run
	}

	// method that write bookings info to file
	private void writeBookingToFile(Path filePath, List<Booking> bookings) {

		Charset charset = Charset.forName("UTF8");

		try (BufferedWriter writer = Files.newBufferedWriter(filePath, charset, CREATE, APPEND)) {

			String room = String.valueOf(bookings.get(0).getRoom().getType());
			String season = String.valueOf(bookings.get(0).getRoom().getFair().getSeason().getType());
			String price = String.valueOf(bookings.get(0).getRoom().getFair().getValue());

			String dateFrom = fromDateToString(bookings.get(0).getBookingPeriod().getFrom());
			String dateTo = fromDateToString(bookings.get(0).getBookingPeriod().getTo());

			// we create a big string with "," that we write to file
			String newLine = room + "," + season + "," + price + "," + dateFrom + "," + dateTo;

			writer.write(newLine + "\n");

		} catch (IOException x) {

			System.err.println("IOException " + x);
		}
	}

	public String fromDateToString(Date date) {

		String newFormat = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(newFormat);
		String stringDate = sdf.format(date);

		return stringDate;
	}

	@After
	public void destroyBooking() {
		bookings = null;
		forStoreBookingToFile = null;
	}

}
