package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import application.*;

/**
 * This class test if a chosen book by customer decrease the amount of that book
 * from Stock
 * 
 * @author stefan1
 *
 */
public class TestCart {

	List<Stock> lStock;
	Book book1;
	TestCustomer testCustomer;
	Stock stock;
	int initialStockForTheBook;

	@Before
	public void init() {

		// Initializations
		initialStockForTheBook = 10;
		lStock = new ArrayList<>();
		stock = new Stock();

		// I need a customer who already chose a book
		// I do this from TestCustomer not from Customer
		// from Customer i think it would have been more correct
		testCustomer = new TestCustomer();
		testCustomer.initCustomer();

		// object from Book with isbn and amount of book with the same isbn
		book1 = new Book(12345l, initialStockForTheBook);

		// add in lStock
		lStock.add(createObjectFromStock(book1));

		// I get the isbn from the chosen book by customer
		long isbnOfBookChosenByCustomer = testCustomer.cart[0].getlCartEntry().get(0).getBook().getIsbn();
		decreseAmountBookFromStock(isbnOfBookChosenByCustomer);

	}

	private Stock createObjectFromStock(Book firstBook) {

		stock.setBook(firstBook);

		return stock;
	}

	public void decreseAmountBookFromStock(long isbn) {
		// we check if the isbn from Customer is the same with isbn from lStock
		// in this case the amount will decrees
		if (isbn == lStock.get(0).getBook().getIsbn()) {

			int amount = lStock.get(0).getBook().getAmount();
			amount--;
			lStock.get(0).getBook().setAmount(amount);
		}
	}

	// --------------------------TESTS--------------------------------//

	// test if after the customer chose a book the stock of the book is less
	@Test
	public void testStockAfterPurchase() {

		assertNotEquals(initialStockForTheBook, lStock.get(0).getBook().getAmount());
	}

	// the initialStockForTheBook has to be greater than 0
	@Test
	public void testInitialStock() {
		assertNotNull(initialStockForTheBook);

	}

}
