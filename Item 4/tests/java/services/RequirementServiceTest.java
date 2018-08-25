package services;

import java.util.Collection;
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
import domain.Law;
import domain.Requirement;
import domain.Visa;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RequirementServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	RequirementService requirementService;

	@Autowired
	VisaService visaService;

	@Autowired
	LawService lawService;

	// Tests ------------------------------------------------------------------

	// RF 25.1 An actor who is not authenticated must be able to display the
	// requirements (including the laws that generate them) of every visa that
	// he or she can display
	@Test
	public void driverListDisplay() throws ObjectNotFoundException {
		final Object testingData[][] = { { "admin", "requirement1", null },
				{ null, "requirement8", NumberFormatException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateListDisplay((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateListDisplay(final String user,
			final String requirement, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (user != null)
				super.authenticate(user);
			final List<Visa> visas = this.visaService.getAvailableVisas();
			final Requirement r = this.requirementService.findOne(super
					.getEntityId(requirement));
			Assert.isTrue(visas.get(0).getRequirements().contains(r));
			final Law l = this.lawService.findOne(r.getLaw().getId());
			Assert.notNull(l);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// RF 26.3 An actor authenticated as an administrator must be able to
	// create requirements
	@Test
	public void driverCreate() {
		final Object testingData[][] = { { "admin", null },
				{ "officer1", ForbbidenActionException.class },
				{ "immigrant2", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
	}

	protected void template(final String user, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Requirement r = this.requirementService.create();
			r.setDescription("description");
			r.setLaw(this.lawService.findOne(super.getEntityId("law1")));
			r.setTitle("title");
			this.requirementService.save(r);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// RF 26.3 An actor authenticated as an administrator must be able to
	// edit requirements
	@Test
	public void driverEdit() {
		final Object testingData[][] = {
				{ "officer1", "requirement1", "requirement",
						ForbbidenActionException.class },
				{ "admin", "requirement1", "descripcion", null },
				{ "admin", "requirement10", "permanente",
						NumberFormatException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}

	protected void templateEdit(final String user, final String requirement,
			final String description, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Requirement v = this.requirementService.findOne(super
					.getEntityId(requirement));
			v.setTitle(description);
			this.requirementService.save(v);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// RF 26.3 An actor authenticated as an administrator must be able to
	// delete requirements
	@Test
	public void driverDelete() {
		final Object testingData[][] = {
				{ "officer1", "requirement1", ForbbidenActionException.class },
				{ "admin", "requirement1", null },
				{ "immigrant2", "requirement2", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateDelete(final String user, final String requirement,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Requirement v = this.requirementService.findOne(super
					.getEntityId(requirement));
			this.requirementService.delete(v);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	// RF 26.3 An actor authenticated as an administrator must be able to
	// assign requirements to visas
	@Test
	public void driverAddVisa() {
		final Object testingData[][] = {
				{ "officer1", "requirement1", "visa5",
						ForbbidenActionException.class },
				{ "admin", "requirement1", "visa5", null },
				{ "admin", "requirement10", "visa8",
						NumberFormatException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateAddVisa((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}

	protected void templateAddVisa(final String user, final String requirement,
			final String law, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			this.requirementService.addVisa(super.getEntityId(requirement),
					super.getEntityId(law));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	protected void templateList(final String user, final String requirement,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			final Collection<Requirement> requirements = this.requirementService
					.findAll();
			Assert.isTrue(requirements.contains(this.requirementService
					.findOne(super.getEntityId(requirement))));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
