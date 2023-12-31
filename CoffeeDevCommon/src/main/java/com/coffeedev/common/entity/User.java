package com.coffeedev.common.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer id;
	@Column(length=128, nullable=false)
	public String name;
	@Column(length=128, nullable=false, unique=true)
	public String email;
	@Column(length=64, nullable=false)
	public String password;
	@Column(length=64)
	public String photo;
	public boolean enabled;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)
	private Set<Role> roles = new HashSet();
	
	
	public User() {
		
	}
	
	public User(String email, String password, String name) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;

	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
	@Override
	public String toString() {
		return "User [Id=" + id 
				+ ", Email=" + email 
				+ ", Name= "+name
				+ " , Roles=" + roles
				+ "]";
	}
	
	@Transient
	public String getPhotosImagePath() {
		if (id == null || photo == null) return "/images/default-user.png";

		return "/user-photos/" + this.id + "/" + this.photo;
	}
	
	@Transient
	public String getFullName() {
		return name+" "+id;
	}
	
	
}
