package com.pointwest.workforce.planner.security;

public class TokenUser {
	
	public TokenUser() {
		super();
	}
	
	public TokenUser(String username, String role) {
		this.username = username;
		this.role = role;
	}
	
	private String username;
	
	private String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
