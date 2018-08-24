package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import utilities.ForbbidenActionException;
import domain.Immigrant;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ImmigrantServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	ImmigrantService immigrantService;

	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		final Object testingData[][] = {
				{ "immigrant1", "Chicote", null },
				{ "immigrant2", "Chicote",
						new ForbbidenActionException().getClass() } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void register() throws ForbbidenActionException {
		super.unauthenticate();
		final Immigrant i = this.immigrantService.create();
		i.setName("pepe");
		i.setSurname("shurmano");
		i.setEmail("email@user.com");
		i.getUserAccount().setPassword("prueba");
		i.getUserAccount().setUsername("user");
		this.immigrantService.save(i);
	}

	@Test(expected = ForbbidenActionException.class)
	public void registerAuth() throws ForbbidenActionException {
		super.authenticate("immigrant1");
		final Immigrant i = this.immigrantService.create();
		i.setName("pepe");
		i.setSurname("shurmano");
		i.setEmail("email@user.com");
		i.getUserAccount().setPassword("prueba");
		i.getUserAccount().setUsername("user");
		this.immigrantService.save(i);
		super.unauthenticate();
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String beanName, final String name,
			final Class<?> expected) {
		Class<?> caught;
		Immigrant a;

		caught = null;
		try {
			super.authenticate("immigrant1");
			final int id = super.getEntityId(beanName);
			a = this.immigrantService.findOne(id);
			a.setName(name);
			this.immigrantService.save(a);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
