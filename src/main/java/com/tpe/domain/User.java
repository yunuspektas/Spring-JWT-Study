package com.tpe.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name="tbl_user")
@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=50,nullable=false)
	private String firstName;
	
	@Column(length=50,nullable=false)
	private String lastName;

	@Column(length=20,nullable = false,unique = true)
	private String userName;
	
	
	@Column(length=255,nullable = false)
	private String password;
	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="tbl_user_role",joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns =@JoinColumn(name="role_id"))
	private Set<Role> roles=new HashSet<>();

	
}
