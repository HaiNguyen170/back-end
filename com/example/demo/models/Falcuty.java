//package com.example.demo.models;
//
//import java.util.Set;
//
//import javax.persistence.*;
//
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//@Entity
//@Table(name = "falcuty")
//public class Falcuty {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//
//	@Enumerated(EnumType.STRING)
//	@Column(length = 20)
//	private EFalcuty name;
//
//	@OneToMany(mappedBy = "falcuty", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JsonIgnore
//	private Set<User> user;
//	
//	@OneToMany(mappedBy = "falcuty", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JsonIgnore
//	private Set<Article> article;
//	
////	@OneToOne(mappedBy = "falcuty", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
////	private Set<Article> article ;
//
//	public Falcuty(EFalcuty name) {
//		super();
//		this.name = name;
//	}
//
//	// Getters and setters omitted for brevity
//
//	public Falcuty() {
//	}
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public EFalcuty getName() {
//		return name;
//	}
//
//	public void setName(EFalcuty name) {
//		this.name = name;
//	}
//
//	public Set<User> getUser() {
//		return user;
//	}
//
//	public void setUser(Set<User> user) {
//		this.user = user;
//	}
//
//	public Set<Article> getArticle() {
//		return article;
//	}
//
//	public void setArticle(Set<Article> article) {
//		this.article = article;
//	}
//
//	
//	
//	
//	/*
//	 *	INSERT INTO falcuty(name) VALUE ("FALCUTY_SE");
//		INSERT INTO falcuty(name) VALUE ("FALCUTY_IB");
//		INSERT INTO falcuty(name) VALUE ("FALCUTY_AI");
//		INSERT INTO falcuty(name) VALUE ("FALCUTY_SA");
//*/
//
//}



package com.example.demo.models;

import java.util.Set;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "falcuty")
public class Falcuty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private EFalcuty name;

	@OneToMany(mappedBy = "falcuty", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<User> user;
	
	//@OneToMany(mappedBy = "falcuty", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private Set<Article> article ;

	public Falcuty(EFalcuty name) {
		super();
		this.name = name;
	}

	// Getters and setters omitted for brevity

	public Falcuty() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EFalcuty getName() {
		return name;
	}

	public void setName(EFalcuty name) {
		this.name = name;
	}
	/*
	 *	INSERT INTO falcuty(name) VALUE ("FALCUTY_SE");
		INSERT INTO falcuty(name) VALUE ("FALCUTY_IB");
		INSERT INTO falcuty(name) VALUE ("FALCUTY_AI");
		INSERT INTO falcuty(name) VALUE ("FALCUTY_SA");
*/

}
