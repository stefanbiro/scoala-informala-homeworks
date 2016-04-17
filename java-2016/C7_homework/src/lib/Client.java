package lib;

public class Client {

	private String fName;
	private String LName;
	private long cnp;
	private BankAcount acount;

	public Client() {
		super();
	}

	public Client(String fName, String lName, long cnp, BankAcount acount) {

		this.fName = fName;
		LName = lName;
		this.cnp = cnp;
		this.acount = acount;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getLName() {
		return LName;
	}

	public void setLName(String lName) {
		LName = lName;
	}

	public long getCnp() {
		return cnp;
	}

	public void setCnp(long cnp) {
		this.cnp = cnp;
	}

	public BankAcount getAcount() {
		return acount;
	}

}
