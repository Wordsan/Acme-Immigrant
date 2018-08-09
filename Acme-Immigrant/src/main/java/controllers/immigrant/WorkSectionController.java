package controllers.immigrant;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.WorkSectionService;
import controllers.AbstractController;
import domain.Application;
import domain.WorkSection;

@Controller
@RequestMapping("/workSection/immigrant")
public class WorkSectionController extends AbstractController {

	// Services
	@Autowired
	WorkSectionService workSectionService;

	@Autowired
	ApplicationService applicationService;

	// Constructors (Debugueo)
	public WorkSectionController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int workId) {
		ModelAndView result;
		final WorkSection a;

		a = this.workSectionService.findOne(workId);
		result = new ModelAndView("workSection/display");
		result.addObject("workSection", a);

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int applicationId) {
		ModelAndView result;
		final WorkSection c = this.workSectionService.create();

		result = this.createEditModelAndView(c, applicationId);

		return result;
	}

	// Save
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final WorkSection workSection,
			final BindingResult br, @RequestParam final int applicationId) {
		ModelAndView result;
		if (br.hasErrors())
			result = this.createEditModelAndView(workSection, applicationId);
		else
			try {
				final WorkSection sc = this.workSectionService
						.save(workSection);
				final Application a = this.applicationService
						.findOne(applicationId);
				a.getWorkSections().add(sc);
				this.applicationService.save(a);
				result = new ModelAndView(
						"redirect:/application/immigrant/display.do");
				result.addObject("applicationId", applicationId);
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(workSection,
						applicationId, "workSection.commit.error");
			}

		return result;

	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(final WorkSection work,
			final int applicationId) {

		final ModelAndView result;

		result = this.createEditModelAndView(work, applicationId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final WorkSection work,
			final int applicationId, final String messageCode) {

		ModelAndView result;
		result = new ModelAndView("workSection/edit");
		result.addObject("workSection", work);
		result.addObject("applicationId", applicationId);
		result.addObject("formURI", "workSection/immigrant/edit.do");
		result.addObject("message", messageCode);

		return result;
	}

}
