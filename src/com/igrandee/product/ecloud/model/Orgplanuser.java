package com.igrandee.product.ecloud.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the orgplanuser database table.
 * 
 */
@Entity
@Table(name="orgplanuser")
public class Orgplanuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	//bi-directional many-to-one association to Courseinvitation
	@ManyToOne
	@JoinColumn(name="courseinvitationid")
	private Courseinvitation courseinvitation;

	//bi-directional many-to-one association to Orgplansubscription
	@ManyToOne
	@JoinColumn(name="orgplansubscriptionid")
	private Orgplansubscription orgplansubscription;

	public Orgplanuser() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Courseinvitation getCourseinvitation() {
		return this.courseinvitation;
	}

	public void setCourseinvitation(Courseinvitation courseinvitation) {
		this.courseinvitation = courseinvitation;
	}

	public Orgplansubscription getOrgplansubscription() {
		return this.orgplansubscription;
	}

	public void setOrgplansubscription(Orgplansubscription orgplansubscription) {
		this.orgplansubscription = orgplansubscription;
	}

}