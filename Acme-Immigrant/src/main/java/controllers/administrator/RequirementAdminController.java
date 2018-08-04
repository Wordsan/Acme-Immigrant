package controllers.administrator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LawService;
import services.RequirementService;
import controllers.AbstractController;
import domain.Requirement;

@Controller
@RequestMapping("/requirement/admin")
public class RequirementAdminController extends AbstractController {

	// Services
	@Autowired
	RequirementService requirementService;

	@Autowired
	LawService lawService;

	// Constructors (Debugueo)
	public RequirementAdminController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int requirementId) {
		ModelAndView result;
		final Requirement a;

		a = this.requirementService.findOne(requirementId);
		result = new ModelAndView("requirement/display");
		result.addObject("requirement", a);

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Requirement v;

		v = this.requirementService.create();

		result = this.createEditModelAndView(v);

		return result;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int requirementId) {

		ModelAndView result;
		Requirement a;

		a = this.requirementService.findOne(requirementId);
		result = this.createEditModelAndView(a);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Requirement requirement,
			final BindingResult br) {

		ModelAndView result;
		if (br.hasErrors())
			result = this.createEditModelAndView(requirement);
		else
			try {
				this.requirementService.save(requirement);
				result = this.list();
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(requirement,
						"requirement.commit.error");
			}

		return result;

	}

	// Delete
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView delete(final Requirement requirement,
			final BindingResult binding) {
		ModelAndView result;

		try {
			this.requirementService.delete(requirement);
			result = this.list();
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(requirement,
					"requirement.commit.error");
		}

		return result;
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		List<Requirement> requirements = new ArrayList<>();

		requirements = (List<Requirement>) this.requirementService.findAll();
		result = new ModelAndView("requirement/list");
		result.addObject("requestUri", "requirement/admin/list.do");
		result.addObject("requirements", requirements);
		return result;
	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(final Requirement requirement) {

		final ModelAndView result;

		result = this.createEditModelAndView(requirement, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			final Requirement requirement, final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("requirement/edit");
		result.addObject("requirement", requirement);
		result.addObject("laws", this.lawService.findAll());
		result.addObject("formURI", "requirement/admin/edit.do");
		result.addObject("messageCode", messageCode);

		return result;
	}
}
