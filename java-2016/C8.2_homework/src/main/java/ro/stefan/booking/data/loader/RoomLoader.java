package ro.stefan.booking.data.loader;

import java.io.*;
import ro.stefan.booking.exception.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.text.*;
import java.util.*;
import ro.stefan.booking.data.Accommodation.AccommodationType;
import ro.stefan.booking.data.Season.SeasonType;

/**
 * This class reads room info from files 
 * using the (RoomType,Season,price,dateIn,dateOut) fashion separated by ","
 * All the files (.txt) are in textFiles folder in src/main/java
 * The text file is roomsToLoad.txt
 * 
 * @author stefan1
 * */

public class RoomLoader {

	SimpleDateFormat sdf;
	Date date;

	// filePath
	private String periodsFile2 = "C:\\Users\\stefan1\\workspace1\\C8.1_homework\\src\\main\\java\\textFiles\\roomsToLoad.txt";

	public List<AccommodationType> roomTypes;
	public List<SeasonType> season;
	public List<Float> prices;
	public List<Date> dateFrom;
	public List<Date> dateTo;

	public void readFile()
			throws IllegalFormatStringInputException, IllegalDataForPriceException, IllegalFormatInputDateException {

		Path file1 = Paths.get(periodsFile2);

		roomTypes = new ArrayList<>();
		season = new ArrayList<>();
		prices = new ArrayList<>();
		dateFrom = new ArrayList<>();
		dateTo = new ArrayList<>();

		try {
			readDateFromFiles(file1, roomTypes, season, prices, dateFrom, dateTo);

		} catch (InvalidAttributeIdentiefierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// read lines from files
	private void readDateFromFiles(Path file1, List<AccommodationType> roomTypes, List<SeasonType> season,
			List<Float> prices, List<Date> roomFrom, List<Date> roomTo) throws InvalidAttributeIdentiefierException,
			IllegalFormatStringInputException, IllegalDataForPriceException, IllegalFormatInputDateException {

		Charset charset = Charset.forName("UTF8");

		// try with resources - close automatic the streams
		try (BufferedReader reader = Files.newBufferedReader(file1, charset)) {

			String line = null;
			while ((line = reader.readLine()) != null) {
				// the readied object will be store in an ArrayList (all)

				roomTypes.add(createObjectFromAccommodationType(line));
				season.add(createObjectFromSeasonType(line));
				prices.add(createObjectFromPrice(line));
				roomFrom.add(createObjectfromDateIn(line));
				roomTo.add(createObjectfromDateOut(line));
			}
		} catch (IOException x) {

			System.err.println("IOException " + x);
		}
	}

	// from the same line i extract every unit and i convert it to wished form

	// method to convert string from line into a float variable - price
	private Float createObjectFromPrice(String line) throws IllegalDataForPriceException {

		String[] dates = line.split(",");
		String regex = "[\\d]{3}";

		if (dates[2].trim().matches(regex)) {
			Float price = Float.parseFloat(dates[2].trim());
			return price;
		} else
			throw new IllegalDataForPriceException("");
	}

	// method to convert string from line into a seasonType variable - season
	private SeasonType createObjectFromSeasonType(String line) throws IllegalFormatStringInputException {

		String[] dates = line.split(",");

		String regex = "[A-Z]{3,4}"; // Only uppercase with 3 or 4 characters
										// only !

		if (dates[1].trim().matches(regex)) {
			SeasonType type = SeasonType.valueOf(dates[1].trim());
			return type;
		}
		throw new IllegalFormatStringInputException("Wrong format for season");
	}

	// method to convert string from line into a AccommodationType variable -
	// type of room
	private AccommodationType createObjectFromAccommodationType(String line) throws IllegalFormatStringInputException {

		String[] dates = line.split(","); // size 5

		String regex = "[A-Z]{1,10}"; // Only uppercase with max 10 characters

		if (dates[0].trim().matches(regex)) {

			AccommodationType type = AccommodationType.valueOf(dates[0].trim());
			return type;
		}
		throw new IllegalFormatStringInputException("Wrong format for room accomodation");
	}

	// method to convert string from line into a Date variable - dateIn
	public Date createObjectfromDateIn(String line) throws IllegalFormatInputDateException {

		String[] dates = line.split(","); // size 5

		String regex = "[\\d]{4}-[\\d]{2}-[\\d]{2}";

		if (dates[3].trim().matches(regex)) {

			return createDate(dates[3].trim());

		} else
			throw new IllegalFormatInputDateException("The input data must be only numbers");

	}

	// method to convert string from line into a Date variable - dateOut
	public Date createObjectfromDateOut(String line) throws IllegalFormatInputDateException {

		String[] dates = line.split(",");

		String regex = "[\\d]{4}-[\\d]{2}-[\\d]{2}";

		if (dates[4].trim().matches(regex)) {

			return createDate(dates[4].trim());

		} else
			throw new IllegalFormatInputDateException("The input data must be only numbers");
	}

	// method to convert string to Date object
	public Date createDate(String roomDate) {

		try {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(roomDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

}
