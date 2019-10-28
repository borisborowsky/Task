package com.company.app.data;

import java.util.Date;

public class BookView {
	private int id;
	private int memberId;
	private String title;
	private String type;
	private String subject;
	private String authour;
	private String publishDate;
	private double borrowPrice;
	private Date borrowDate;
	private Date returnDate;
	private String bookStatus;

	
	public BookView(int id, String title, String type, String subject, String authour,
			String publishDate, double borrowPrice, String bookStatus) {
		this.title = title;
		this.type = type;
		this.subject = subject;
		this.authour = authour;
		this.publishDate = publishDate;
		this.borrowPrice = borrowPrice;
		this.bookStatus = bookStatus;
		this.id = id;
	}

	public String createJsonString() {
		return "{ \r\n" + 
				"	   \"title\":\"" + title + "\"" + ",\r\n" + 
				"	   \"type\":\"" + type + "\"" + ",\r\n" + 
				"	   \"subject\":\"" + subject + "\"" + ",\r\n" + 
				"	   \"authour\":\"" + authour + "\"" + ",\r\n" + 
				"	   \"publishDate\":" + "\"" + publishDate + "\"" + ",\r\n" + 
				"	   \"borrowPrice\":" + "\"" + borrowPrice + "\"" + ",\r\n" + 
				"	   \"bookStatus\":" + "\"" + bookStatus + "\"" +   ",\r\n" + 
				"      \"id\":" + "\"" + id + "\"" + "\r\n" +
				"}";
	}



	public int getId() {
		return id;
	}

    

	public void setId(int id) {
		this.id = id;
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



	public String getPublishDate() {
		return publishDate;
	}



	public double getBorrowPrice() {
		return borrowPrice;
	}



	public Date getBorrowDate() {
		return borrowDate;
	}



	public Date getReturnDate() {
		return returnDate;
	}



	public String getBookStatus() {
		return bookStatus;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authour == null) ? 0 : authour.hashCode());
		result = prime * result + ((bookStatus == null) ? 0 : bookStatus.hashCode());
		result = prime * result + ((borrowDate == null) ? 0 : borrowDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(borrowPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((publishDate == null) ? 0 : publishDate.hashCode());
		result = prime * result + ((returnDate == null) ? 0 : returnDate.hashCode());
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
		BookView other = (BookView) obj;
		if (authour == null) {
			if (other.authour != null)
				return false;
		} else if (!authour.equals(other.authour))
			return false;
		if (bookStatus == null) {
			if (other.bookStatus != null)
				return false;
		} else if (!bookStatus.equals(other.bookStatus))
			return false;
		if (borrowDate == null) {
			if (other.borrowDate != null)
				return false;
		} else if (!borrowDate.equals(other.borrowDate))
			return false;
		if (Double.doubleToLongBits(borrowPrice) != Double.doubleToLongBits(other.borrowPrice))
			return false;
		if (id != other.id)
			return false;
		if (publishDate == null) {
			if (other.publishDate != null)
				return false;
		} else if (!publishDate.equals(other.publishDate))
			return false;
		if (returnDate == null) {
			if (other.returnDate != null)
				return false;
		} else if (!returnDate.equals(other.returnDate))
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
	
	
	
	@Override
	public String toString() {
		return "[ Authour : " + authour + " Title : " + title + " Type : " + "MemberId : " + memberId + " ]"; 
	}


	
	
}

