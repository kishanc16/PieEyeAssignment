package com.pii.app.exception;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Controller
public class ExceptionHandlerController {
	private Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

	/**
	 * Handling IOException, Null exception and other all exception
	 * 
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest req, Exception ex) {
		LOGGER.error("Request: " + req.getRequestURL() + "  raised by " + ex);
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "Please contact administrator for more details...");
		mv.setViewName("error");
		return mv;
	}

	/**
	 * Handling Authenication exception and exception raised by Javax.mail
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ AuthenticationFailedException.class, MessagingException.class })
	public ModelAndView ImapConnectionException(Exception ex) {
		LOGGER.error("Exception: " + ex.getLocalizedMessage());
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", "Authenication failed!! Please check credentials....");
		mv.setViewName("error");
		return mv;
	}
}
