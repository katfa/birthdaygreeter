package hioa.mappe2.s171183;

public class Contact {
	private int id;
	private String firstName;
	private String lastName;
	private String birthday;
	private String phoneNumber;
	

	public Contact() {
	}

	public Contact(int id, String firstName, String lastName, String phoneNumber, String birthday) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.phoneNumber = phoneNumber;
	}

	public Contact(String firstName, String lastName, String phoneNumber, String birthday) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.phoneNumber = phoneNumber;
	}
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	public void setFirstName(String name) {
		firstName = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String name) {
		lastName = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setPhoneNumber(String number){
		phoneNumber = number;
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public void setBirthday(String birthday){
		this.birthday = birthday;
	}
	
	public String getBirthday(){
		return birthday;
	}
	
	public String toString(){
		if(lastName != "---") {
			return lastName + ", " + firstName;
		} else {
			return firstName;
		}
	}

}
