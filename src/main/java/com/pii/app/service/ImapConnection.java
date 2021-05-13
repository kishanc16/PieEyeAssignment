package com.pii.app.service;

import java.util.Map;

import javax.mail.MessagingException;

import com.pii.app.model.ConnectionModel;
import com.pii.app.model.EmailModel;

public interface ImapConnection {

	Map<Long,EmailModel> readAllMail(String server, String port, String protocol, String username, String password) throws MessagingException;

}
