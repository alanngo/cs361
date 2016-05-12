/*
Object class has a name, a level of access and a temp value. All have meothds to access the values.
*/
public class Object {
	private String name = "";
	public int val = 0;
	private int level;

	/*
	Setup
	*/
	Object(String name, int level) {
		this.name = name;
		this.level  = level;
	}
	
	/*
	Get object name
	*/
	public String getName(){
		return name;
	}
	
	/*
	Get object value
	*/
	public int getVal(){
		return val;
	}
	
	/*
	Set object level
	*/
	public void setVal(int val){
		this.val = val;
	}
	
	
	/*
	Get object level
	*/
	public int getLevel() {
		return level;
	}
}
