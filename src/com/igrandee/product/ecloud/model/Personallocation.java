package com.igrandee.product.ecloud.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.igrandee.core.organization.model.Department;
import com.igrandee.core.organization.model.Role;


/**
 * The persistent class for the personallocation database table.
 * 
 */
@Repository
@Scope("prototype")
@Entity
@Table(name="personallocation")
public class Personallocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int personallocationid;

	@ManyToOne (optional=false ,cascade=CascadeType.ALL )
	@JoinColumn(name="departmentid", nullable=false)
	private Department department;

	@ManyToOne
	@JoinColumn(name="orgpersonid")
	private Orgperson orgperson;

	@Column(length=1)
	private char personallocationstatus;

	@ManyToOne (optional=false ,cascade=CascadeType.ALL ) 
	@JoinColumn(name="roleid", nullable=false)
	private Role role;
	
	public Personallocation() {
	}

	public int getPersonallocationid() {
		return personallocationid;
	}

	public void setPersonallocationid(int personallocationid) {
		this.personallocationid = personallocationid;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Orgperson getOrgperson() {
		return orgperson;
	}

	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

	public char getPersonallocationstatus() {
		return personallocationstatus;
	}

	public void setPersonallocationstatus(char personallocationstatus) {
		this.personallocationstatus = personallocationstatus;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}