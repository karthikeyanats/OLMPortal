package com.igrandee.product.ecloud.model.blog;

import java.io.Serializable;
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

import java.util.Set;

/**
 * The persistent class for the tags database table.
 * 
 */
@Entity
@Repository
@Scope("prototype")
@Table(name = "tags")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(length = 500)
	@NotBlank
	private String description;

	@Column(nullable = false, length = 235)
	@NotBlank
	private String name;

	@Column(nullable = false, length = 235)
	@NotBlank
	@Length(min = 1, max = 1, message = "Length of status must be greateer than {min} and lesser than {min} | 422")
	private String status;

	// bi-directional many-to-one association to Posttag
	@OneToMany(mappedBy = "tag")
	private Set<Posttag> posttags;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgpersonid", nullable = false)
	private Orgperson orgperson;

	public Tag() {
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

	public Set<Posttag> getPosttags() {
		return this.posttags;
	}

	public void setPosttags(Set<Posttag> posttags) {
		this.posttags = posttags;
	}

	public Posttag addPosttag(Posttag posttag) {
		getPosttags().add(posttag);
		posttag.setTag(this);

		return posttag;
	}

	public Posttag removePosttag(Posttag posttag) {
		getPosttags().remove(posttag);
		posttag.setTag(null);

		return posttag;
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