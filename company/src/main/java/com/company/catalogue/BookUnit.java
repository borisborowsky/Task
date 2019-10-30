package com.company.catalogue;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.bridge.builtin.IntegerBridge;

import com.company.users.Member;

@Entity
@Indexed
@Table(name="book_tbl")
public final class BookUnit extends Book {
	public enum BookStatus { AVAILABLE,RESERVED,TAKEN,LOST }
	
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private final double borrowPrice;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
	private int id;
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private boolean isThereFineOnTheBook;
	
	@ContainedIn
	@ManyToOne
	@JsonbTransient
	@JoinColumn(name = "member_id")
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@FieldBridge(impl = IntegerBridge.class)
	@IndexedEmbedded	
	private Member member;
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private Date borrowDate;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private Date returnDate;
	
	@Enumerated(EnumType.STRING)
	private BookStatus bookStatus;
	
	BookUnit() {
		id = 0;
		member = null;
		borrowPrice = 0;
		borrowDate = new Date();
		returnDate = new Date();
		bookStatus = null;
		isThereFineOnTheBook = false;
	}

	
	@SuppressWarnings("deprecation")
	BookUnit(int memberId, int id, String title, String type, String subject, 
			String authour,  Date publishedDate, Member member, LocalDate borrowDate,
			double borrowPrice) {
		
		super(title, type, subject, authour, publishedDate);
        this.id = id;
		this.member = Objects.requireNonNull(member);
		this.borrowDate = new Date();
		this.returnDate = new Date();
		this.borrowPrice = borrowPrice;
		bookStatus = BookStatus.TAKEN;
		this.isThereFineOnTheBook = false;
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

	
	
	public boolean isThereFineOnTheBook() {
		return isThereFineOnTheBook;
	}


	public void setThereFineOnTheBook(boolean isThereFineOnTheBook) {
		this.isThereFineOnTheBook = isThereFineOnTheBook;
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
		result = prime * result + (isThereFineOnTheBook ? 1231 : 1237);
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
		if (isThereFineOnTheBook != other.isThereFineOnTheBook)
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
}
