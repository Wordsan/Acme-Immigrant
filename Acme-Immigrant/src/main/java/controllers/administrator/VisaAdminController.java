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
import services.ApplicationService;
import services.CategoryService;
import services.CountryService;
import services.VisaService;
import controllers.AbstractController;
import controllers.WelcomeController;
import domain.Visa;
import forms.SearchVisa;

@Controller
@RequestMapping("/visa/admin")
public class VisaAdminController extends AbstractController {

	// Services
	@Autowired
	VisaService visaService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	CountryService countryService;

	@Autowired
	ApplicationService applicationService;

	@Autowired
	AdministratorService administratorService;

	// Constructors (Debugueo)
	public VisaAdminController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int visaId) {
		ModelAndView result;
		final Visa a;

		try {
			a = this.visaService.findOne(visaId);
			if (a == null)
				throw new Exception("object.not.fount");
		} catch (final Exception e) {
			result = WelcomeController.indice("object.not.found",
					this.administratorService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		result = new ModelAndView("visa/display");
		result.addObject("statistics1", this.applicationService
				.timeStadisticsByVisa(visaId).get("AVG"));
		result.addObject("statistics2", this.applicationService
				.timeStadisticsByVisa(visaId).get("STD"));
		result.addObject("visa", a);

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Visa v;

		v = this.visaService.create();

		result = this.createEditModelAndView(v);

		return result;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int visaId) {

		ModelAndView result;
		Visa a;

		try {
			a = this.visaService.findOne(visaId);
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
	public ModelAndView save(@Valid final Visa visa, final BindingResult br) {

		ModelAndView result;
		if (br.hasErrors())
			result = this.createEditModelAndView(visa);
		else
			try {
				this.visaService.save(visa);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(visa, "visa.commit.error");
			}

		return result;

	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView search(final SearchVisa searchVisa) {
		ModelAndView result;
		List<Visa> visas = new ArrayList<>();

		if (searchVisa.getKeyword() != null)
			visas = this.visaService.searchVisaByKeyword(searchVisa
					.getKeyword());
		else
			visas = this.visaService.getAvailableVisas();
		result = new ModelAndView("visa/list");
		result.addObject("requestUri", "visa/admin/list.do");
		result.addObject("visas", visas);
		return result;
	}

	// Abrogate
	@RequestMapping(value = "/abrogate", method = RequestMethod.GET)
	public ModelAndView abrogate(final int visaId) {

		ModelAndView result;
		Visa v;
		try {
			v = this.visaService.findOne(visaId);
			if (v == null)
				throw new Exception("object.not.fount");
		} catch (final Exception e) {
			result = WelcomeController.indice("object.not.found",
					this.administratorService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		try {
			if (!this.visaService.abrogate(v))
				throw new Throwable();
			result = this.display(visaId);
		} catch (final Throwable ops) {
			result = this.createEditModelAndView(v, "visa.commit.error");
		}

		return result;

	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(final Visa visa) {

		final ModelAndView result;

		result = this.createEditModelAndView(visa, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Visa visa,
			final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("visa/edit");
		result.addObject("visa", visa);
		result.addObject("countries", this.countryService.findAll());
		result.addObject("categories", this.categoryService.getCategories());
		result.addObject("formURI", "visa/admin/edit.do");
		result.addObject("messageCode", messageCode);

		return result;
	}

}
