
package application;

/**
 * Interface that supply specific behaviors for Stock management
 * 
 * @author stefan1
 */
public interface IStock {
	
	abstract void addBook(Book book);

	abstract void removeBook(Book book);

	abstract void viewBooks();
	
	abstract void cheekBook(Book book);
}
