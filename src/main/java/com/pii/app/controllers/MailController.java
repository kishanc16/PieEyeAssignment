package com.pii.app.controllers;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pii.app.config.ImapConfig;
import com.pii.app.model.ConnectionModel;
import com.pii.app.model.EmailModel;
import com.pii.app.service.ImapConnection;

@Controller
public class MailController {
	
	@Autowired
	ImapConnection imapConnection;
	@Autowired
	ImapConfig imapConfig;
	Map<Long,EmailModel> listOfMail = null;
//	@RequestMapping("/home/")
//	public String home() {
//		System.out.println("mail controller----");
//		//imapConnection.read();
//		return "home";
//	}
	
	//@ResponseBody
	@GetMapping("/mail")
	public String showAllMail(@ModelAttribute("connectionModel")ConnectionModel connectionModel, Model m) throws MessagingException {
		System.out.println("Get Mail called .... ");
		listOfMail = imapConnection.readAllMail(connectionModel);
		m.addAttribute("listOfMail", listOfMail.entrySet());
		return "list-of-mail";
	}
	@GetMapping("/mail/{mailId}")
	public String showMail(@PathVariable("mailId") String mailId, Model m) {
		EmailModel email=  listOfMail.get(Long.parseLong(mailId));
		m.addAttribute("email", email);
		return "email-page";
	}
	@GetMapping("/login")
	public String showLogin(Model m) {
		System.out.println("My login called....");
		ConnectionModel connectionModel= new ConnectionModel();
		m.addAttribute("connectionModel", connectionModel);
		return "mail-login";
	}
	
//	@PostMapping("/loginProcess")
//	public ModelAndView loginProcess(@ModelAttribute("connectionModel")ConnectionModel connectionModel) {
//		System.out.println("My loginProcess called....");
//		ModelAndView model = null;
//		Store store = imapConfig.setUpConnection(connectionModel);
//		if(store != null) {
//			System.out.println("Login Successfully");
//			model = new ModelAndView("mail");
//			
//		}else {
//			model = new ModelAndView("login");
//			model.addObject("connectionModel", connectionModel);
//			model.addObject("message", "Invalid credentials!!");
//		}
//		return model;
//	}
	
}
