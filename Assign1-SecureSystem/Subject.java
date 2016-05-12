/*
Subject class has a name, a level of access and a temp value. All have meothds to access the values.
*/
public class Subject {
	private String name = "";
	private int temp = 0;  //the value most recently read. Initially 0
	private int level;

	/*
	Setup
	*/
	Subject(String name, int level) {
		this.name = name;
		this.level =level;
	}	
	
	/*
	Get subject name
	*/
	public String getName(){
		return name;
	}
	
	/*
	Get subject temp value
	*/
	public int getTemp(){
		return temp;
	}

	/*
	Set subject name
	*/
	public void setTemp(int val) {
		this.temp = val;
	}
	
	/*
	Get subject level
	*/
	public int getLevel() {
		return level;
	}	
}
