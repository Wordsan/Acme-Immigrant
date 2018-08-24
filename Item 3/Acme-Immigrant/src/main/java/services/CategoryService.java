package services;

import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.LoginService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Category;
import domain.Visa;

@Service
@Transactional
public class CategoryService {

	// Managed Repository
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private VisaService visaService;

	@Autowired
	private AdministratorService administratorService;

	// Constructors
	public CategoryService() {
		super();
	}

	// Simple CRUD methods

	public Category create() {
		final Category f = new Category();
		return f;
	}

	public Collection<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category findOne(final int categoryId)
			throws ObjectNotFoundException {
		final Category a = this.categoryRepository.findOne(categoryId);
		if (a == null)
			throw new ObjectNotFoundException();
		return a;
	}

	public Category save(final Category category)
			throws ForbbidenActionException {
		Category f;
		Assert.notNull(category);
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null
				|| category.getName().equals("CATEGORY"))
			throw new ForbbidenActionException();
		if (category.getParent() == null)
			throw new IllegalArgumentException();
		f = this.categoryRepository.save(category);
		return f;
	}

	public List<Category> getCategories() {
		return this.categoryRepository.getCategories();
	}

	public Map<String, Double> visasStadistics()
			throws ForbbidenActionException {
		final Double[] statistics = this.categoryRepository.visasStadistics();
		final Map<String, Double> res = new HashMap<>();
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();

		res.put("AVG", statistics[0]);
		res.put("MIN", statistics[1]);
		res.put("MAX", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

	public List<Category> categoriesByCategoryId(final Integer categoryId) {
		return this.categoryRepository.categoriesByCategoryId(categoryId);
	}

	public List<Category> categoriesOfFirstLevel() {
		return this.categoryRepository.categoriesOfFirstLevel();
	}

	public void delete(final Category category)
			throws ForbbidenActionException, IllegalClassFormatException {
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null
				|| category.getName().equals("CATEGORY"))
			throw new ForbbidenActionException();
		final Category parent = category.getParent();
		parent.getChilds().remove(category);
		this.categoryRepository.save(parent);
		final Collection<Category> childs = category.getChilds();
		category.setChilds(new ArrayList<Category>());
		if (childs != null)
			for (final Category c : childs)
				this.delete(c);
		final Collection<Visa> visas = category.getVisas();
		category.setVisas(new ArrayList<Visa>());
		if (visas != null)
			for (final Visa v : visas)
				this.visaService.delete(v);
		this.categoryRepository.delete(category);
	}
}
