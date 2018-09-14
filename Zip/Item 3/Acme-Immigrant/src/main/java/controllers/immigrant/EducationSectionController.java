package controllers.immigrant;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.EducationSectionService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import controllers.AbstractController;
import domain.Application;
import domain.EducationSection;

@Controller
@RequestMapping("/educationSection/immigrant")
public class EducationSectionController extends AbstractController {

	// Services
	@Autowired
	EducationSectionService educationSectionService;

	@Autowired
	ApplicationService applicationService;

	// Constructors (Debugueo)
	public EducationSectionController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int educationId)
			throws ObjectNotFoundException, ForbbidenActionException {
		ModelAndView result;
		final EducationSection a;

		a = this.educationSectionService.findOne(educationId);
		result = new ModelAndView("educationSection/display");
		result.addObject("educationSection", a);

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int applicationId) {
		ModelAndView result;
		final EducationSection c = this.educationSectionService.create();

		result = this.createEditModelAndView(c, applicationId);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationSection educationSection,
			final BindingResult br, @RequestParam final int applicationId) {
		ModelAndView result;
		if (br.hasErrors())
			result = this.createEditModelAndView(educationSection,
					applicationId);
		else if (educationSection.getAwarded().after(
				new Date(System.currentTimeMillis())))
			result = this.createEditModelAndView(educationSection,
					applicationId, "awarded.date.past");
		else
			try {
				final EducationSection sc = this.educationSectionService
						.save(educationSection);
				final Application a = this.applicationService
						.findOne(applicationId);
				a.getEducationSections().add(sc);
				this.applicationService.save(a);
				result = new ModelAndView(
						"redirect:/application/immigrant/display.do");
				result.addObject("applicationId", applicationId);
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(educationSection,
						applicationId, "educationSection.commit.error");
			}

		return result;

	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(
			final EducationSection education, final int applicationId) {

		final ModelAndView result;

		result = this.createEditModelAndView(education, applicationId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			final EducationSection education, final int applicationId,
			final String messageCode) {

		ModelAndView result;
		result = new ModelAndView("educationSection/edit");
		result.addObject("educationSection", education);
		result.addObject("applicationId", applicationId);
		result.addObject("formURI", "educationSection/immigrant/edit.do");
		result.addObject("message", messageCode);

		return result;
	}

}
