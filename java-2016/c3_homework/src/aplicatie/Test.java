/** clasa Test creeaza o interfata interactiva cu posibilitatea de a administra intrari de carti/
albume , stegere carti/albume , afisare publicatii .Clasa nu furnizeaza si salvarea pe disc a informatiilor
introduse de la tastatura
 autor Stefan Biro*/

package src.aplicatie;

import librarie.novel.Novel;
import librarie.album.Album;
import java.util.* ;

public class Test{
	// creeam ArrayListuri de tip referinta pentru a stoca obiecte din clasele respective
	ArrayList<Novel>lNovels;
	ArrayList<Album>lAlbums;
	// creeam referinte spre obiecte din clasele respective dar alocam memorie numai cand avem nevoie
	Novel n;
	Album a;
	Scanner s; // referinta pentru a introduce date de la tastatura
	
	public Test(){
		// ca sa putem folosii arraylisturile  si scanerul ele trebuie initializate
		lNovels = new ArrayList();
		lAlbums = new ArrayList();
		s  = new Scanner(System.in);
		
		char rasp1 = ' ';
		char rasp2 = ' ';
		System.out.println(" ");
		System.out.println("---------Buna ziua-----------");
		System.out.println(" ");
		
		do{
			System.out.println("Ce doriti sa faceti ?");
			System.out.println("---------------------");
			System.out.println("Adaugare carte noua - a");
			System.out.println("Adaugare album nou - b");
			System.out.println("Stergere carte  - c");
			System.out.println("Stergere album - d ");
			System.out.println("Afisare lista de publicatii - l");
			System.out.println("---------------------");
			rasp1 = s.next().charAt(0); // preluam un caracter de la tastatura
			switch(rasp1){
				case 'a':{
					addNovel();
				}
				break;
				case 'b':{
					addAlbum();
				}
				break;
				case 'c':{
					removeNovel();
				}
				break ;
				case 'd':{
					removeAlbum();
				}
				break ;
				case 'l':{
					listBooks();
				}
				break;
				default :{
					System.out.println("Nu avem aceasta varianta");
					break;
				}
			}
			System.out.println("Continuati aplicatia ? y/n");
			rasp2 = s.next().charAt(0);
		}
		while(rasp2 == 'y');
		System.out.println("Va mai asteptam !");
	}
	/*metoda adauga o carte , se creeaza un obiect din clasa Novel si se adauga in Arraylist*/
	public void addNovel(){
		System.out.println("Titlul cartii");
		String title = s.next();
		System.out.println("Numarul de pagini");
		int pages = s.nextInt();
		System.out.println("Genul cartii");
		String type = s.next();
	    n = new Novel(title,pages,type);
		// aduagam in ArrayList obiectul creat
		lNovels.add(n);
		
	}
	/*metoda adauga un album  */
	public void addAlbum(){
		System.out.println("Titlul albumului");
		String title = s.next();
		System.out.println("Numarul de pagini");
		int pages = s.nextInt();
		System.out.println("Calitatea hartiei -low,med,high-");
		String type = s.next();
		a = new Album(title,pages,type);
		// aduagam in ArrayList obiectul creat
		lAlbums.add(a);
		
	}
	/*in aceasta metoda se preia titlul de la tastatura si se cauta in ArrayList obiectul ce contine
    acel titlu si se elimina 	*/
	public void removeNovel(){
		System.out.println("Titlul cartii de sters");
		String novelToRemove = s.next();
		for(int i = 0 ; i<lNovels.size() ;i++){
			    // verificam daca exista un obiect cu titlul respectiv
			if(novelToRemove.equals(lNovels.get(i).getNameOfTheBook())){
				int indexOfObject = lNovels.indexOf(lNovels.get(i)); // preluam indexul obiectului
				lNovels.remove(indexOfObject); // indepartam obiectul din ArrayList
			}
		}
	}
	public void removeAlbum(){
		System.out.println("Titlul albumului de sters");
		String albumToRemove = s.next();
		for(int i = 0 ; i<lAlbums.size() ;i++){
			if(albumToRemove.equals(lAlbums.get(i).getNameOfTheBook())){ // verificare
				int indexOfObject = lAlbums.indexOf(lAlbums.get(i)); // preluam indexul obiectului
				lAlbums.remove(indexOfObject); // indepartam obiectul din ArrayList
			}
		}
		
	}
	/*metoda va afisa nr de carti, titlul ,paginiile si genul cartii
	 la fel si pentru album */
	public void listBooks(){
		
		System.out.println(" ");
		System.out.println("Carti : \n");
		System.out.println("Numarul de carti este  : " + lNovels.size() +"\n");
		
		for(int i = 0 ; i<lNovels.size();i++){
			// metodele getNameOfTheBook() si getNrOfPages() sunt preluate din superclasa
			System.out.println("Numele cartii: "+lNovels.get(i).getNameOfTheBook());
			System.out.println("Numarul de pagini: "+lNovels.get(i).getNrOfPages());
			System.out.println("Genul: "+lNovels.get(i).getTypeOfTheBook());
			System.out.println(" ");
		}
		System.out.println(" ");
		System.out.println("Albume : \n");
		System.out.println("Numarul de albume este  : " + lAlbums.size() +"\n");
		
		for(int i = 0 ; i<lAlbums.size();i++){
			// metodele getNameOfTheBook() si getNrOfPages() sunt preluate din superclasa
			System.out.println("Numele albumului: "+lAlbums.get(i).getNameOfTheBook());
			System.out.println("Numarul de pagini: "+lAlbums.get(i).getNrOfPages());
			System.out.println("Calitatea paginii: "+lAlbums.get(i).getPageQualities());
			System.out.println(" ");
		}
	}
	public static void main(String[] sir){
		Test t = new Test();
	}
}