package com.company.app.data;

public class MemberView {
	private String gender;
	private String name;
	private String phone;
	private String country;
	private String city;
	private String postCode;
	private String username;
	private String password;
	private String status;
	
	public MemberView(String gender, String name, String phone, String country,
			String city, String postCode, String username, String password,
			String status) {
		this.gender = gender;
		this.name = name;
		this.phone = phone;
		this.country = country;
		this.city = city;
		this.postCode = postCode;
		this.username = username;
		this.password = password;
		this.status = status;
	}
	

	@Override
	public String toString() {
		return "{ \r\n" + 
				"   \"gender\":\"" + gender + "\",\r\n" + 
				"   \"name\":\"" + name + "\",\r\n" + 
				"   \"phone\":\"" + phone + "\",\r\n" + 
				"   \"address\":{ \r\n" + 
				"      \"country\":\"" + country + "\",\r\n" + 
				"      \"city\":\"" + city + "\",\r\n" + 
				"      \"postCode\":\"" + postCode + "\"\r\n" + 
				"   },\r\n" + 
				"   \"account\":{ \r\n" + 
				"      \"username\":\"" + username + "\",\r\n" + 
				"      \"password\":\"" + password + "\",\r\n" + 
				"      \"status\":\"" + status + "\"\r\n" + 
				"   }\r\n" + 
				"}";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((postCode == null) ? 0 : postCode.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		MemberView other = (MemberView) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (postCode == null) {
			if (other.postCode != null)
				return false;
		} else if (!postCode.equals(other.postCode))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
	
	
}