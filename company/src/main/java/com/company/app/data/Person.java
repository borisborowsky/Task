package com.company.app.data;

import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@MappedSuperclass
public abstract class Person {
	
	
	public enum Gender { MALE, FEMALE }

	@Enumerated(EnumType.STRING)
	private final Gender gender;
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private final String name;
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private final String phone;
	
	@Embedded
	private final Address address;
	
	Person() {
		this.address = null;
		this.phone = "";
		this.gender = null;
		this.name = "";
	}
	
	Person(String name, String phone, String country,
			String city, String postCode, Gender gender) {
		this.name = name;
		this.phone = phone;
		this.address = new Address(country, city, postCode);
		this.gender = gender;
	}
	
	public static class Address {
		private String country;
		private String city;
		private String postCode;
		
		Address() {}
		
		Address(String country, String city, String postCode) {
			this.country = country;
			this.city = city;
			this.postCode = postCode;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((city == null) ? 0 : city.hashCode());
			result = prime * result + ((country == null) ? 0 : country.hashCode());
			result = prime * result + ((postCode == null) ? 0 : postCode.hashCode());
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
			Address other = (Address) obj;
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
			if (postCode == null) {
				if (other.postCode != null)
					return false;
			} else if (!postCode.equals(other.postCode))
				return false;
			return true;
		}
	}

	public String getPhone() {
		return phone;
	}

	public Address getAddress() {
		return address;
	}


	public Gender getGender() {
		return gender;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
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
		Person other = (Person) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (gender != other.gender)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}
	
	
}
