package services;

import java.util.Collection;
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
import domain.Visa;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class VisaServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	VisaService visaService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	CountryService countryService;

	// Tests ------------------------------------------------------------------

	// RF 14.1 An actor who is authenticated as an administrator must be able to
	// create visas
	// 5 positive cases, 5 negative cases
	@Test
	public void driverCreate() {
		// Clase, description, price, currency, country, category, requirements
		final Object testingData[][] = {
				{ "admin", "verde", "description", 200, "€", "country1",
						"category4", null },
				{ "admin", "verde", "description", 200, "€", "country2",
						"category4", null },
				{ "admin", "verde", "description", 200, "€", "country1",
						"category2", null },
				{ "admin", "verde", "description", 300, "€", "country1",
						"category4", null },
				{ "admin", "verde", "description", 200, "$", "country1",
						"category4", null },
				{ "admin", "gris", "description", 200, "€", "country8",
						"category4", NumberFormatException.class },
				{ "admin", "verde", "description", 200, "€", "country1",
						"category15", NumberFormatException.class },
				{ "admin", "verde", "description", "200", "€", "country1",
						"category4", ClassCastException.class },
				{ "immigrant1", "verde", "description", 200, "€", "country1",
						"category4", ForbbidenActionException.class },
				{ "officer2", "verde", "description", 200, "€", "country1",
						"category4", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6],
					(Class<?>) testingData[i][7]);
	}

	protected void template(final String username, final String clase,
			final String description, final Object price,
			final String currency, final String country, final String category,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			final Visa v = this.visaService.create();
			v.setCategory(this.categoryService.findOne(super
					.getEntityId(category)));
			v.setClase(clase);
			v.setCountry(this.countryService.findOne(super.getEntityId(country)));
			v.setCurrency(currency);
			v.setDescription(description);
			v.setPrice((int) price);
			this.visaService.save(v);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// RF 14.2 An actor who is authenticated as an administrator must be able to
	// edit visas
	@Test
	public void driverEdit() {
		final Object testingData[][] = {
				{ "officer1", "visa1", "visa", ForbbidenActionException.class },
				{ "admin", "visa1", "descripcion", null },
				{ "admin", "visa10", "permanente", NumberFormatException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}

	protected void templateEdit(final String user, final String visa,
			final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Visa v = this.visaService.findOne(super.getEntityId(visa));
			v.setDescription(description);
			this.visaService.save(v);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// RF 14.2 An actor who is authenticated as an administrator must be able to
	// list visas
	@Test
	public void driverList() {
		final Object testingData[][] = {
				{ "officer1", "visa1", ForbbidenActionException.class },
				{ "admin", "visa1", null },
				{ "immigrant2", "visa2", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateList((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateList(final String user, final String visa,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Collection<Visa> visas = this.visaService.findAll();
			Assert.isTrue(visas.contains(this.visaService.findOne(super
					.getEntityId(visa))));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// RF 14.2 An actor who is authenticated as an administrator must be able to
	// abrogate visas
	@Test
	public void driverAbrogate() {
		final Object testingData[][] = {
				{ "officer1", "visa1", ForbbidenActionException.class },
				{ "admin", "visa1", null },
				{ "immigrant2", "visa2", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateAbrogate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateAbrogate(final String user, final String visa,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Visa v = this.visaService.findOne(super.getEntityId(visa));
			this.visaService.abrogate(v);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	/*
	 * RF 10.1 An actor who is not authenticated must be able to search for
	 * visas using a single key word
	 * 
	 * RF 11.1 An actor who is authenticated must be able to do the same as an
	 * actor who is not authenticated but register to the system
	 */
	@Test
	public void driverSearch() {
		final Object testingData[][] = { { null, "visa", null },
				{ "admin", "", null }, { "immigrant1", "permanente", null } };

		for (int i = 0; i < testingData.length; i++)
			this.visaSearch((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void visaSearch(final String user, final String keyword,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final List<Visa> visas = this.visaService
					.searchVisaByKeyword(keyword);
			Assert.isTrue(!visas.isEmpty());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// RF 14.3.3 An actor who is authenticated as an administrator must be able
	// to display a dash board with some statistics about the price of the visas
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
			final Map<String, String> statistics = this.visaService
					.priceStadistics();
			Assert.isTrue(statistics.get(stat) != null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// RF 26.5.1 An actor who is authenticated as an administrator must be able
	// to display a dash board with some statistics about requirements per visa
	@Test
	public void driverStatistics2() {
		final Object testingData[][] = { { "admin", "AVG", null },
				{ "officer1", "AVG", ForbbidenActionException.class },
				{ "admin", "LMAO", IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.statisticsTemplate2((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void statisticsTemplate2(final String user, final String stat,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Map<String, Double> statistics = this.visaService
					.requirementsStadistics();
			Assert.isTrue(statistics.get(stat) != null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
