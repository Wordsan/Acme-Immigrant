package controllers.anonymous;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LawService;
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

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Law> laws = new ArrayList<>();

		laws = this.lawService.findAll();
		result = new ModelAndView("law/list");
		result.addObject("laws", laws);
		return result;
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int lawId) {
		ModelAndView result;
		Law law;

		law = this.lawService.findOne(lawId);
		result = new ModelAndView("law/display");
		result.addObject("law", law);
		return result;
	}
}
