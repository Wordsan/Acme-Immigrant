package controllers.officer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.DecisionService;
import services.OfficerService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import controllers.AbstractController;
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
	public ModelAndView display(@RequestParam final int decisionId)
			throws ObjectNotFoundException {
		ModelAndView result;
		final Decision d;

		d = this.decisionService.findOne(decisionId);

		result = new ModelAndView("decision/display");
		result.addObject("decision", d);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam final int applicationId,
			final boolean accepted, final String reason)
			throws ForbbidenActionException, ObjectNotFoundException {
		ModelAndView result;

		int code;
		try {
			// El metodo devuelve un codigo numerico dependiendo del resultado,
			// para mas informacion de los codigos mirar el metodo
			code = this.decisionService.createNew(applicationId, accepted,
					reason);
		} catch (final IllegalArgumentException e) {
			/*
			 * Esta excepcion se lanza manualmente en caso de que se rechace la
			 * solicitud y no se proporcione una razon, lo cual es obligatorio
			 * cuando se rechaza
			 */
			result = new ModelAndView(
					"redirect:/application/officer/display.do?applicationId="
							+ applicationId);
			result.addObject("message", "decision.reason.error");
			return result;
		}
		if (code == 0)
			result = new ModelAndView(
					"redirect:/application/officer/display.do?applicationId="
							+ applicationId);
		else {
			result = new ModelAndView("application/display");
			result.addObject("applicationId", applicationId);
			result.addObject("message", "decision.commit.error");
		}

		return result;
	}

	// Save
	// No se usa ya que no se pide que se pueda editar una decision
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Decision decision,
			final BindingResult br) {

		ModelAndView result;

		if (br.hasErrors())
			result = this.createEditModelAndView(decision);
		else
			try {
				if (!decision.isAccepted()
						&& (decision.getComments() == "" || decision
								.getComments() == null)) {
					result = this.createEditModelAndView(decision,
							"decision.reason.error");
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
