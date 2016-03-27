package application;

public class Book {

	private String title;
	private String genre;
	private String author;
	private int weigth;
	private long isbn;
	private double price;
	private int amount;

	public Book(String title, String genre, String author, int weigth, long isbn, double price) {
		super();
		this.title = title;
		this.genre = genre;
		this.author = author;
		this.weigth = weigth;
		this.isbn = isbn;
		this.price = price;
	}

	// new constructor
	public Book(long isbn, int amount) {
		this.isbn = isbn;
		this.amount = amount;
	}

	public Book(long isbn) {

		this.isbn = isbn;
	}

	public Book() {
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getWeigth() {
		return weigth;
	}

	public void setWeigth(int weigth) {
		this.weigth = weigth;
	}

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
