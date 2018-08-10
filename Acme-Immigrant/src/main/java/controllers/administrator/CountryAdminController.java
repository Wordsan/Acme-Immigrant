package controllers.administrator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AdministratorService;
import services.CountryService;
import controllers.AbstractController;
import controllers.WelcomeController;
import domain.Country;

@Controller
@RequestMapping("/country/admin")
public class CountryAdminController extends AbstractController {

	// Services
	@Autowired
	CountryService countryService;

	@Autowired
	AdministratorService administratorService;

	// Constructors (Debugueo)
	public CountryAdminController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int countryId) {
		ModelAndView result;
		final Country a;

		try {
			a = this.countryService.findOne(countryId);
			if (a == null)
				throw new Exception("object.not.fount");
		} catch (final Exception e) {
			result = WelcomeController.indice("object.not.found",
					this.administratorService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		result = new ModelAndView("country/display");
		result.addObject("country", a);

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Country v;

		v = this.countryService.create();

		result = this.createEditModelAndView(v);

		return result;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int countryId) {

		ModelAndView result;
		Country a;

		try {
			a = this.countryService.findOne(countryId);
			if (a == null)
				throw new Exception("object.not.fount");
		} catch (final Exception e) {
			result = WelcomeController.indice("object.not.found",
					this.administratorService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		result = this.createEditModelAndView(a);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Country country,
			final BindingResult br) {

		ModelAndView result;
		if (br.hasErrors())
			result = this.createEditModelAndView(country);
		else
			try {
				this.countryService.save(country);
				result = this.list();
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(country,
						"country.commit.error");
			}

		return result;

	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		List<Country> countries = new ArrayList<>();

		countries = (List<Country>) this.countryService.findAll();
		result = new ModelAndView("country/list");
		result.addObject("requestUri", "country/admin/list.do");
		result.addObject("countries", countries);
		return result;
	}

	// Delete
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView delete(final Country country,
			final BindingResult binding) {
		ModelAndView result;

		try {
			this.countryService.delete(country);
			result = this.list();
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(country,
					"country.commit.error");
		}

		return result;
	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(final Country country) {

		final ModelAndView result;

		result = this.createEditModelAndView(country, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Country country,
			final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("country/edit");
		result.addObject("country", country);
		result.addObject("formURI", "country/admin/edit.do");
		result.addObject("messageCode", messageCode);

		return result;
	}

}
