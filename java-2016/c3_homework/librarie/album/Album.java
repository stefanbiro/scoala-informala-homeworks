package librarie.album ;
import librarie.book.Book;

public class Album extends Book{
	String pageQualities;
	
	public Album(String albumsName ,int nrOfPages , String pageQualities){
		super(albumsName,nrOfPages);
	    this.pageQualities = pageQualities ;
	}
	public Album(){
	}
	// metode getteri
	
	public String getPageQualities(){
		return pageQualities;
	}
}