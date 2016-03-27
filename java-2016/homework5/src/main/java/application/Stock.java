package application;

//This class implements an interface for further implementation

public class Stock implements IStock {

	private Book book;
	private int amount;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public void addBook(Book book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBook(Book book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewBooks() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cheekBook(Book book) {
		// TODO Auto-generated method stub
		
	}

}
