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
import services.CategoryService;
import controllers.AbstractController;
import controllers.WelcomeController;
import domain.Category;

@Controller
@RequestMapping("/category/admin")
public class CategoryAdminController extends AbstractController {

	// Services
	@Autowired
	CategoryService categoryService;

	@Autowired
	AdministratorService administratorService;

	// Constructors (Debugueo)
	public CategoryAdminController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int categoryId) {
		ModelAndView result;
		final Category a;

		try {
			a = this.categoryService.findOne(categoryId);
			if (a == null)
				throw new Exception("object.not.fount");
		} catch (final Exception e) {
			result = WelcomeController.indice("object.not.found",
					this.administratorService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		result = new ModelAndView("category/display");
		result.addObject("category", a);

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Category v;

		v = this.categoryService.create();

		result = this.createEditModelAndView(v);

		return result;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {

		ModelAndView result;
		Category a;

		try {
			a = this.categoryService.findOne(categoryId);
			if (a == null)
				throw new Exception("object.not.fount");
			if (a.getName().equals("CATEGORY")) {
				result = WelcomeController.indice("forbbiden.access.error",
						this.administratorService.getActorByUA(LoginService
								.getPrincipal()));
				return result;
			}
		} catch (final Exception e) {
			result = WelcomeController.indice("object.not.found",
					this.administratorService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		result = this.createEditModelAndView(a);

		return result;
	}

	// Delete
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView delete(final Category category,
			final BindingResult binding) {
		ModelAndView result;
		if (category.getName().equals("CATEGORY")) {
			result = WelcomeController.indice("forbbiden.access.error",
					this.administratorService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		try {
			this.categoryService.delete(category);
			result = this.list();
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(category,
					"category.commit.error");
		}

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Category category,
			final BindingResult br) {

		ModelAndView result;
		if (category.getName().equals("CATEGORY")) {
			result = WelcomeController.indice("forbbiden.access.error",
					this.administratorService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		if (br.hasErrors())
			result = this.createEditModelAndView(category);
		else if (category.getParent() == null)
			result = this.createEditModelAndView(category,
					"javax.validation.constraints.NotNull.message");
		else
			try {
				this.categoryService.save(category);
				result = this.list();
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(category,
						"category.commit.error");
			}

		return result;

	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		List<Category> categories = new ArrayList<>();

		categories = (List<Category>) this.categoryService.findAll();
		result = new ModelAndView("category/list");
		result.addObject("requestUri", "category/admin/list.do");
		result.addObject("categories", categories);
		return result;
	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(final Category category) {

		final ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category,
			final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("category/edit");
		result.addObject("category", category);
		result.addObject("formURI", "category/admin/edit.do");
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("messageCode", messageCode);

		return result;
	}

}
