package com.pii.app.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import org.springframework.web.bind.annotation.RequestParam;
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

	
	@GetMapping("/mail")
	public ModelAndView showAllMail(@ModelAttribute("connectionModel") ConnectionModel connectionModel,
			HttpServletRequest request, ModelAndView model) throws MessagingException {
		LOGGER.info("showAllMail method called.... ");
		
		Map<Long, EmailModel> listOfMail = null;
		
		String server = connectionModel.getServer();
		String port  = connectionModel.getPort();
		String protocol = connectionModel.getProtocol();
		String username	= connectionModel.getUsername();
		String password	= connectionModel.getPassword();
		 
		listOfMail = imapConnection.readAllMail(server,port,protocol,username,password);
		
		usersMail.put(connectionModel.getUsername(),listOfMail);
		
		model.addObject("listOfMail", listOfMail.entrySet()); 
		model.addObject("username", connectionModel.getUsername());
		model.setViewName("list-of-mail");
		return model;
	}

	@GetMapping("mail/{username}/{mailId}")
	public ModelAndView showMail(@PathVariable("username") String username,@PathVariable("mailId") long mailId) {
		LOGGER.info("showMail method called.....");
		
//		System.out.println("----------------------------------");
//		System.out.println(username);
//		System.out.println(usersMail);
		Map<Long,EmailModel> map = usersMail.get(username);
		EmailModel emailModel = map.get(mailId);		
		ModelAndView model = new ModelAndView();		
		 if (emailModel != null) {

			model.addObject("email", emailModel);
			model.setViewName("email-page");
		} else {
			model.addObject("message", "Data not exists.....");
			model.setViewName("error");
		}
		
		return model;
	}
//	@GetMapping("mail/details")
//	public ModelAndView showMail(@RequestParam("from") String from,@RequestParam("subject") String subject,
//			@RequestParam("body") String body) {
//		LOGGER.info("showMail method called.....");
//		//EmailModel email = listOfMail.get(Long.parseLong(mailId));
//		
//		EmailModel emailModel = new EmailModel();
//		emailModel.setFrom(from);
//		emailModel.setSubject(subject);
//		emailModel.setBody(body);
//		
//		System.out.println("EMail : "+ emailModel);
//		ModelAndView model = new ModelAndView();
//		if (emailModel != null) {
//			model.addObject("email", emailModel);
//			model.setViewName("email-page");
//		} else {
//			model.addObject("message", "Data not exists.....");
//			model.setViewName("error");
//		}
//		return model;
//	}

	@GetMapping("/login")
	public ModelAndView showLogin(ModelAndView model) {
		LOGGER.info("showLogin method called.....");
		ConnectionModel connectionModel = new ConnectionModel();
		model.addObject("connectionModel", connectionModel);
		model.setViewName("mail-login");
		return model;
	}

	@PostMapping("/login")
	public RedirectView loginProcess(@ModelAttribute("connectionModel") ConnectionModel connectionModel,
			HttpServletRequest request,RedirectAttributes redirectAttrs) throws MessagingException {
		LOGGER.info("loginProcess called....");
		redirectAttrs.addFlashAttribute("connectionModel", connectionModel);
		
		return new RedirectView("/mail", true);
	}

}
