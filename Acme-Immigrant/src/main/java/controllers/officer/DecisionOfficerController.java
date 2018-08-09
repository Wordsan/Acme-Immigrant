package controllers.officer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import services.DecisionService;
import services.OfficerService;
import controllers.AbstractController;
import controllers.WelcomeController;
import domain.Decision;

@Controller
@RequestMapping("/decision/officer")
public class DecisionOfficerController extends AbstractController {

	// Services
	@Autowired
	DecisionService decisionService;
	@Autowired
	OfficerService officerService;
	@Autowired
	ApplicationService applicationService;

	// Constructors (Debugueo)
	public DecisionOfficerController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int decisionId) {
		ModelAndView result;
		final Decision d;

		d = this.decisionService.findOne(decisionId);
		if (!this.officerService.getActorByUA(LoginService.getPrincipal())
				.getApplications().contains(d.getApplication())) {
			result = WelcomeController.indice("forbbiden.access.error",
					this.officerService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		result = new ModelAndView("decision/display");
		result.addObject("decision", d);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam final int applicationId,
			final boolean accepted, final String reason) {
		ModelAndView result;

		if (!this.officerService.getActorByUA(LoginService.getPrincipal())
				.getApplications()
				.contains(this.applicationService.findOne(applicationId))) {
			result = WelcomeController.indice("forbbiden.access.error",
					this.officerService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		final int code = this.decisionService.createNew(applicationId,
				accepted, reason);
		if (code == 0)
			result = new ModelAndView(
					"redirect:/application/officer/display.do?applicationId="
							+ applicationId);
		else if (code == 1) {
			result = new ModelAndView(
					"redirect:/application/officer/display.do?applicationId="
							+ applicationId);
			result.addObject("message", "decision.reason.error");
		} else {
			result = new ModelAndView("application/display");
			result.addObject("applicationId", applicationId);
			result.addObject("message", "decision.commit.error");
		}

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Decision decision,
			final BindingResult br) {

		ModelAndView result;
		if (!this.officerService.getActorByUA(LoginService.getPrincipal())
				.getApplications().contains(decision.getApplication())) {
			result = WelcomeController.indice("forbbiden.access.error",
					this.officerService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		if (br.hasErrors())
			result = this.createEditModelAndView(decision);
		else
			try {
				if (!decision.isAccepted()
						&& (decision.getComments() == "" || decision
								.getComments() == null)) {
					result = this.createEditModelAndView(decision,
							"decision.comments.error");
					return result;
				}
				this.decisionService.save(decision);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(decision,
						"decision.commit.error");
			}

		return result;

	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(final Decision d) {

		final ModelAndView result;

		result = this.createEditModelAndView(d, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Decision d,
			final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("decision/edit");
		result.addObject("decision", d);
		result.addObject("formURI", "decision/officer/edit.do");
		result.addObject("messageCode", messageCode);

		return result;
	}

}
