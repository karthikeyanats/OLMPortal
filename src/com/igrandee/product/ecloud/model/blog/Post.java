package com.igrandee.product.ecloud.model.blog;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.igrandee.product.ecloud.model.Orgperson;

/**
 * The persistent class for the post database table.
 * 
 */
@Entity
@Repository
@Scope("prototype")
@Table(name = "post")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(nullable = false, length = 235)
	private String bannerpath;

	@Column(nullable = false)
	@NotBlank
	@Length(max=5, min=1, message="Length must be atleast {min} and maximum {max}| 422")
	private String commentable;
	
	@Column(nullable = false, length = 25505)
	@NotBlank
	@Length(min=1)
	private String content;

	@Column(nullable = false)  
	private Timestamp date;

	@Column(length = 235)
	private String media;

	@Column(nullable = false, length = 45)
	@NotBlank
	@Length(min=1, max=1, message="Length of status must be greater than {min} and lesser than {max}|422")
	private String status;

	@Column(nullable = false, length = 235)
	@NotBlank 
	@Length(min = 1,message="Length of status must be greateer than {min} |422 ")
	private String title;

	// bi-directional many-to-one association to Comment
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
	private Set<Comment> comments;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgpersonid", nullable = false)
	private Orgperson orgperson;

	// bi-directional many-to-one association to Postcategory
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Postcategory> postcategories;

	// bi-directional many-to-one association to Postskill
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Postskill> postskills;

	// bi-directional many-to-one association to Posttag
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Posttag> posttags;

	public Post() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBannerpath() {
		return this.bannerpath;
	}

	public void setBannerpath(String bannerpath) {
		this.bannerpath = bannerpath;
	}

	public String getCommentable() {
		return this.commentable;
	}

	public void setCommentable(String commentable) {
		this.commentable = commentable;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getMedia() {
		return this.media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Comment> getComments() {
		return this.comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setPost(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setPost(null);

		return comment;
	}

	public Set<Postcategory> getPostcategories() {
		return this.postcategories;
	}

	public void setPostcategories(Set<Postcategory> postcategories) {
		this.postcategories = postcategories;
	}

	public Postcategory addPostcategory(Postcategory postcategory) {
		getPostcategories().add(postcategory);
		postcategory.setPost(this);

		return postcategory;
	}

	public Postcategory removePostcategory(Postcategory postcategory) {
		getPostcategories().remove(postcategory);
		postcategory.setPost(null);

		return postcategory;
	}

	public Set<Postskill> getPostskills() {
		return this.postskills;
	}

	public void setPostskills(Set<Postskill> postskills) {
		this.postskills = postskills;
	}

	public Postskill addPostskill(Postskill postskill) {
		getPostskills().add(postskill);
		postskill.setPost(this);

		return postskill;
	}

	public Postskill removePostskill(Postskill postskill) {
		getPostskills().remove(postskill);
		postskill.setPost(null);

		return postskill;
	}

	public Set<Posttag> getPosttags() {
		return this.posttags;
	}

	public void setPosttags(Set<Posttag> posttags) {
		this.posttags = posttags;
	}

	public Posttag addPosttag(Posttag posttag) {
		getPosttags().add(posttag);
		posttag.setPost(this);

		return posttag;
	}

	public Posttag removePosttag(Posttag posttag) {
		getPosttags().remove(posttag);
		posttag.setPost(null);

		return posttag;
	}
 
	public Orgperson getOrgperson() {
		return orgperson;
	}
 
	public void setOrgperson(Orgperson orgperson) {
		this.orgperson = orgperson;
	}

}