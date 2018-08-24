package controllers.anonymous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LawService;
import utilities.ObjectNotFoundException;
import controllers.AbstractController;
import domain.Law;

@Controller
@RequestMapping("/law")
public class LawNonAuthController extends AbstractController {

	// Services
	@Autowired
	LawService lawService;

	// Constructors (Debugueo)
	public LawNonAuthController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int lawId)
			throws ObjectNotFoundException {
		ModelAndView result;
		Law law;

		law = this.lawService.findOne(lawId);
		result = new ModelAndView("law/display");
		result.addObject("law", law);
		return result;
	}
}
