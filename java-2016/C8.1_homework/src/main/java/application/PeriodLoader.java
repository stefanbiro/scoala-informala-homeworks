package application;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.text.*;
import java.util.*;
/**
 * This class reads booking periods from files 
 * using the (chekIn,chelOut) fashion
 * All the files (.txt) are in textFiles folder in src/main/java
 * The text file is periodsToLoad.txt
 * */

public class PeriodLoader {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	// filePath
	private String periodsFile2 = "C:\\Users\\stefan1\\workspace1\\C8.1_homework\\src\\main\\java\\textFiles\\periodsToLoad.txt";

	public List<Date> dateIn;
	public List<Date> dateOut;

	public void readFile() throws IllegalFormatInputDateException {

		Path file1 = Paths.get(periodsFile2);

		dateIn = new ArrayList<>();
		dateOut = new ArrayList<>();

		try {
			readDateFromFiles(file1, dateIn, dateOut);

		} catch (InvalidAttributeIdentiefierException e) {

			e.printStackTrace();
		}

	}

	// reading data from files
	private void readDateFromFiles(Path file1, List<Date> in, List<Date> out)
			throws InvalidAttributeIdentiefierException, IllegalFormatInputDateException {

		Charset charset = Charset.forName("UTF8");

		// try with resources - automatic close of the streams
		try (BufferedReader reader = Files.newBufferedReader(file1, charset)) {

			String line = null;

			while ((line = reader.readLine()) != null) {

				// the readied object will be store in an ArrayList
				in.add(createObjectfromDateIn(line));
				out.add(createObjectfromDateOut(line));
			}
		} catch (IOException x) {

			System.err.println("IOException " + x);
		}
	}

	// TODO see the comments in RooLoader regarding these methods
	// this method convert string to date (chekIn)
	public Date createObjectfromDateIn(String line) throws IllegalFormatInputDateException {

		// the format of dates yyyy-mm-dd
		String regex = "[\\d]{4}-[\\d]{2}-[\\d]{2}";

		String[] dates = line.split(","); // between chekIn and checkOut is ","
											// separator

		if (dates[0].matches(regex)) {

			return createDate(dates[0].trim());

		} else
			throw new IllegalFormatInputDateException("The input data must be only numbers");
	}

	// this method convert string to date (chekOut)
	public Date createObjectfromDateOut(String line) throws IllegalFormatInputDateException {

		String regex = "[\\d]{4}-[\\d]{2}-[\\d]{2}";

		String[] dates = line.split(",");

		if (dates[1].matches(regex)) {

			return createDate(dates[1].trim());

		} else
			throw new IllegalFormatInputDateException("The input data must be only numbers");
	}

	// method that create Date object from string
	public Date createDate(String line) {
		Date date = null;
		try {
			date = sdf.parse(line);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}
}