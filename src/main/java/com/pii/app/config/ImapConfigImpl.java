package com.pii.app.config;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.pii.app.model.ConnectionModel;

@Configuration
public class ImapConfigImpl implements ImapConfig{
	private Logger LOGGER = LoggerFactory.getLogger(ImapConfigImpl.class);
	@Override
	public Store setUpConnection(ConnectionModel connectionModel) {
		Properties properties = new Properties();
		Store store = null;
		
		properties.put("mail.imap.host", connectionModel.getServer());
	  	properties.put("mail.imap.port", connectionModel.getPort());
	  	properties.put("mail.store.protocol",connectionModel.getProtocol());
	  	//Session emailSession = Session.getDefaultInstance(properties);
	  	Session emailSession = Session.getDefaultInstance(properties,
	  		   new javax.mail.Authenticator() {
	  		    protected PasswordAuthentication getPasswordAuthentication() {
	  		     return new PasswordAuthentication(connectionModel.getUsername(),connectionModel.getPassword());
	  		    }
	  		   }); 
	  	LOGGER.info("Session created........");
	  	
	  	try {
			store = emailSession.getStore(connectionModel.getProtocol());
			store.connect(connectionModel.getServer(), connectionModel.getUsername(), connectionModel.getPassword());
			LOGGER.info("Connection established.......");
		} catch (MessagingException e) {
			LOGGER.info("Opps!!!! Something went wrong");
			e.printStackTrace();
		}
	  	return store;
	}

}
