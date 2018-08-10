/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import domain.Actor;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Services ----------------------------------------------------------------
	@Autowired
	private ActorService actorService;

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index(
			@RequestParam(required = false, defaultValue = "Anon") String name,
			final Locale locale) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		if (name == null || name.equals("") || name.equals("Anon"))
			if (locale.getLanguage().equals("es"))
				name = "usuario";
			else
				name = "user";

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");

		try {
			LoginService.getPrincipal();
			final UserAccount uA = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUA(uA);
			name = a.getName();

		} catch (final IllegalArgumentException i) {
		}

		result.addObject("name", name);
		result.addObject("moment", moment);

		return result;
	}

	public static ModelAndView indice(final String message, final Actor a) {
		ModelAndView result;
		String name = "user";
		SimpleDateFormat formatter;
		String moment;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");

		try {
			name = a.getName();

		} catch (final Throwable i) {
		}

		result.addObject("name", name);
		result.addObject("moment", moment);
		result.addObject("message", message);

		return result;
	}

}
