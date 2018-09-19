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
 * The persistent class for the skills database table.
 * 
 */
@Entity
@Repository
@Scope("prototype")
@Table(name="skills")
public class Skill implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=500)
	@NotBlank
	private String description;

	@Column(length=500)
	private String mediapath;

	@Column(length=235)
	@NotBlank
	private String name;

	@Column(length=45)
	@NotBlank
	@Length(min=1, max=1, message="Length of status must be greateer than {min} and lesser than {min} ")
	private String status;
	
	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgpersonid", nullable = false)
	private Orgperson orgperson;

	//bi-directional many-to-one association to Postskill
	@OneToMany(mappedBy="skill")
	private Set<Postskill> postskills;

	public Skill() {
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

	public String getMediapath() {
		return this.mediapath;
	}

	public void setMediapath(String mediapath) {
		this.mediapath = mediapath;
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

	public Set<Postskill> getPostskills() {
		return this.postskills;
	}

	public void setPostskills(Set<Postskill> postskills) {
		this.postskills = postskills;
	}

	public Postskill addPostskill(Postskill postskill) {
		getPostskills().add(postskill);
		postskill.setSkill(this);

		return postskill;
	}

	public Postskill removePostskill(Postskill postskill) {
		getPostskills().remove(postskill);
		postskill.setSkill(null);

		return postskill;
	}

	/**
	 * @return the orgperson
	 */
	public Orgperson getOrgperson() {
		return orgperson;
	}

	/**
	 * @param orgperson the orgperson to set
	 */
	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}
	
	

}