package services;

import java.lang.instrument.IllegalClassFormatException;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.ResourceAccessException;

import utilities.AbstractTest;
import utilities.ApplicationLinkException;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import utilities.RandomUtilities;
import domain.Application;
import domain.CreditCard;
import domain.PersonalSection;
import domain.SocialSection;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	ApplicationService applicationService;

	@Autowired
	PersonalSectionService personalSectionService;

	@Autowired
	SocialSectionService socialSectionService;

	// Tests ------------------------------------------------------------------
	@Test
	public void driver() {
		final Object testingData1[][] = {
				{ "immigrant1", "visa1", "19", ResourceAccessException.class },
				{ "immigrant1", "visa2", "19", null },
				{ "officer1", "visa2", "19", ForbbidenActionException.class },
				{ "immigrant1", "visa2", "18",
						IllegalClassFormatException.class } };

		final Object testingData2[][] = {
				{ "immigrant1", "application1", null },
				{ "immigrant1", "application2", ForbbidenActionException.class },
				{ "immigrant2", "application2", IllegalArgumentException.class } };

		final Object testingData3[][] = { { "application1", "officer3", null },
				{ "application3", "officer3", ForbbidenActionException.class } };

		final Object testingData4[][] = {
				{ "application2", "immigrant2", null },
				{ "application2", "officer2", null },
				{ "application2", "investigator2", null },
				{ "application2", "immigrant1", ForbbidenActionException.class },
				{ "application2", "officer1", ForbbidenActionException.class },
				{ "application2", "investigator1",
						ForbbidenActionException.class } };

		for (int i = 0; i < testingData1.length; i++)
			this.templateCrear((String) testingData1[i][0],
					(String) testingData1[i][1], (String) testingData1[i][2],
					(Class<?>) testingData1[i][3]);

		for (int i = 0; i < testingData2.length; i++)
			this.templateClose((String) testingData2[i][0],
					(String) testingData2[i][1], (Class<?>) testingData2[i][2]);

		for (int i = 0; i < testingData3.length; i++)
			this.templateAssign((String) testingData3[i][0],
					(String) testingData3[i][1], (Class<?>) testingData3[i][2]);

		for (int i = 0; i < testingData4.length; i++)
			this.templateDisplay((String) testingData4[i][0],
					(String) testingData4[i][1], (Class<?>) testingData4[i][2]);
	}

	@Test(expected = ApplicationLinkException.class)
	public void testLinkErroneo() throws ApplicationLinkException {
		super.authenticate("immigrant2");
		this.applicationService.link("180702-MFVU24",
				super.getEntityId("application2"));

	}

	@Test
	public void testLink() throws ApplicationLinkException {
		super.authenticate("immigrant2");
		this.applicationService.link("180702-MFVU24",
				super.getEntityId("application3"));
	}

	@Test
	public void testSearch() throws ForbbidenActionException,
			ObjectNotFoundException {
		super.authenticate("immigrant2");
		Assert.isTrue(
				this.applicationService.applicationsFromStatus("ACCEPTED")
						.contains(
								this.applicationService.findOne(super
										.getEntityId("application2"))),
				"Debe estar contenida");
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateCrear(final String user, final String visa,
			final String creditCardYear, final Class<?> expected) {
		Class<?> caught;
		int visaId;
		final CreditCard c = new CreditCard();
		c.setHolderName("holder");
		c.setBrandName("VISA");
		c.setNumber(utilities.RandomUtilities.generarCCNumber());
		c.setExpirationMonth("09");
		c.setExpirationYear(creditCardYear);
		c.setCVVCode(utilities.RandomUtilities.generarNumero(0, 999));
		caught = null;

		try {
			visaId = super.getEntityId(visa);
			super.authenticate(user);
			PersonalSection ps = this.personalSectionService.create();
			ps.setBirthDate(RandomUtilities.generarFechaPasada());
			ps.setBirthPlace(RandomUtilities.generarTexto());
			ps.setFullNames(RandomUtilities.generarTexto());
			ps.setPicture(RandomUtilities.generarURL());
			SocialSection ss = this.socialSectionService.create();
			ss.setLinkProfile(RandomUtilities.generarURL());
			ss.setNickname(RandomUtilities.generarTexto());
			ss.setSocialNetwork(RandomUtilities.generarTexto());
			try {
				ps = this.personalSectionService.save(ps);
				ss = this.socialSectionService.save(ss);
			} catch (final ForbbidenActionException e) {
				// Nunca pasara
			}
			final Application a = this.applicationService.create(visaId);
			a.setPersonalSection(ps);
			a.setCreditCard(c);
			a.getSocialSections().add(ss);
			this.applicationService.save(a);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();

		this.checkExceptions(expected, caught);
	}

	protected void templateAssign(final String application, final String user,
			final Class<?> expected) {
		Class<?> caught;
		caught = null;
		super.unauthenticate();
		try {
			super.authenticate(user);
			this.applicationService.assign(super.getEntityId(application));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();

		this.checkExceptions(expected, caught);
	}

	protected void templateClose(final String user, final String application,
			final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(user);
			this.applicationService.close(super.getEntityId(application));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();

		this.checkExceptions(expected, caught);
	}

	protected void templateDisplay(final String application, final String user,
			final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(user);
			this.applicationService.findOne(super.getEntityId(application));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();

		this.checkExceptions(expected, caught);
	}
}
