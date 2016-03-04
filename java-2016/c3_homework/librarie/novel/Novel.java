package librarie.novel ;
import librarie.book.Book;

public class Novel extends Book{
	private String typeOfTheBook ;
	
	public Novel(String nameOfTheBook,int nrOfPages,String typeOfTheBook){
		super(nameOfTheBook,nrOfPages); // accesam constructorul superclasei cu doi parametrii
		this.typeOfTheBook = typeOfTheBook ;
	}
	public Novel(){
	}
	// metode getteri 
	public String getTypeOfTheBook(){
		return typeOfTheBook ;
	}
}