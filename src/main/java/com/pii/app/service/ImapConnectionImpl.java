package com.pii.app.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pii.app.config.ImapConfig;
import com.pii.app.model.ConnectionModel;
import com.pii.app.model.EmailModel;

@Service
public class ImapConnectionImpl implements ImapConnection {

	@Autowired
	ImapConfig imapConfig;

//	String server = "outlook.office365.com";
//	int port = 993;
//	String encryptionMethod = "TLS";
//	String username = "pieeyecandidate@outlook.com";
//	String password= "2021-codder#";

//    @Value("${username}")
//    String username;
//    
//    @Value("${password}")
//    String password;
//    
//    @Value("${imap.server}")
//    String server;
//    @Value("${imap.port}")
//    String port;
	Logger LOGGER = LoggerFactory.getLogger(ImapConnectionImpl.class);
	
	@Override
	public Map<Long, EmailModel> readAllMail(String server, String port, String protocol, String username, String password) throws MessagingException {
		Folder inboxFolder;
		Store store;
		Map<Long, EmailModel> hmap = new HashMap<>();
		
		ConnectionModel connectionModel = new ConnectionModel();
		connectionModel.setServer(server);
		connectionModel.setPort(port);
		connectionModel.setProtocol(protocol);
		connectionModel.setUsername(username);
		connectionModel.setPassword(password);
		System.out.println("Read : " + connectionModel.toString());
		
		store = imapConfig.setUpConnection(connectionModel);

		if (store != null) {
			
			inboxFolder = store.getFolder("INBOX");
			LOGGER.info("Inbox folder accessed......");
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
				LOGGER.info("Message header: " + Arrays.toString(message.getFrom()));
				LOGGER.info("Received Date: " + receivedDate);
				LOGGER.info("From: " + from.toString());
				LOGGER.info("Subject: " + subject);
				//System.out.println("Body: " + body);
			}
			inboxFolder.close();
		}else {
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
				// System.out.println("Part: "+part.toString());
				message += part.getContent();
			}
		}
		return message;
	}
}
