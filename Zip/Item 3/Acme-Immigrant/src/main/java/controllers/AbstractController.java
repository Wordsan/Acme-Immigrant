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

import security.LoginService;
import services.ActorService;
import services.ConfigurationSystemService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.ConfigurationSystem;

@Controller
public class AbstractController {

	@Autowired
	ConfigurationSystemService configurationSystemService;

	@Autowired
	ActorService actorService;

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

	/*
	 * Cuando se realiza una busqueda en la base de datos con un id de un objeto
	 * que no existe en la tabla, se lanza la excepcion ObjectNotFoundException,
	 * la cual se captura aqui y redirige a la página principal indicando al
	 * usuario que ese objeto no existe
	 */
	@ExceptionHandler(ObjectNotFoundException.class)
	public ModelAndView notFound(final ObjectNotFoundException oops,
			final Locale locale) {
		return WelcomeController.indice("object.not.found",
				this.actorService.getActorByUA(LoginService.getPrincipal()),
				this.configurationSystemService.get(), locale);
	}

	/*
	 * Cuando se realiza una operacion que incumple alguna regla de negocio o se
	 * intenta modificar un objeto sobre el cual no tiene permisos se lanza la
	 * excepcion ForbbidenActionException, la cual se captura y redirige a la
	 * página principal e indicando al usuario que no puede realizar la acción,
	 * similar al error HTTP 403
	 */
	@ExceptionHandler(ForbbidenActionException.class)
	public ModelAndView forbbiden(final ForbbidenActionException oops,
			final Locale locale) {
		return WelcomeController.indice("forbbiden.access.error",
				this.actorService.getActorByUA(LoginService.getPrincipal()),
				this.configurationSystemService.get(), locale);
	}

	/*
	 * Este metodo se utiliza para disponer del banner, el nombre del sistema y
	 * algunos mensajes disponibles en todas las páginas
	 */
	@ModelAttribute
	public void showBanner(final Model model, final Locale locale) {
		final ConfigurationSystem cs = this.configurationSystemService.get();
		model.addAttribute("banner", cs.getBanner());
		model.addAttribute("systemName", cs.getSystemName());
		model.addAttribute("idioma", locale.getLanguage());

		if (locale.getLanguage().equals("es")) {
			model.addAttribute("confirmTel",
					"El teléfono no sigue los patrones recomendados, ¿quiere continuar?");
			model.addAttribute("welcomeMessage", cs.getWelcomeMessageES());
		} else {
			model.addAttribute(
					"confirmTel",
					"The phone number does not verify any of the recommended patterns, are you sure you want save it?");
			model.addAttribute("welcomeMessage", cs.getWelcomeMessageEN());
		}
	}
}
