package com.pii.app.controllers;

import java.util.Map;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.pii.app.model.ConnectionModel;
import com.pii.app.model.EmailModel;
import com.pii.app.service.ImapConnection;

@Controller
public class MailController {

	@Autowired
	ImapConnection imapConnection;
	private Map<Long, EmailModel> listOfMail = null;
	private Logger LOGGER = LoggerFactory.getLogger(MailController.class);

	@GetMapping("/mail")
	public ModelAndView showAllMail(@ModelAttribute("connectionModel") ConnectionModel connectionModel,
			ModelAndView model) throws MessagingException {
		LOGGER.info("showAllMail method called.... ");

		System.out.println(connectionModel.toString());
		listOfMail = imapConnection.readAllMail(connectionModel);
		model.addObject("listOfMail", listOfMail.entrySet());
		model.setViewName("list-of-mail");
		return model;
	}

	@GetMapping("/mail/{mailId}")
	public ModelAndView showMail(@PathVariable("mailId") String mailId) {
		LOGGER.info("showMail method called.....");
		EmailModel email = listOfMail.get(Long.parseLong(mailId));
		ModelAndView model = new ModelAndView();
		;
		if (email != null) {
			model.addObject("email", email);
			model.setViewName("email-page");
		} else {
			model.addObject("message", "Data not exists.....");
			model.setViewName("error");
		}
		return model;
	}

	@GetMapping("/login")
	public ModelAndView showLogin(ModelAndView model) {
		LOGGER.info("showLogin method called.....");
		ConnectionModel connectionModel = new ConnectionModel();
		model.addObject("connectionModel", connectionModel);
		model.setViewName("mail-login");
		return model;
	}

//	@PostMapping("/loginProcess")
//	public RedirectView loginProcess(@ModelAttribute("connectionModel") ConnectionModel connectionModel) throws MessagingException {
//		System.out.println("My loginProcess called....");
//		this.connection = connectionModel;
//		// ModelAndView model = new ModelAndView();
//		// listOfMail = imapConnection.readAllMail(connectionModel);
//		// m.addAttribute("connectionModel", connectionModel);
//		// model.addObject("connectionModel", connectionModel);
//
//		return new RedirectView("/mail", true);
//	}

}
