package com.pii.app.model;

import java.util.Date;

public class EmailModel {
	
	private long mailId;
	private String from;
	private String subject;
	private Date receivedDate;
	private String body;
	
	public long getMailId() {
		return mailId;
	}
	public String getFrom() {
		return from;
	}
	public String getSubject() {
		return subject;
	}
	public Date getReceivedDate() {
		return receivedDate;
	}
	public String getBody() {
		return body;
	}
	public void setMailId(long mailId) {
		this.mailId = mailId;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	public void setBody(String body) {
		this.body = body;
	}
		
	
}
