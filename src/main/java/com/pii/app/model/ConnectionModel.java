package com.pii.app.model;

public class ConnectionModel {
	
	private String server ="imap.gmail.com";
	private String port ="993";
	private String protocol="imaps";
	private String username;
	private String password;
	
	public String getServer() {
		return server;
	}
	public String getPort() {
		return port;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	@Override
	public String toString() {
		return "ConnectionModel [server=" + server + ", port=" + port + ", protocol=" + protocol + ", username="
				+ username + ", password=" + password + "]";
	}
	
	
	
}
