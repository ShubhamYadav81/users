package com.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter

public class User {
	
	@Id
	@Column(name = "USER_NAME", length = 32)
	private String username;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "NAME")
	private String name;

}
