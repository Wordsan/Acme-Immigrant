package services;

import java.lang.instrument.IllegalClassFormatException;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.LoginService;
import utilities.AbstractTest;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Investigator;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class InvestigatorServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	InvestigatorService investigatorService;

	@Autowired
	ApplicationService applicationService;

	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		final Object testingData[][] = {
				{ "investigator1", "Chicote", null },
				{ "investigator2", "Chicote",
						new ForbbidenActionException().getClass() } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test(expected = ForbbidenActionException.class)
	public void registerBad() throws ForbbidenActionException {
		super.authenticate("immigrant1");
		final Investigator i = this.investigatorService.create();
		i.setName("pepe");
		i.setSurname("shurmano");
		i.setEmail("email@user.com");
		i.getUserAccount().setPassword("prueba");
		i.getUserAccount().setUsername("user");
		this.investigatorService.save(i);
	}

	@Test
	public void registerAuth() throws ForbbidenActionException {
		super.authenticate("admin1");
		final Investigator i = this.investigatorService.create();
		i.setName("pepe");
		i.setSurname("shurmano");
		i.setEmail("email@user.com");
		i.getUserAccount().setPassword("prueba");
		i.getUserAccount().setUsername("user");
		this.investigatorService.save(i);
		super.unauthenticate();
	}

	@Test
	public void assign() throws ForbbidenActionException,
			ObjectNotFoundException, IllegalClassFormatException {
		super.authenticate("immigrant1");
		this.applicationService.close(super.getEntityId("application1"));
		super.authenticate("officer1");
		this.applicationService.assign(super.getEntityId("application1"));
		this.investigatorService.assign(super.getEntityId("investigator1"),
				super.getEntityId("immigrant1"), LoginService.getPrincipal());
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String beanName, final String name,
			final Class<?> expected) {
		Class<?> caught;
		Investigator a;

		caught = null;
		try {
			super.authenticate("investigator1");
			final int id = super.getEntityId(beanName);
			a = this.investigatorService.findOne(id);
			a.setName(name);
			this.investigatorService.save(a);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
