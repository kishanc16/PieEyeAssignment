package com.pii.app.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.pii.app.model.ConnectionModel;
import com.pii.app.model.EmailModel;
import com.pii.app.service.ImapConnection;

@Controller
public class MailController {

	@Autowired
	ImapConnection imapConnection;
	private Logger LOGGER = LoggerFactory.getLogger(MailController.class);
	Map<String, Map<Long, EmailModel>> usersMail = new HashMap<>();

	/**
	 * 
	 * @param connectionModel get value from UI via post login
	 * @param request         handling http request
	 * @param model           adding object and view and return to view page
	 * @return view page 'list of mail'
	 * @throws MessagingException
	 */
	@GetMapping("/mail")
	public ModelAndView showAllMail(@ModelAttribute("connectionModel") ConnectionModel connectionModel,
			HttpServletRequest request, ModelAndView model) throws MessagingException {
		LOGGER.info("showAllMail method called... ");

		Map<Long, EmailModel> listOfMail = null;

		listOfMail = imapConnection.readAllMail(connectionModel);

		usersMail.put(connectionModel.getUsername(), listOfMail);

		model.addObject("listOfMail", listOfMail.entrySet());
		model.addObject("username", connectionModel.getUsername());
		model.setViewName("list-of-mail");

		return model;
	}

	/**
	 * 
	 * @param username current username
	 * @param mailId   mailId from list of mail
	 * @return specific mail from list of mail
	 */
	@GetMapping("mail/{username}/{mailId}")
	public ModelAndView showMail(@PathVariable("username") String username, @PathVariable("mailId") long mailId) {
		LOGGER.info("showMail method called...");

		Map<Long, EmailModel> map = usersMail.get(username);
		EmailModel emailModel = map.get(mailId);
		ModelAndView model = new ModelAndView();
		if (emailModel != null) {

			model.addObject("email", emailModel);
			model.setViewName("email-page");
		} else {
			model.addObject("message", "Data not exists");
			model.setViewName("error");
		}

		return model;
	}

	/**
	 * 
	 * @param model add default Connection model
	 * @return login page
	 */
	@GetMapping("/login")
	public ModelAndView showLogin(ModelAndView model) {
		LOGGER.info("showLogin method called...");
		ConnectionModel connectionModel = new ConnectionModel();
		model.addObject("connectionModel", connectionModel);
		model.setViewName("mail-login");
		return model;
	}

	/**
	 * 
	 * @param connectionModel get connection model value from UI
	 * @param request         handling http request
	 * @param redirectAttrs
	 * @return
	 * @throws MessagingException
	 */
	@PostMapping("/login")
	public RedirectView loginProcess(@ModelAttribute("connectionModel") ConnectionModel connectionModel,
			HttpServletRequest request, RedirectAttributes redirectAttrs) throws MessagingException {
		LOGGER.info("loginProcess called...");
		redirectAttrs.addFlashAttribute("connectionModel", connectionModel);

		return new RedirectView("/mail", true);
	}

}
