package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	// RF 26.2 An actor authenticated as an administrator must be able to create
	// categories
	@SuppressWarnings("unchecked")
	@Test
	public void driverCreate() {
		final List<Category> categories = (List<Category>) this.categoryService
				.findAll();
		final List<Category> categories2 = new ArrayList<Category>();
		final List<Category> categories3 = new ArrayList<Category>();
		categories3.add(categories.get(5));
		categories2.add(categories.get(5));
		categories2.add(categories.get(4));
		final Object testingData[][] = {
				{ "nombre", "admin", null, null, IllegalArgumentException.class },
				{ "nombre", "admin", "category1", null, null },
				{ "nombre", "officer1", "category1", null,
						ForbbidenActionException.class },
				{ "nombre", "admin", "category1", categories3, null },
				{ "non-existent", "admin", "category1", categories2, null } };

		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(List<Category>) testingData[i][3],
					(Class<?>) testingData[i][4]);
	}

	protected void templateCreate(final String nombre, final String user,
			final String padre, final List<Category> categories,
			final Class<?> expected) {
		Class<?> caught;
		Category padreCat = null;

		caught = null;
		try {
			super.authenticate(user);
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

	// RF 26.2 An actor authenticated as an administrator must be able to create
	// categories
	@Test
	public void driverEdit() {
		final Object testingData[][] = { { "admin", 0, null },
				{ "officer1", 0, ForbbidenActionException.class },
				{ "admin", 1, IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0],
					(int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateEdit(final String user, final int op,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Category c = this.categoryService.findOne(super
					.getEntityId("category2"));
			if (op == 1)
				c.setParent(null);
			c.setName("nombre");
			this.categoryService.save(c);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// RF 25.1 An actor who is not authenticated must be able to navigate the
	// hierarchy of visas and display them
	@Test
	public void driverListDisplay() throws ObjectNotFoundException {
		final Object testingData[][] = { { "admin", "category2", null },
				{ null, "category2", null },
				{ "immigrant1", "category10", NumberFormatException.class },
				{ "officer1", "category5", IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateListDisplay((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateListDisplay(final String user,
			final String category, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (user != null)
				super.authenticate(user);
			final List<Category> categories = this.categoryService
					.categoriesOfFirstLevel();
			Assert.isTrue(categories.contains(this.categoryService
					.findOne(super.getEntityId(category))));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// RF 26.2 An actor authenticated as an administrator must be able to delete
	// categories
	@Test
	public void driverDelete() {
		final Object testingData[][] = { { "admin", "category2", null },
				{ "officer1", "category3", ForbbidenActionException.class },
				{ "immigrant1", "category10", NumberFormatException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateDelete(final String user, final String category,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Category c = this.categoryService.findOne(super
					.getEntityId(category));
			this.categoryService.delete(c);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// RF 26.5.1 An actor who is authenticated as an administrator must be able
	// to display a dash board with some statistics about visas per category
	@Test
	public void driverStatistics() {
		final Object testingData[][] = { { "admin", "AVG", null },
				{ "officer1", "AVG", ForbbidenActionException.class },
				{ "admin", "LMAO", IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.statisticsTemplate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void statisticsTemplate(final String user, final String stat,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Map<String, Double> statistics = this.categoryService
					.visasStadistics();
			Assert.isTrue(statistics.get(stat) != null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
