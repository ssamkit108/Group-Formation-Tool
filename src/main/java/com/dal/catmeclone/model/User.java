package com.dal.catmeclone.model;

	
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

	private String bannerId;

	private String firstName;

	private String lastName;

	private String password;

	private String email;

	private Role userRoles;

	private static final String email_regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

	/**
	 * 
	 */
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param bannerId
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param email
	 * @param userRoles
	 */
	public User(String bannerId, String firstName, String lastName, String password, String email, Role userRoles) {
		super();
		this.bannerId = bannerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.userRoles = userRoles;
	}
	

	

	public User(String bannerId, String firstName, String lastName, String password, String email) {
		super();
		this.bannerId = bannerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
	}

	public User(String bannerId, String firstName, String lastName, String email) {
		super();
		this.bannerId = bannerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public User(String bannerId) {
		super();
		this.bannerId = bannerId;
	}

	/**
	 * @return the bannerId
	 */
	public String getBannerId() {
		return bannerId;
	}

	/**
	 * @param bannerId the bannerId to set
	 */
	public void setBannerId(String bannerId) {
		this.bannerId = bannerId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the userRoles
	 */
	public Role getUserRoles() {
		return userRoles;
	}

	/**
	 * @param userRoles the userRoles to set
	 */
	public void setUserRoles(Role userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bannerId == null) ? 0 : bannerId.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (bannerId == null) {
			if (other.bannerId != null)
				return false;
		} else if (!bannerId.equals(other.bannerId))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	public static boolean isBannerIDValid(String bannerID) {
		if (null == bannerID) {
			return false;
		}
		return !bannerID.isEmpty();
	}



	public static boolean isFirstNameValid(String name) {
		if (null == name) {
			return false;
		}
		return !name.isEmpty();
	}

	public static boolean isLastNameValid(String name) {
		if (null == name) {
			return false;
		}
		return !name.isEmpty();
	}


	public static boolean isEmailValid(String email) {
		if (null == email || email.isEmpty()) {
			return false;
		}


		Pattern pattern = Pattern.compile(email_regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}
}
