package com.igrandee.product.ecloud.model.blog;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the postskill database table.
 * 
 */
@Entity
@Repository
@Scope("prototype")
@Table(name="postskill")
public class Postskill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=45)
	@NotBlank
	@Length(min=1, max=1, message="Length of status must be greateer than {min} and lesser than {min} ")
	private String status;

	//bi-directional many-to-one association to Post
	@ManyToOne
	@JoinColumn(name="post_id", nullable=false)
	@JsonBackReference
	private Post post;

	//bi-directional many-to-one association to Skill
	@ManyToOne
	@JoinColumn(name="skills_id", nullable=false)
	private Skill skill;

	public Postskill() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Post getPost() {
		return this.post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Skill getSkill() {
		return this.skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

}