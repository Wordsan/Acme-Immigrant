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
import utilities.ObjectNotFoundException;
import domain.Law;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LawServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	LawService lawService;

	@Autowired
	CountryService countryService;

	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		final Object testingData[][] = { { "admin1", 0, null },
				{ "officer1", 0, ForbbidenActionException.class },
				{ "admin1", 1, null },
				{ "officer1", 1, ForbbidenActionException.class },
				{ "admin1", 2, null },
				{ "officer1", 2, ForbbidenActionException.class },
				{ "admin1", 3, null },
				{ "officer1", 3, ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (int) testingData[i][1],
					(Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String user, final int operacion,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (operacion == 0) {
				super.authenticate(user);
				final Law l = this.lawService.create();
				l.setCountry(this.countryService.findOne(super
						.getEntityId("country1")));
				l.setTitle("titulo");
				l.setText("texto");
				this.lawService.save(l);
			} else if (operacion == 1) {
				super.authenticate(user);
				final Law l = this.lawService
						.findOne(super.getEntityId("law1"));
				l.setTitle("title");
				this.lawService.save(l);
			} else if (operacion == 2) {
				super.authenticate(user);
				final Law l = this.lawService
						.findOne(super.getEntityId("law1"));
				this.lawService.abrogate(l.getId());
				Assert.isTrue(l.getAbrogatedAt() != null);
			} else {
				super.authenticate(user);
				Law l;
				try {
					l = this.lawService.findOne(super.getEntityId("law2"));
				} catch (final ObjectNotFoundException e) {
					l = this.lawService.findOne(super.getEntityId("law1"));
				}
				this.lawService.delete(l);
				Assert.isTrue(!this.lawService.findAll().contains(l));
			}
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
