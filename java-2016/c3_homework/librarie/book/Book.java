package librarie.book ;
// superclasa celor doua clase derivate Novels si Albums
public class Book{
	private String nameOfBook;
	private int nrOfPages;
	// avem doi constructori
	public Book(){
	}
	public Book(String nameOfBook,int nrOfPages){
		this.nameOfBook = nameOfBook ;
		this.nrOfPages = nrOfPages ;
	}
	// metode geteri
	
	public String getNameOfTheBook(){
		return nameOfBook ;
	}
	public int getNrOfPages(){
		return nrOfPages ;
	}
}