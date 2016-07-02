package com.whck.domain.base;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name="tb_role")
public class Role {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="tb_user_role",
				joinColumns=@JoinColumn(name="rid"),
				inverseJoinColumns=@JoinColumn(name="uid"))
	private List<User> users;
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
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
}
