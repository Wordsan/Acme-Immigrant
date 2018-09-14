package controllers.anonymous;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RequirementService;
import services.VisaService;
import utilities.ObjectNotFoundException;
import controllers.AbstractController;
import domain.Requirement;

@Controller
@RequestMapping("/requirement")
public class RequirementNonAuthController extends AbstractController {

	// Services
	@Autowired
	RequirementService requirementService;

	@Autowired
	VisaService visaService;

	// Constructors (Debugueo)
	public RequirementNonAuthController() {
		super();
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Requirement> requirements = new ArrayList<>();

		requirements = this.requirementService.findAll();
		result = new ModelAndView("requirement/list");
		result.addObject("requirements", requirements);
		return result;
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int requirementId)
			throws ObjectNotFoundException {
		ModelAndView result;
		Requirement requirement;

		requirement = this.requirementService.findOne(requirementId);
		result = new ModelAndView("requirement/display");
		result.addObject("requirement", requirement);
		result.addObject("visas", this.visaService.getAvailableVisas());
		return result;
	}
}
