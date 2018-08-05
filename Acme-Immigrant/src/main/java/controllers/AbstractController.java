/*
 * AbstractController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Locale;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationSystemService;
import domain.ConfigurationSystem;

@Controller
public class AbstractController {

	@Autowired
	ConfigurationSystemService	configurationSystemService;


	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

	@ModelAttribute
	public void showBanner(final Model model, final Locale locale) {
		final ConfigurationSystem cs = this.configurationSystemService.get();
		model.addAttribute("banner", cs.getBanner());
		model.addAttribute("systemName", cs.getSystemName());
		model.addAttribute("idioma",locale.getLanguage());

		if (locale.getLanguage().equals("es")) {
			model.addAttribute("confirmTel", "El teléfono no sigue los patrones recomendados, ¿quiere continuar?");
			model.addAttribute("welcomeMessage", cs.getWelcomeMessageES());
		} else {
			model.addAttribute("confirmTel", "The phone number does not verify any of the recommended patterns, are you sure you want save it?");
			model.addAttribute("welcomeMessage", cs.getWelcomeMessageEN());
		}
	}
}
