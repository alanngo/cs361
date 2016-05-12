//Class that stores the information for each user
public class SystemUser {
	String firstName;
	String lastName;
	String salt;
	String pw;
	String encField = "";
	
	public SystemUser(String fName, String lName, String s, String password) {
		firstName = fName;
		lastName = lName;
		salt =s;
		pw = password;
		encField = s + "" +password;
	}
}
