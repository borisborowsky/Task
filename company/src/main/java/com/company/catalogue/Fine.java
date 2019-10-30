package com.company.catalogue;

import java.util.Date;
import java.util.Objects;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="fine_table")
public class Fine {
	public static final double DEFAULT_FINE_SUM = 0x2.0p1F;
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private final double fineAmount;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fine_id")
	private int id;
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private Date fineDate;
	
	
	@ContainedIn
	@ManyToOne
	@JsonbTransient
	@JoinColumn(name = "member_id")
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@FieldBridge(impl = IntegerBridge.class)
	@IndexedEmbedded	
	private Member member;
	
	
	Fine() {
		this.fineDate = new Date();
		this.id = 0;
		this.fineAmount = 0;
		this.member = null;
	}
	

	public Fine(Date fineDate, Member member, double fineAmount) {
		this.fineDate = new Date();
		this.fineDate = fineDate;
		this.member = Objects.requireNonNull(member);
		this.fineAmount = fineAmount;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Date getFineDate() {
		return fineDate;
	}


	public void setFineDate(Date fineDate) {
		this.fineDate = fineDate;
	}


	public static double getDefaultFineSum() {
		return DEFAULT_FINE_SUM;
	}


	public double getFineAmount() {
		return fineAmount;
	}


	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fineAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((fineDate == null) ? 0 : fineDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((member == null) ? 0 : member.hashCode());
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
		Fine other = (Fine) obj;
		if (Double.doubleToLongBits(fineAmount) != Double.doubleToLongBits(other.fineAmount))
			return false;
		if (fineDate == null) {
			if (other.fineDate != null)
				return false;
		} else if (!fineDate.equals(other.fineDate))
			return false;
		if (id != other.id)
			return false;
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		return true;
	}
}
