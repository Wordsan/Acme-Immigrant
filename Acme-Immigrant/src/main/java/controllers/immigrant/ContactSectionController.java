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
import services.ContactSectionService;
import controllers.AbstractController;
import domain.Application;
import domain.ContactSection;

@Controller
@RequestMapping("/contactSection/immigrant")
public class ContactSectionController extends AbstractController {

	// Services
	@Autowired
	ContactSectionService contactSectionService;

	@Autowired
	ApplicationService applicationService;

	// Constructors (Debugueo)
	public ContactSectionController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int contactId) {
		ModelAndView result;
		final ContactSection a;

		a = this.contactSectionService.findOne(contactId);
		result = new ModelAndView("contactSection/display");
		result.addObject("contactSection", a);

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int applicationId) {
		ModelAndView result;
		final ContactSection c = this.contactSectionService.create();

		result = this.createEditModelAndView(c, applicationId);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ContactSection contactSection,
			@RequestParam final int applicationId, final BindingResult br) {
		ModelAndView result;
		if (br.hasErrors())
			result = this.createEditModelAndView(contactSection, applicationId);
		else
			try {
				final ContactSection sc = this.contactSectionService
						.save(contactSection);
				final Application a = this.applicationService
						.findOne(applicationId);
				a.getContactSections().add(sc);
				this.applicationService.save(a);
				result = new ModelAndView(
						"redirect:/application/immigrant/display.do");
				result.addObject("applicationId", applicationId);
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(contactSection,
						applicationId, "contactSection.commit.error");
			}

		return result;

	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(final ContactSection contact,
			final int applicationId) {

		final ModelAndView result;

		result = this.createEditModelAndView(contact, applicationId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ContactSection contact,
			final int applicationId, final String messageCode) {

		ModelAndView result;
		result = new ModelAndView("contactSection/edit");
		result.addObject("contactSection", contact);
		result.addObject("applicationId", applicationId);
		result.addObject("formURI", "contactSection/immigrant/edit.do");
		result.addObject("message", messageCode);

		return result;
	}

}
