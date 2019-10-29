package com.company.app.data;

import java.util.Date;
import java.util.Objects;

public abstract class Book {

	private final String title;

	private final String type;

	private final String subject;

	private final String authour;

	private final Date publishDate;
	
	Book() {
		title = "";
		type = "";
		subject = "";
		authour = "";
		publishDate = new Date();
	}
	
	Book(String title, String type, String subject,
			String authour, Date publishDate) {
		this.title = title;
		this.type = type;
		this.subject = subject;
		this.authour = authour;
		this.publishDate = Objects.requireNonNull(publishDate);
	}

	

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public String getSubject() {
		return subject;
	}

	public String getAuthour() {
		return authour;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authour == null) ? 0 : authour.hashCode());
		result = prime * result + ((publishDate == null) ? 0 : publishDate.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Book other = (Book) obj;
		if (authour == null) {
			if (other.authour != null)
				return false;
		} else if (!authour.equals(other.authour))
			return false;
		if (publishDate == null) {
			if (other.publishDate != null)
				return false;
		} else if (!publishDate.equals(other.publishDate))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
	
	
	
}
