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

import security.LoginService;
import services.AdministratorService;
import services.CountryService;
import services.LawService;
import controllers.AbstractController;
import controllers.WelcomeController;
import domain.Law;

@Controller
@RequestMapping("/law/admin")
public class LawAdminController extends AbstractController {

	// Services
	@Autowired
	LawService lawService;

	@Autowired
	CountryService countryService;

	@Autowired
	AdministratorService administratorService;

	// Constructors (Debugueo)
	public LawAdminController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int lawId) {
		ModelAndView result;
		final Law a;

		try {
			a = this.lawService.findOne(lawId);
			if (a == null)
				throw new Exception("object.not.fount");
		} catch (final Exception e) {
			result = WelcomeController.indice("object.not.found",
					this.administratorService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		result = new ModelAndView("law/display");
		result.addObject("law", a);

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Law v;

		v = this.lawService.create();

		result = this.createEditModelAndView(v);

		return result;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int lawId) {

		ModelAndView result;
		Law a;

		try {
			a = this.lawService.findOne(lawId);
			if (a == null)
				throw new Exception("object.not.fount");
		} catch (final Exception e) {
			result = WelcomeController.indice("object.not.found",
					this.administratorService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		result = this.createEditModelAndView(a);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Law law, final BindingResult br) {

		ModelAndView result;
		if (br.hasErrors())
			result = this.createEditModelAndView(law);
		else
			try {
				this.lawService.save(law);
				result = this.list();
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(law, "law.commit.error");
			}

		return result;

	}

	// Delete
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView delete(final Law law, final BindingResult binding) {
		ModelAndView result;

		try {
			this.lawService.delete(law);
			result = this.list();
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(law, "law.commit.error");
		}

		return result;
	}

	// Abrogate
	@RequestMapping(value = "/abrogate", method = RequestMethod.GET)
	public ModelAndView abrogate(@RequestParam final int lawId) {
		ModelAndView result;

		final int code = this.lawService.abrogate(lawId);
		if (code == 0)
			result = this.list();
		else
			result = this.createEditModelAndView(
					this.lawService.findOne(lawId), "law.commit.error");
		return result;
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		List<Law> laws = new ArrayList<>();

		laws = (List<Law>) this.lawService.findAll();
		result = new ModelAndView("law/list");
		result.addObject("requestUri", "law/admin/list.do");
		result.addObject("laws", laws);
		return result;
	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(final Law law) {

		final ModelAndView result;

		result = this.createEditModelAndView(law, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Law law,
			final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("law/edit");
		result.addObject("law", law);
		result.addObject("countries", this.countryService.findAll());
		result.addObject("formURI", "law/admin/edit.do");
		result.addObject("messageCode", messageCode);

		return result;
	}

}
