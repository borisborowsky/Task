package com.company.catalogue;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.company.users.Customer;
import com.company.users.Member;

@Entity
@Table(name="fine_table")
public class Fine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fine_id")
	private int id;
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private final double fineAmount;
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private Date fineDate;

	@ContainedIn
	@ManyToOne
	@JoinColumn(name = "member_id")
	private final Member member;
	
	
	Fine() {
		this.fineDate = new Date();
		this.id = 0;
		this.fineAmount = 0;
		this.member = null;
	}
	

	Fine(Date fineDate, Member member, double fineAmount) {
		this.fineDate = new Date();
		this.fineDate = fineDate;
		this.member = Objects.requireNonNull(member);
		this.fineAmount = fineAmount;
	}
}
