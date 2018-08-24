package services;

import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Category;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	CategoryService categoryService;

	// Tests ------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	@Test
	public void driver() {
		final List<Category> categories = (List<Category>) this.categoryService
				.findAll();
		final List<Category> categories2 = new ArrayList<Category>();
		final List<Category> categories3 = new ArrayList<Category>();
		categories3.add(categories.get(5));
		categories2.add(categories.get(5));
		categories2.add(categories.get(4));
		final Object testingData[][] = {
				{ "nombre", null, null, IllegalArgumentException.class },
				{ "nombre", "category1", null, null },
				{ "nombre", "category1", categories3, null },
				{ "non-existent", "category1", categories2, null } };

		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0],
					(String) testingData[i][1],
					(List<Category>) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}

	@Test
	public void list() throws ObjectNotFoundException {
		Category c = null;
		try {
			c = this.categoryService.findOne(super.getEntityId("category3"));
		} catch (final ObjectNotFoundException e) {
			throw e;
		}
		Assert.isTrue(this.categoryService.findAll().contains(c));
	}

	@Test
	public void delete() throws ObjectNotFoundException,
			ForbbidenActionException, IllegalClassFormatException {
		super.authenticate("admin1");
		Category c;
		try {
			c = this.categoryService.findOne(super.getEntityId("category3"));
			this.categoryService.delete(c);
			Assert.isTrue(!this.categoryService.findAll().contains(c));
		} catch (final ObjectNotFoundException e) {
			throw e;
		}
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateCreate(final String nombre, final String padre,
			final List<Category> categories, final Class<?> expected) {
		Class<?> caught;
		Category padreCat = null;

		caught = null;
		try {
			if (!(padre == null))
				padreCat = this.categoryService.findOne(super
						.getEntityId(padre));
			final Category c = this.categoryService.create();
			c.setName(nombre);
			c.setParent(padreCat);
			if (categories != null)
				c.setChilds(categories);
			this.categoryService.save(c);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
