package com.pii.app.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pii.app.model.ConnectionModel;
import com.pii.app.model.EmailModel;

@Service
public class ImapConnectionImpl implements ImapConnection {

	Logger LOGGER = LoggerFactory.getLogger(ImapConnectionImpl.class);

	/**
	 * create connection using username, password, server, port and protocol return
	 * store object
	 */
	@Override
	public Store setUpConnection(ConnectionModel connectionModel) throws MessagingException {
		Properties properties = new Properties();
		Store store = null;

		properties.put("mail.imap.host", connectionModel.getServer());
		properties.put("mail.imap.port", connectionModel.getPort());
		properties.put("mail.store.protocol", connectionModel.getProtocol());
		// Session emailSession = Session.getDefaultInstance(properties);

		Session emailSession = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(connectionModel.getUsername(), connectionModel.getPassword());
			}
		});
		LOGGER.info("Session created...");

		store = emailSession.getStore(connectionModel.getProtocol());
		store.connect(connectionModel.getServer(), connectionModel.getUsername(), connectionModel.getPassword());
		LOGGER.info("Connection established...");

		return store;
	}

	/**
	 * fetch all mail from Inbox return list of mail to controller
	 */
	@Override
	public Map<Long, EmailModel> readAllMail(ConnectionModel connectionModel) throws MessagingException {
		Folder inboxFolder = null;
		Store store = null;

		Map<Long, EmailModel> hmap = new HashMap<>();

		store = setUpConnection(connectionModel);

		if (store != null) {

			inboxFolder = store.getFolder("INBOX");
			LOGGER.info("Inbox folder accessed...");
			inboxFolder.open(Folder.READ_ONLY);

			LOGGER.info("No of Messages : " + inboxFolder.getMessageCount());
			Message[] messages = inboxFolder.getMessages();
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				EmailModel emailModel = new EmailModel();
				LOGGER.info("--------------------------------");
				long messageId = i + 1;
				Address from = message.getFrom()[0];
				String subject = message.getSubject();
				Date receivedDate = message.getReceivedDate();
				String body = "";
				try {
					body = getMessageBody(message.getContent());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				emailModel.setMailId(messageId);
				emailModel.setFrom(from.toString());
				emailModel.setSubject(subject);
				emailModel.setReceivedDate(receivedDate);
				emailModel.setBody(body);
				hmap.put(messageId, emailModel);

				LOGGER.info("MESSAGE #" + messageId);
				// LOGGER.info("Received Date: " + receivedDate);
				LOGGER.info("From: " + from.toString());
				// LOGGER.info("Subject: " + subject);
				// System.out.println("Body: " + body);
			}
			inboxFolder.close();
		} else {
			LOGGER.info("Oops!!! Something went wrong");
		}
		return hmap;
	}

	private String getMessageBody(Object content) throws IOException, MessagingException {
		String message = "";
		if (content instanceof Multipart) {
			Multipart multipart = (Multipart) content;
			int parts = multipart.getCount();
			for (int i = 1; i < parts; i++) {
				MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(i);
				message += part.getContent();
			}
		}
		return message;
	}
}
