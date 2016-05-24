package ro.stefan.db;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ro.stefan.db.Accommodation.AccommodationType;
import ro.stefan.db.Accommodation.BedType;
import ro.stefan.db.Season.SeasonType;

public class TestDB {

	Connection conn;
	List<Float> prices; // a list that will store prices from db
	List<Accommodation> rooms = new LinkedList<>();
	List<RoomFair> roomFairs = new LinkedList<>();

	Accommodation acc;
	RoomFair rf;

	@Before
	public void init() {

		// load a jdbc driver
		try {
			Class.forName("org.postgresql.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		// create a connection
		conn = connect("postgresql", "localhost", 5432, "Booking", "postgres", "stefan");
		if (conn == null)
			return;

		prices = new LinkedList<>();
	}

	private Connection connect(String type, String host, int port, String dbName, String user, String pw) {

		Connection conn = null;
		DriverManager.setLoginTimeout(60);

		try {
			String url = new StringBuilder().append("jdbc:").append(type).append("://").append(host).append(":")
					.append(port).append("/").append(dbName).append("?user=").append(user).append("&password=")
					.append(pw).toString();

			System.out.println("URL:" + url);

			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.err.println("Cannot connect to the database: " + e.getMessage());
		}

		return conn;
	}

	/**
	 * this test is commented because i already insert data in db
	 */
	// @Test
	public void testInsert() {

		PreparedStatement statment1 = null;
		PreparedStatement statment2 = null;
		PreparedStatement statment3 = null;

		try {

			// insert values to first table - accommodation

			statment1 = conn.prepareStatement(
					"insert into accomodation(id,type,bed_type,max_quests,description) values(?,?,?,?,?) ");
			statment1.setInt(1, 3);
			statment1.setString(2, "single");
			statment1.setString(3, "single");
			statment1.setFloat(4, 1);
			statment1.setString(5, "cu cabina dus");

			statment1.executeUpdate();

			// insert values to second table - room_fair

			statment2 = conn.prepareStatement("insert into room_fair(id,value,season) values(?,?,?) ");
			statment2.setInt(1, 3);
			statment2.setFloat(2, 100);
			statment2.setString(3, "low");

			statment2.executeUpdate();

			// insert values to the third table - accomodation_fair_relation

			statment3 = conn.prepareStatement(
					"insert into accomodation_fair_relation(id,id_accomodation,id_room_fair) values(?,?,?) ");
			statment3.setInt(1, 3);
			statment3.setInt(2, 3);
			statment3.setInt(3, 3);

			statment3.executeUpdate();

		} catch (SQLException e) {
			System.err.println(" Cannot insert something " + e.getMessage());

		} finally {

			if (statment1 != null || statment2 != null || statment3 != null)
				try {
					statment1.close();
					statment2.close();
					statment3.close();
				} catch (SQLException e) {
				}
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}

	}

	/**
	 * This test is for test if data from db are the expected one
	 */
	// @Test
	public void testSelect1() {

		PreparedStatement statment = null;
		ResultSet resultSet = null;

		final String format = "%10s\n";

		try {
			String sqlStatemant = "select value from accomodation_fair_relation inner join room_fair on accomodation_fair_relation.id_room_fair=room_fair.id;";
			statment = conn.prepareStatement(sqlStatemant);

			resultSet = statment.executeQuery();

			boolean hasResults = resultSet.next();

			if (hasResults) {
				System.out.format(format, "prices of rooms");

				do {
					System.out.format(format, resultSet.getFloat("value"));

					// adding the prices from db in list
					prices.add(resultSet.getFloat("value"));

				} while (resultSet.next());

			} else {
				System.out.println("No results");
			}

		} catch (SQLException e) {
			System.err.println("Cannot execute query: " + e.getMessage());

		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			if (statment != null)
				try {
					statment.close();
				} catch (SQLException e) {
				}
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}

		// test if the data from db is the expected one
		assertEquals(120, prices.get(0), 0);
		assertEquals(180, prices.get(1), 0);
		assertEquals(100, prices.get(2), 0);

	}

	/**
	 * this test if we can make a booking from data readied from db
	 */
	@Test
	public void createObjectFromDB() {

		PreparedStatement statment1 = null;
		PreparedStatement statment2 = null;
		ResultSet resultSet1 = null;
		ResultSet resultSet2 = null;

		try {

			statment1 = conn.prepareStatement("select * from accomodation ");
			statment2 = conn.prepareStatement("select value,season from room_fair ");

			resultSet1 = statment1.executeQuery();
			resultSet2 = statment2.executeQuery();

			boolean hasResults1 = resultSet1.next();
			boolean hasResults2 = resultSet2.next();

			if (hasResults1 && hasResults2) {

				do {

					// conversion data from db to data entry in constructor
					String idFromDB = String.valueOf(resultSet1.getObject(1));

					String roomT = String.valueOf(resultSet1.getObject(2));
					AccommodationType roomTypeFromDB = AccommodationType.valueOf(roomT);

					String bedT = String.valueOf(resultSet1.getObject(3));
					BedType bedTypeFromDB = BedType.valueOf(bedT);

					int max_GuestFromDB = (int) resultSet1.getObject(4);
					String description = (String) resultSet1.getObject(5);

					float priceFromDB = resultSet2.getFloat("value");

					// instantation of accommodation with data from db
					Accommodation obj1 = new Accommodation(idFromDB, roomTypeFromDB, description, max_GuestFromDB,
							bedTypeFromDB, priceFromDB);
					// adding to room list
					rooms.add(obj1);

					// data conversion from db
					String seasonT = String.valueOf(resultSet2.getObject(2));
					SeasonType seasonTypeFromDB = Season.SeasonType.valueOf(seasonT);

					// instantation of accommodation with data from db
					RoomFair obj2 = new RoomFair(priceFromDB, seasonTypeFromDB);

					// adding to roomfairList
					roomFairs.add(obj2);

				} while (resultSet1.next() && resultSet2.next());

			} else {
				System.out.println("No results");
			}

		} catch (SQLException e) {
			System.err.println("Cannot execute query: " + e.getMessage());

		} finally {
			if (resultSet1 != null && resultSet2 != null)
				try {
					resultSet1.close();
					resultSet2.close();
				} catch (SQLException e) {
				}
			if (statment1 != null && statment2 != null)
				try {
					statment1.close();
					statment2.close();
				} catch (SQLException e) {
				}
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		// aici verific daca s-au creat obiectele
		System.out.println("---------------");
		for(Accommodation a : rooms){
			 System.out.println(a.getType());
			 System.out.println(a.getDescription());
		}
		System.out.println("---------------");
		
		for(RoomFair r : roomFairs){
			 System.out.println(r.getValue());
			 System.out.println(r.getSeason());
		}

	}

}
