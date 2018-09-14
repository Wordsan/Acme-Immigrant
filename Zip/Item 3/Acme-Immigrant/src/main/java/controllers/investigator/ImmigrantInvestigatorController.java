package controllers.investigator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ImmigrantService;
import controllers.AbstractController;
import domain.Immigrant;

@Controller
@RequestMapping("/immigrant/investigator")
public class ImmigrantInvestigatorController extends AbstractController {

	// Services
	@Autowired
	ImmigrantService immigrantService;

	// Constructors (Debugueo)
	public ImmigrantInvestigatorController() {
		super();
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Immigrant> immigrants = new ArrayList<>();

		immigrants = this.immigrantService.immigrantsOfInvestigator();
		result = new ModelAndView("immigrant/list");
		result.addObject("immigrants", immigrants);
		result.addObject("requestURI", "immigrant/list.do");
		return result;
	}
}
