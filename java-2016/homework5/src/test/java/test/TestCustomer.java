package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import application.*;

/**
 * This class test if 1. elements of Address are correct 2. the cart is not
 * empty 3. the number of books chosen for buy is different from 0
 * 
 * @author stefan1
 *
 */
public class TestCustomer {

	List<Adress> lAdress;
	List<CartEntry> lCartEntry;
	Cart[] cart; // array of objects of type Cart

	@Before
	public void initCustomer() {

		// Initializations
		lAdress = new ArrayList<>();
		lCartEntry = new ArrayList<>();
		cart = new Cart[1]; // size of array is 1 because every Customer may
							// have a single cart in a single session of
							// shopping

		// I suppose that a customer may have different addresses
		lAdress.add(makeObjectFromAdress("Mehedinti", 23, "Cluj-Napoca"));

		// we make object from CartEntry(which involve a Book object and
		// quantities)
		// and this object will be added to a list of cartEntry

		// the object from Book is based upon a isbn number which is unique
		// (I make this type of constructor in Book class)
		lCartEntry.add(makeObjectfromCartEntry(makeObjectFromBook(12345l), 1));
		lCartEntry.add(makeObjectfromCartEntry(makeObjectFromBook(12346l), 1));

		// I can add how many Books i want in lCartEntry because the input value
		// for
		// a Cart is a list of CartEntry

		// add object to array -> that is mean a list of books and a total price
		// of them
		cart[0] = new Cart(lCartEntry, 20);

	}

	private CartEntry makeObjectfromCartEntry(Book book, int quantities) {
		CartEntry cartEntry = new CartEntry();
		cartEntry.setBook(book);
		cartEntry.setQuantities(quantities);
		return cartEntry;
	}

	private Book makeObjectFromBook(long isbn) {
		Book book = new Book();
		book.setIsbn(isbn);
		return book;
	}

	private Adress makeObjectFromAdress(String street, int number, String city) {
		Adress adress = new Adress();
		adress.setStreet(street);
		adress.setNumber(number);
		adress.setCity(city);
		return adress;
	}

	// ------------------------TESTS----------------------------------//

	// The list of CartEntry can not be empty
	@Test
	public void testlCartEntry() {

		assertNotNull(lCartEntry.size());
	}

	// The number of chosen books heave to be different from 0
	@Test
	public void testNrOfBook() {

		assertTrue(lCartEntry.get(0).getQuantities() > 0);
	}

	// Every customer on e-book store he must have an address
	@Test
	public void testLAdressSize() {

		assertNotNull(lAdress);
		assertTrue(lAdress.size() > 0);
	}

	// test if the numbers of street is real
	@Test
	public void testLAdressContentNo() {

		assertTrue(lAdress.get(0).getNumber() > 0);
	}

	// test if the street name is valid(only characters)
	@Test
	public void testLAdressContentStreet() {

		// regular expression -> any name with <20 characters
		String regex = "[A-Z][a-z]{1,20}";
		assertTrue(lAdress.get(0).getStreet().matches(regex));

	}

	// test city
	@Test
	public void testLAdressContentCity() {

		assertEquals("Cluj-Napoca", lAdress.get(0).getCity());
	}

	@After
	public void destroyCustomer() {
		lAdress = null;
		lCartEntry = null;
	}
}
