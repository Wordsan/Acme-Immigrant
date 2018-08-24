package controllers.officer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.OfficerService;
import services.ReportService;
import utilities.ObjectNotFoundException;
import controllers.AbstractController;
import domain.Report;

@Controller
@RequestMapping("/report/officer")
public class ReportOfficerController extends AbstractController {

	// Services
	@Autowired
	ReportService reportService;

	@Autowired
	OfficerService officerService;

	// Constructors (Debugueo)
	public ReportOfficerController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int reportId)
			throws ObjectNotFoundException {
		ModelAndView result;
		final Report a;

		a = this.reportService.findOne(reportId);
		result = new ModelAndView("report/display");
		result.addObject("report", a);

		return result;
	}

}
