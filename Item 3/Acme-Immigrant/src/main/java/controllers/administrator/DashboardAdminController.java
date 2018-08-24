package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CategoryService;
import services.CountryService;
import services.ImmigrantService;
import services.InvestigatorService;
import services.OfficerService;
import services.VisaService;
import utilities.ForbbidenActionException;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/admin")
public class DashboardAdminController extends AbstractController {

	// Services
	@Autowired
	CategoryService categoryService;

	@Autowired
	ImmigrantService immigrantService;

	@Autowired
	ApplicationService applicationService;

	@Autowired
	CountryService countryService;

	@Autowired
	InvestigatorService investigatorService;

	@Autowired
	OfficerService officerService;

	@Autowired
	VisaService visaService;

	// Constructors (Debugueo)
	public DashboardAdminController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() throws ForbbidenActionException {
		ModelAndView result;
		Double avgAppUser, minAppUser, maxAppUser, stdAppUser;
		Double avgAppOff, minAppOff, maxAppOff, stdAppOff;
		String avgPrice, minPrice, maxPrice, stdPrice;
		Double avgImmInv, minImmInv, maxImmInv, stdImmInv;
		Double avgTimeDecision, minTimeDecision, maxTimeDecision, stdTimeDecision;
		Double avgVisaCat, minVisaCat, maxVisaCat, stdVisaCat;
		Double avgLawCoun, minLawCoun, maxLawCoun, stdLawCoun;
		Double avgReqVisa, minReqVisa, maxReqVisa, stdReqVisa;

		avgAppUser = this.immigrantService.applicationsStadistics().get("AVG");
		minAppUser = this.immigrantService.applicationsStadistics().get("MIN");
		maxAppUser = this.immigrantService.applicationsStadistics().get("MAX");
		stdAppUser = this.immigrantService.applicationsStadistics().get("STD");

		avgAppOff = this.officerService.applicationsStadistics().get("AVG");
		minAppOff = this.officerService.applicationsStadistics().get("MIN");
		maxAppOff = this.officerService.applicationsStadistics().get("MAX");
		stdAppOff = this.officerService.applicationsStadistics().get("STD");

		avgPrice = this.visaService.priceStadistics().get("AVG");
		minPrice = this.visaService.priceStadistics().get("MIN");
		maxPrice = this.visaService.priceStadistics().get("MAX");
		stdPrice = this.visaService.priceStadistics().get("STD");

		avgImmInv = this.investigatorService.immigrantsStadistics().get("AVG");
		minImmInv = this.investigatorService.immigrantsStadistics().get("MIN");
		maxImmInv = this.investigatorService.immigrantsStadistics().get("MAX");
		stdImmInv = this.investigatorService.immigrantsStadistics().get("STD");

		avgTimeDecision = this.applicationService.timeStadistics().get("AVG");
		minTimeDecision = this.applicationService.timeStadistics().get("MIN");
		maxTimeDecision = this.applicationService.timeStadistics().get("MAX");
		stdTimeDecision = this.applicationService.timeStadistics().get("STD");

		avgVisaCat = this.categoryService.visasStadistics().get("AVG");
		minVisaCat = this.categoryService.visasStadistics().get("MIN");
		maxVisaCat = this.categoryService.visasStadistics().get("MAX");
		stdVisaCat = this.categoryService.visasStadistics().get("STD");

		avgLawCoun = this.countryService.lawStadistics().get("AVG");
		minLawCoun = this.countryService.lawStadistics().get("MIN");
		maxLawCoun = this.countryService.lawStadistics().get("MAX");
		stdLawCoun = this.countryService.lawStadistics().get("STD");

		avgReqVisa = this.visaService.requirementsStadistics().get("AVG");
		minReqVisa = this.visaService.requirementsStadistics().get("MIN");
		maxReqVisa = this.visaService.requirementsStadistics().get("MAX");
		stdReqVisa = this.visaService.requirementsStadistics().get("STD");

		result = new ModelAndView("dasboard/display");

		result.addObject("avgAppUser", avgAppUser);
		result.addObject("minAppUser", minAppUser);
		result.addObject("maxAppUser", maxAppUser);
		result.addObject("stdAppUser", stdAppUser);

		result.addObject("avgAppOff", avgAppOff);
		result.addObject("minAppOff", minAppOff);
		result.addObject("maxAppOff", maxAppOff);
		result.addObject("stdAppOff", stdAppOff);

		result.addObject("avgPrice", avgPrice);
		result.addObject("minPrice", minPrice);
		result.addObject("maxPrice", maxPrice);
		result.addObject("stdPrice", stdPrice);

		result.addObject("avgImmInv", avgImmInv);
		result.addObject("minImmInv", minImmInv);
		result.addObject("maxImmInv", maxImmInv);
		result.addObject("stdImmInv", stdImmInv);

		result.addObject("avgTimeDecision", avgTimeDecision);
		result.addObject("minTimeDecision", minTimeDecision);
		result.addObject("maxTimeDecision", maxTimeDecision);
		result.addObject("stdTimeDecision", stdTimeDecision);

		result.addObject("avgVisaCat", avgVisaCat);
		result.addObject("minVisaCat", minVisaCat);
		result.addObject("maxVisaCat", maxVisaCat);
		result.addObject("stdVisaCat", stdVisaCat);

		result.addObject("avgLawCount", avgLawCoun);
		result.addObject("minLawCount", minLawCoun);
		result.addObject("maxLawCount", maxLawCoun);
		result.addObject("stdLawCount", stdLawCoun);

		result.addObject("avgReqVisa", avgReqVisa);
		result.addObject("minReqVisa", minReqVisa);
		result.addObject("maxReqVisa", maxReqVisa);
		result.addObject("stdReqVisa", stdReqVisa);

		return result;
	}

}
