package controllers.officer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;
import controllers.AbstractController;
import domain.Report;

@Controller
@RequestMapping("/report/officer")
public class ReportOfficerController extends AbstractController {

	// Services
	@Autowired
	ReportService reportService;

	// Constructors (Debugueo)
	public ReportOfficerController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int reportId) {
		ModelAndView result;
		final Report a;

		a = this.reportService.findOne(reportId);
		result = new ModelAndView("report/display");
		result.addObject("report", a);

		return result;
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final String status) {
		ModelAndView result;
		List<Report> reports = new ArrayList<>();

		reports = this.reportService.reportsOfAnOfficer();
		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		result.addObject("requestURI", "officer/report/list.do");

		return result;
	}

}
