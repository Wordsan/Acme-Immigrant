package controllers.anonymous;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CountryService;
import controllers.AbstractController;
import domain.Country;

@Controller
@RequestMapping("/country")
public class CountryNonAuthController extends AbstractController {

	// Services
	@Autowired
	CountryService countryService;

	// Constructors (Debugueo)
	public CountryNonAuthController() {
		super();
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Country> countries = new ArrayList<>();

		countries = this.countryService.findAll();
		result = new ModelAndView("country/list");
		result.addObject("countries", countries);
		result.addObject("requestURI", "country/list.do");
		return result;
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int countryId) {
		ModelAndView result;
		Country country;

		country = this.countryService.findOne(countryId);
		result = new ModelAndView("country/display");
		result.addObject("country", country);
		return result;
	}
}
