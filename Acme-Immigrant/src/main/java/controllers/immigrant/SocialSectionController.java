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
import services.SocialSectionService;
import controllers.AbstractController;
import domain.Application;
import domain.SocialSection;

@Controller
@RequestMapping("/socialSection/immigrant")
public class SocialSectionController extends AbstractController {

	// Services
	@Autowired
	SocialSectionService socialSectionService;

	@Autowired
	ApplicationService applicationService;

	// Constructors (Debugueo)
	public SocialSectionController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int socialId) {
		ModelAndView result;
		final SocialSection a;

		a = this.socialSectionService.findOne(socialId);
		result = new ModelAndView("socialSection/display");
		result.addObject("socialSection", a);

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int applicationId) {
		ModelAndView result;
		final SocialSection c = this.socialSectionService.create();

		result = this.createEditModelAndView(c, applicationId);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SocialSection socialSection,
			final BindingResult br, @RequestParam final int applicationId) {
		ModelAndView result;
		if (br.hasErrors())
			result = this.createEditModelAndView(socialSection, applicationId);
		else
			try {
				final SocialSection sc = this.socialSectionService
						.save(socialSection);
				final Application a = this.applicationService
						.findOne(applicationId);
				a.getSocialSections().add(sc);
				this.applicationService.save(a);
				result = new ModelAndView(
						"redirect:/application/immigrant/display.do");
				result.addObject("applicationId", applicationId);
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(socialSection,
						applicationId, "socialSection.commit.error");
			}

		return result;

	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(final SocialSection social,
			final int applicationId) {

		final ModelAndView result;

		result = this.createEditModelAndView(social, applicationId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialSection social,
			final int applicationId, final String messageCode) {

		ModelAndView result;
		result = new ModelAndView("socialSection/edit");
		result.addObject("socialSection", social);
		result.addObject("applicationId", applicationId);
		result.addObject("formURI", "socialSection/immigrant/edit.do");
		result.addObject("message", messageCode);

		return result;
	}

}
