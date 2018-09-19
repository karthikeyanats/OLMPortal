package com.igrandee.product.ecloud.model.royalty;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * The persistent class for the royaltytotalamount database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="royaltytotalamount")
public class Royaltytotalamount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	private int authorid;

	private float pendingamount;

	@Column(length=1)
	private char royaltytotalamountstatus;

	private float totalamount;

	public Royaltytotalamount() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAuthorid() {
		return this.authorid;
	}

	public void setAuthorid(int authorid) {
		this.authorid = authorid;
	}

	public float getPendingamount() {
		return this.pendingamount;
	}

	public void setPendingamount(float pendingamount) {
		this.pendingamount = pendingamount;
	}


	public char getRoyaltytotalamountstatus() {
		return royaltytotalamountstatus;
	}

	public void setRoyaltytotalamountstatus(char royaltytotalamountstatus) {
		this.royaltytotalamountstatus = royaltytotalamountstatus;
	}

	public float getTotalamount() {
		return this.totalamount;
	}

	public void setTotalamount(float totalamount) {
		this.totalamount = totalamount;
	}

}