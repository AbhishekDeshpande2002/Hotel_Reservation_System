package model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
	private final String firstName,lastName,email;
	
	public Customer(String email,String firstName,String lastName) {
		this.firstName=firstName;
		this.lastName=lastName;
		
		String emailRegex ="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher =pattern.matcher(email);
		
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Invalid Email format. Please enter a valid address.");
		}
		this.email=email;
	}
	
	public String toString() {
		return "First Name:"+firstName+"\nLast Name:"+lastName+"\nEmail:"+email;
	}

	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

}
