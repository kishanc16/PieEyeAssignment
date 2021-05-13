package com.pii.app.service;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.Store;

import com.pii.app.model.ConnectionModel;
import com.pii.app.model.EmailModel;

public interface ImapConnection {

	Map<Long, EmailModel> readAllMail(ConnectionModel connectionModel) throws MessagingException;

	Store setUpConnection(ConnectionModel connectionModel) throws MessagingException;
}
