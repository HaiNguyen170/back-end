package com.example.demo.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author khoi.tranvan
 *
 */
@Entity
@Table(name = "article")
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank
	@Size(max=100)
	private String title;
	
	@NotBlank
	@Temporal(TemporalType.DATE)
    private Date created_date;
	
	@NotBlank
	@Temporal(TemporalType.DATE)
    private Date expired_date;
	
	@NotBlank 
	private boolean active;
	/*
	//One article can have many files
	 @OneToMany(mappedBy = "article", fetch = FetchType.LAZY,
	            cascade = CascadeType.ALL)
	    private Set<File> file;
	
	//Many article can be created one users
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	//Many article can be created in one falcuty
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "falcuty_id", nullable = false)
	private Falcuty falcuty;
*/
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "falcuty_id")
	private Falcuty falcuty;
	
	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<File> file;
	
	public Article(String title,Date created_date,  Date expired_date,boolean active) {
		super();
		this.title = title;
		this.created_date = created_date;
		this.expired_date = expired_date;
		this.active = active;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getExpired_date() {
		return expired_date;
	}

	public void setExpired_date(Date expired_date) {
		this.expired_date = expired_date;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Falcuty getFalcuty() {
		return falcuty;
	}

	public void setFalcuty(Falcuty falcuty) {
		this.falcuty = falcuty;
	}
}


//package com.example.demo.models;
//
//import java.util.Date;
//import java.util.Set;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;
//
///**
// * @author khoi.tranvan
// *
// */
//@Entity
//@Table(name = "article")
//public class Article {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private long id;
//	
//	@NotBlank
//	@Size(max=100)
//	private String title;
//	
//	@NotBlank
//	@Temporal(TemporalType.DATE)
//    private Date created_date;
//	
//	@NotBlank
//	@Temporal(TemporalType.DATE)
//    private Date expired_date;
//	
//	@NotBlank 
//	private boolean active;
//	/*
//	//One article can have many files
//	 @OneToMany(mappedBy = "article", fetch = FetchType.LAZY,
//	            cascade = CascadeType.ALL)
//	    private Set<File> file;
//	
//	//Many article can be created one users
//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "user_id", nullable = false)
//	private User user;
//	
//	//Many article can be created in one falcuty
//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "falcuty_id", nullable = false)
//	private Falcuty falcuty;
//*/
//	public Article(String title,Date created_date,  Date expired_date,boolean active) {
//		super();
//		this.title = title;
//		this.created_date = created_date;
//		this.expired_date = expired_date;
//		this.active = active;
//	}
//
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public Date getCreated_date() {
//		return created_date;
//	}
//
//	public void setCreated_date(Date created_date) {
//		this.created_date = created_date;
//	}
//
//	public Date getExpired_date() {
//		return expired_date;
//	}
//
//	public void setExpired_date(Date expired_date) {
//		this.expired_date = expired_date;
//	}
//
//	public boolean isActive() {
//		return active;
//	}
//
//	public void setActive(boolean active) {
//		this.active = active;
//	}
//
//}