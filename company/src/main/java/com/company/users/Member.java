package com.company.users;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.bridge.builtin.IntegerBridge;

import com.company.catalogue.BookUnit;
import com.company.catalogue.Fine;

@Entity
@Table(name = "member_tbl")
@Indexed
public class Member extends Person {
	@Embedded
	private final Account account;
	
	@ContainedIn
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
	@LazyCollection(LazyCollectionOption.FALSE)
	@FieldBridge(impl = IntegerBridge.class)
	private final List<BookUnit> borrowedBooks;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private int id;

	@Column(insertable = false, updatable = false)
	private String token;

//	@IndexedEmbedded
	@ContainedIn
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
	@LazyCollection(LazyCollectionOption.FALSE)
	@FieldBridge(impl = IntegerBridge.class)
	private final List<Fine> fines;

	public Member() {
		id = 0;
		account = null;
		borrowedBooks = new ArrayList<>();
		fines = new ArrayList<>();
	}

	Member(int id, String name, String phone, String country, String city, String postCode, Gender gender,
			String username, String password, String token) {
		super(name, phone, country, city, postCode, gender);
		this.account = new Account(username, password);
		this.borrowedBooks = new ArrayList<>();
		this.fines = new ArrayList<>();
		this.id = id;
		this.token = token;
	}


    public static class Account {
		enum AccountStatus { ACTIVE, CLOSED, BLACKLISTED, NONE }

		@Enumerated(EnumType.STRING)
		private final AccountStatus status;

		private final String username;
		private final String password;
		
		private String token;

		public Account() {
			username = "";
			password = "";
			token = null;
			status = AccountStatus.NONE;
		}

		public Account(String username, String password) {
			this.username = username;
			this.password = password;
			this.status = AccountStatus.ACTIVE;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((password == null) ? 0 : password.hashCode());
			result = prime * result + ((status == null) ? 0 : status.hashCode());
			result = prime * result + ((token == null) ? 0 : token.hashCode());
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
			Account other = (Account) obj;
			if (password == null) {
				if (other.password != null)
					return false;
			} else if (!password.equals(other.password))
				return false;
			if (status != other.status)
				return false;
			if (token == null) {
				if (other.token != null)
					return false;
			} else if (!token.equals(other.token))
				return false;
			if (username == null) {
				if (other.username != null)
					return false;
			} else if (!username.equals(other.username))
				return false;
			return true;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public AccountStatus getStatus() {
			return status;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;

		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((borrowedBooks == null) ? 0 : borrowedBooks.hashCode());
		result = prime * result + ((fines == null) ? 0 : fines.hashCode());
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (borrowedBooks == null) {
			if (other.borrowedBooks != null)
				return false;
		} else if (!borrowedBooks.equals(other.borrowedBooks))
			return false;
		if (fines == null) {
			if (other.fines != null)
				return false;
		} else if (!fines.equals(other.fines))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	public List<Fine> getFines() {
		return fines;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public List<BookUnit> getBorrowedBoooks() {
		return borrowedBooks;
	}

	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", account=" + account + ", borrowedBoooks=" + borrowedBooks + "]";
	}

}
