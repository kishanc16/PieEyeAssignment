package com.pii.app.config;

import javax.mail.MessagingException;
import javax.mail.Store;

import com.pii.app.model.ConnectionModel;

public interface ImapConfig {

	Store setUpConnection(ConnectionModel connectionModel) throws MessagingException;
}
