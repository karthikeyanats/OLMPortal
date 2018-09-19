package com.igrandee.product.ecloud.model.blog;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.igrandee.product.ecloud.model.Orgperson;

/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Repository
@Scope("prototype")
@Table(name = "category")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(length = 500)
	@NotBlank
	@Length(min = 1, message = "Description of character must be atleast {min} character length")
	private String description;

	@Column(length = 45)
	@NotBlank
	@Length(min = 1, message = "Name of category must be atleast {min} character length")
	private String name;

	@Column(length = 45)
	@NotBlank
	@Length(min = 1, message = "Status must be atleast {min} length and maximum {max} length")
	private String status;

	// bi-directional many-to-one association to Postcategory
	@OneToMany(mappedBy = "category")
	private Set<Postcategory> postcategories;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgpersonid", nullable = false)
	private Orgperson orgperson;

	public Category() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Postcategory> getPostcategories() {
		return this.postcategories;
	}

	public void setPostcategories(Set<Postcategory> postcategories) {
		this.postcategories = postcategories;
	}

	public Postcategory addPostcategory(Postcategory postcategory) {
		getPostcategories().add(postcategory);
		postcategory.setCategory(this);

		return postcategory;
	}

	public Postcategory removePostcategory(Postcategory postcategory) {
		getPostcategories().remove(postcategory);
		postcategory.setCategory(null);

		return postcategory;
	}

	/**
	 * @return the orgperson
	 */
	public Orgperson getOrgperson() {
		return orgperson;
	}

	/**
	 * @param orgperson
	 *            the orgperson to set
	 */
	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

}