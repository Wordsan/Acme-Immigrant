package controllers.anonymous;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import utilities.ObjectNotFoundException;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category")
public class CategoryNonAuthController extends AbstractController {

	// Services
	@Autowired
	CategoryService categoryService;

	// Constructors (Debugueo)
	public CategoryNonAuthController() {
		super();
	}

	// Navigate through tree of categories
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView navigate(@RequestParam final int parentId) {
		ModelAndView result;
		List<Category> categories = new ArrayList<>();

		categories = this.categoryService.categoriesByCategoryId(parentId);
		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/list.do?parentId=" + parentId);
		return result;
	}

	// Categories of first level, childs of CATEGORY
	@RequestMapping(value = "/firsts", method = RequestMethod.GET)
	public ModelAndView listFirsts() {
		final ModelAndView result;
		List<Category> categories = new ArrayList<>();

		categories = this.categoryService.categoriesOfFirstLevel();
		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/firsts.do");
		return result;
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int categoryId)
			throws ObjectNotFoundException {
		ModelAndView result;
		Category category;

		category = this.categoryService.findOne(categoryId);
		result = new ModelAndView("category/display");
		result.addObject("category", category);
		return result;
	}
}
