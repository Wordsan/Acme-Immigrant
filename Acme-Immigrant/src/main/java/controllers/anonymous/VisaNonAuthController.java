package controllers.anonymous;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.VisaService;
import controllers.AbstractController;
import controllers.WelcomeController;
import domain.Visa;
import forms.SearchVisa;

@Controller
@RequestMapping("/visa")
public class VisaNonAuthController extends AbstractController {

	// Services
	@Autowired
	VisaService visaService;

	@Autowired
	ApplicationService applicationService;

	// Constructors (Debugueo)
	public VisaNonAuthController() {
		super();
	}

	// Search
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(final SearchVisa searchVisa) {
		ModelAndView result;
		List<Visa> visas = new ArrayList<>();

		if (searchVisa.getKeyword() != null)
			visas = this.visaService.searchVisaByKeyword(searchVisa
					.getKeyword());
		else
			visas = this.visaService.getAvailableVisas();
		result = new ModelAndView("visa/list");
		result.addObject("requestUri", "visa/search.do");
		result.addObject("visas", visas);
		return result;
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int visaId) {
		ModelAndView result;
		Visa visa;

		try {
			visa = this.visaService.findOne(visaId);
			if (visa == null)
				throw new Exception("object.not.fount");
		} catch (final Exception e) {
			result = WelcomeController.indice("object.not.found", null);
			return result;
		}
		result = new ModelAndView("visa/display");
		result.addObject("statistics1", this.applicationService
				.timeStadisticsByVisa(visaId).get("AVG"));
		result.addObject("statistics2", this.applicationService
				.timeStadisticsByVisa(visaId).get("STD"));
		result.addObject("visa", visa);
		return result;
	}

}
