package com.example.demo.vo;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails{
	/*
	 * user_id varchar2(30) primary key, name varchar2(10) not null, password
	 * varchar2(50) not null, email varchar2(50) not null, deleted char(1) default
	 * 'N', admin char(1) default 'N', reg_date date default sysdate
	 */
	private String id;
	private String name;
	private String password;
	private String phone;
	private String email;
	private int point;
	private String deleted;
	private String admin;
	private Date regDate;
	Collection<GrantedAuthority> authorities;
	
	
	public User() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	//해당 계정의 만료여부를 반환하는 메소드
	//반환 값이 true 면 만료되지 않았음
	public boolean isAccountNonExpired() {
		return true;
	}

	//해당 계정이 잠겼는지 여부를 반환하는 메소드
	//반환 값이 true 면 해당 계정은 잠겨 있지 않음
	public boolean isAccountNonLocked() {
		return true;
	}
	// 해당 계정의 비밀번호 사용기한이 만료되었는지 여부를 반환하는 메소드
	// 반환값이 true면 비밀번호 사용기한이 만료되지 않았음
	public boolean isCredentialsNonExpired() {
		return true;
	}
	// 해당 계정이 사용가능한지 여부를 반환하는 메소드
	// 반환값이 true면 해당 계정은 사용가능함	
	public boolean isEnabled() {
		return true;
	}
	public String getUsername() {
		return id;
	}
	public boolean hasRoleAdmin() {
		
		return authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
	}
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", phone=" + phone + ", email=" + email
				+ ", point=" + point + ", deleted=" + deleted + ", admin=" + admin + ", regDate=" + regDate + "]";
	}

	
}


