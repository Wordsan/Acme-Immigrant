package controllers.officer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ImmigrantService;
import services.InvestigatorService;
import utilities.ForbbidenActionException;
import controllers.AbstractController;
import domain.Investigator;

@Controller
@RequestMapping("/investigator/officer")
public class InvestigatorOfficerController extends AbstractController {

	// Services
	@Autowired
	InvestigatorService investigatorService;

	@Autowired
	ImmigrantService immigrantService;

	// Constructors (Debugueo)
	public InvestigatorOfficerController() {
		super();
	}

	// Assign
	@RequestMapping(value = "/assign", method = RequestMethod.POST)
	public ModelAndView assign(@RequestParam final int investigatorId,
			@RequestParam final int immigrantId)
			throws ForbbidenActionException {
		ModelAndView result;

		if (this.investigatorService.assign(investigatorId, immigrantId,
				LoginService.getPrincipal()))
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			result = new ModelAndView("redirect:/view/immigrant/display.do");
			result.addObject("immigrantId", immigrantId);
			result.addObject("message", "investigator.assign.error");
		}
		return result;
	}

	// Assign
	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView assign(@RequestParam final int immigrantId) {
		ModelAndView result;

		final List<Investigator> inves = (List<Investigator>) this.investigatorService
				.findAll();
		result = new ModelAndView("investigator/assign");
		result.addObject("investigators", inves);
		result.addObject("immigrantId", immigrantId);

		return result;
	}

}
