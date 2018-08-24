package services;

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

	@Test
	public void driver() {
		final Object testingData[][] = { { "admin1", 0, null },
				{ "officer1", 0, ForbbidenActionException.class },
				{ null, 1, null }, { "admin1", 2, null },
				{ "officer1", 0, ForbbidenActionException.class },
				{ "admin1", 3, null },
				{ "officer1", 3, ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (int) testingData[i][1],
					(Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String username, final int operation,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			if (operation == 0) {
				final Visa v = this.visaService.create();
				v.setCategory(this.categoryService.findOne(super
						.getEntityId("category5")));
				v.setClase("verde");
				v.setCountry(this.countryService.findOne(super
						.getEntityId("country1")));
				v.setCurrency("?");
				v.setDescription("description");
				v.setPrice(200);
				this.visaService.save(v);
			} else if (operation == 1) {
				final Visa v = this.visaService.findOne(super
						.getEntityId("visa1"));
				Assert.isTrue(this.visaService
						.searchVisaByKeyword(v.getClase()).contains(v));
			} else if (operation == 2) {
				final Visa v = this.visaService.findOne(super
						.getEntityId("visa1"));
				v.setClase("clase");
				this.visaService.save(v);
			} else {
				final Visa v = this.visaService.findOne(super
						.getEntityId("visa1"));
				Assert.isTrue(this.visaService.abrogate(v));
			}
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
