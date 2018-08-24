package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import utilities.ForbbidenActionException;
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

	// RF 26.3 An actor authenticated as an administrator must be able to create
	// laws
	@Test
	public void driverCreate() {
		final Object testingData[][] = { { "admin", null },
				{ "officer1", ForbbidenActionException.class },
				{ "immigrant1", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
	}

	protected void template(final String user, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Law l = this.lawService.create();
			l.setCountry(this.countryService.findOne(super
					.getEntityId("country1")));
			l.setTitle("titulo");
			l.setText("texto");
			this.lawService.save(l);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// RF 26.3 An actor authenticated as an administrator must be able to edit
	// laws
	@Test
	public void driverEdit() {
		final Object testingData[][] = {
				{ "officer1", "law1", "law", ForbbidenActionException.class },
				{ "admin", "law1", "descripcion", null },
				{ "admin", "law10", "permanente", NumberFormatException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}

	protected void templateEdit(final String user, final String law,
			final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Law v = this.lawService.findOne(super.getEntityId(law));
			v.setText(description);
			this.lawService.save(v);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// RF 26.3 An actor authenticated as an administrator must be able to list
	// laws
	@Test
	public void driverList() {
		final Object testingData[][] = {
				{ "officer1", "law1", ForbbidenActionException.class },
				{ "admin", "law1", null },
				{ "immigrant2", "law2", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateList((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateList(final String user, final String law,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Collection<Law> laws = this.lawService.findAll();
			Assert.isTrue(laws.contains(this.lawService.findOne(super
					.getEntityId(law))));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// RF An actor authenticated as an administrator must be able to
	// abrogate laws
	// PD: No aparece como requisito funcional pero se desprende del RI 23
	@Test
	public void driverAbrogate() {
		final Object testingData[][] = {
				{ "officer1", "law1", ForbbidenActionException.class },
				{ "admin", "law1", null },
				{ "immigrant2", "law2", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateAbrogate((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateAbrogate(final String user, final String law,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Law v = this.lawService.findOne(super.getEntityId(law));
			this.lawService.abrogate(v.getId());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// RF 26.3 An actor authenticated as an administrator must be able to
	// delete laws
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
				{ "officer1", "law1", ForbbidenActionException.class },
				{ "admin", "law1", null },
				{ "immigrant2", "law2", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateDelete(final String user, final String law,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Law v = this.lawService.findOne(super.getEntityId(law));
			this.lawService.delete(v);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
