package controllers.investigator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ImmigrantService;
import services.InvestigatorService;
import services.ReportService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import controllers.AbstractController;
import domain.Immigrant;
import domain.Investigator;
import domain.Report;

@Controller
@RequestMapping("/report/investigator")
public class ReportInvestigatorController extends AbstractController {

	// Services
	@Autowired
	ReportService reportService;

	@Autowired
	ImmigrantService immigrantService;

	@Autowired
	InvestigatorService investigatorService;

	// Constructors (Debugueo)
	public ReportInvestigatorController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int reportId)
			throws ObjectNotFoundException, ForbbidenActionException {
		ModelAndView result;
		final Report a;

		a = this.reportService.findOne(reportId);
		result = new ModelAndView("report/display");
		result.addObject("report", a);

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int immigrantId)
			throws ObjectNotFoundException {
		ModelAndView result;
		final Report c = this.reportService.create(immigrantId);

		result = this.createEditModelAndView(c);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Report report, final BindingResult br)
			throws ForbbidenActionException {
		ModelAndView result;
		if (br.hasErrors())
			result = this.createEditModelAndView(report);
		else
			try {
				final Report sc = this.reportService.save(report);
				final Immigrant i = sc.getImmigrant();
				final Investigator in = sc.getInvestigator();
				i.getReports().add(sc);
				in.getReports().add(sc);
				this.immigrantService.save(i);
				this.investigatorService.save(in);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final ForbbidenActionException ops) {
				throw ops;
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(report,
						"report.commit.error");
			}

		return result;

	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(final Report report) {

		final ModelAndView result;

		result = this.createEditModelAndView(report, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Report report,
			final String messageCode) {

		ModelAndView result;
		result = new ModelAndView("report/edit");
		result.addObject("report", report);
		result.addObject("formURI", "report/investigator/edit.do");
		result.addObject("message", messageCode);

		return result;
	}

}
