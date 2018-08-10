package controllers.officer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import services.OfficerService;
import controllers.AbstractController;
import controllers.WelcomeController;
import domain.Application;

@Controller
@RequestMapping("/application/officer")
public class ApplicationOfficerController extends AbstractController {

	// Services
	@Autowired
	ApplicationService applicationService;

	@Autowired
	OfficerService officerService;

	// Constructors (Debugueo)
	public ApplicationOfficerController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		ModelAndView result;
		final Application a;

		try {
			a = this.applicationService.findOne(applicationId);
			if (a == null)
				throw new Exception("object.not.fount");
		} catch (final Exception e) {
			result = WelcomeController.indice("object.not.found",
					this.officerService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		if (a.getOfficer() == null
				|| !a.getOfficer().equals(
						this.officerService.getActorByUA(LoginService
								.getPrincipal()))) {
			result = WelcomeController.indice("forbbiden.access.error",
					this.officerService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		result = new ModelAndView("application/display");
		result.addObject("application", a);

		return result;
	}

	// Assign
	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView close(@RequestParam final int applicationId) {
		ModelAndView result;

		final int code = this.applicationService.assign(applicationId);
		if (code == 0)
			return this.display(applicationId);
		else if (code == 2) {
			result = this.display(applicationId);
			result.addObject("message", "officer.assign.error");
		} else {
			result = this.display(applicationId);
			result.addObject("message", "application.assign.error");
		}
		return result;
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final String status) {
		ModelAndView result;
		List<Application> apps = new ArrayList<>();

		if (status == null || status == "")
			apps = this.applicationService.applicationsFromOfficer();
		else
			apps = this.applicationService
					.applicationsFromOfficerStatus(status);
		result = new ModelAndView("application/list");
		result.addObject("applications", apps);
		result.addObject("requestURI", "application/officer/list.do");

		return result;
	}

	// List not assigned
	@RequestMapping(value = "/notAssigned", method = RequestMethod.GET)
	public ModelAndView listAssign(final String status) {
		ModelAndView result;
		List<Application> apps = new ArrayList<>();

		apps = this.applicationService.applicationsNotAssigned();
		result = new ModelAndView("application/list");
		result.addObject("applications", apps);
		result.addObject("requestURI", "application/officer/notAssigned.do");

		return result;
	}

}
