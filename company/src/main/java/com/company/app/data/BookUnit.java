package com.company.app.data;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import com.company.app.App;
import com.company.app.data.BookUnit.BookStatus;
import com.company.users.Member;


public final class BookUnit extends Book {
	public enum BookStatus { AVAILABLE,RESERVED,TAKEN,LOST }
	
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	

	private int id;
	
	
	private final double borrowPrice;
	

	private Member member;

	private Date borrowDate;

	private Date returnDate;
	

	private BookStatus bookStatus;
	
	BookUnit() {
		id = 0;
		member = null;
		borrowPrice = 0;
		borrowDate = new Date();
		returnDate = new Date();
		bookStatus = null;
	}
	

	@SuppressWarnings("deprecation")
	public
	BookUnit(int memberId, String title, String type, String subject, 
			String authour,  Date publishedDate, Date borrowDate, double borrowPrice,
			BookStatus bookStatus) {
		
		super(title, type, subject, authour, publishedDate);
        this.id = id;
		this.borrowDate = new Date();
		this.returnDate = new Date();
		this.borrowPrice = borrowPrice;
		bookStatus = BookStatus.TAKEN;
	}
	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Member getMember() {
		return member;
	}


	public void setMember(Member member) {
		this.member = member;
	}


	public Date getBorrowDate() {
		return borrowDate;
	}


	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}


	public Date getReturnDate() {
		return returnDate;
	}


	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}


	public BookStatus getBookStatus() {
		return bookStatus;
	}


	public void setBookStatus(BookStatus bookStatus) {
		this.bookStatus = bookStatus;
	}


	public static String getDatePattern() {
		return DATE_PATTERN;
	}


	public double getBorrowPrice() {
		return borrowPrice;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bookStatus == null) ? 0 : bookStatus.hashCode());
		result = prime * result + ((borrowDate == null) ? 0 : borrowDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(borrowPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		result = prime * result + ((returnDate == null) ? 0 : returnDate.hashCode());
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
		BookUnit other = (BookUnit) obj;
		if (bookStatus != other.bookStatus)
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
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		if (returnDate == null) {
			if (other.returnDate != null)
				return false;
		} else if (!returnDate.equals(other.returnDate))
			return false;
		return true;
	}
	
	public String createView() {
		return "Book info" + "\n" + "Id: " + id + "\n" + "Authour: " + getAuthour() + "\n" + 
			    "Title: " + getTitle() + "\n" + "Subject: " + getSubject();
	}

	@Override
	public String toString() {
		return "[ Authour: " + getAuthour() + "] [Title: " + getTitle() + " ]";
	}
	
}
