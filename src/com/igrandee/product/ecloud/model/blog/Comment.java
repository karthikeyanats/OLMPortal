
package com.igrandee.product.ecloud.model.blog;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import com.igrandee.product.ecloud.model.Orgperson;

/**
 * The persistent class for the comments database table.
 * 
 */
@Entity
@Table(name = "comments")
@Repository
@Scope("prototype")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(length = 25505)
	@NotBlank
	@Length(min = 1, message = "Comments must be atleast {min} length | 422")
	private String commentText;

	private Timestamp date;

	@Column(length = 45)
	@NotBlank
	@Length(min = 1, message = "Status must be atleast {min} length and maximum {max} length | 422")
	private String status;

	// bi-directional many-to-one association to Post
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgpersonid", nullable = false)
	private Orgperson orgperson;

	public Comment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCommentText() {
		return this.commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
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