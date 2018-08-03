package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ImmigrantService;
import services.InvestigatorService;
import domain.Immigrant;

@Controller
@RequestMapping("/view/immigrant")
public class ViewImmigrantController extends AbstractController {

	// Services
	@Autowired
	ImmigrantService immigrantService;

	@Autowired
	InvestigatorService investigatorService;

	// Constructors (Debugueo)
	public ViewImmigrantController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int immigrantId) {
		ModelAndView result;
		final Immigrant a;

		a = this.immigrantService.findOne(immigrantId);
		result = new ModelAndView("immigrant/display");
		result.addObject("immigrant", a);
		result.addObject("investigators", this.investigatorService.findAll());

		return result;
	}

}
