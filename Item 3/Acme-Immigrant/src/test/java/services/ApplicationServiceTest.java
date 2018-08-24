package services;

import java.lang.instrument.IllegalClassFormatException;
import java.util.Map;

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

	// RF 12.1 An actor who is authenticated as an immigrant must be able to
	// display his or her applications
	// RNF 19 An application must not be displayed to actors other than the one
	// who made it, the officer who self-assign it, and the investigators that
	// are assigned to it(if any)
	@Test
	public void driverDisplay() {

		final Object testingData4[][] = {
				{ "application2", "immigrant2", null },
				{ "application2", "officer2", null },
				{ "application2", "investigator2", null },
				{ "application2", "immigrant1", ForbbidenActionException.class },
				{ "application2", "officer1", ForbbidenActionException.class },
				{ "application2", "investigator1",
						ForbbidenActionException.class } };

		for (int i = 0; i < testingData4.length; i++)
			this.templateDisplay((String) testingData4[i][0],
					(String) testingData4[i][1], (Class<?>) testingData4[i][2]);
	}

	// RF 12.1 An actor who is authenticated as an immigrant must be able to
	// open applications
	@Test
	public void driverCrear() {
		final Object testingData1[][] = {
				{ "immigrant1", "visa1", "19", ResourceAccessException.class },
				{ "immigrant1", "visa2", "19", null },
				{ "officer1", "visa2", "19", ForbbidenActionException.class },
				{ "immigrant1", "visa2", "18",
						IllegalClassFormatException.class } };

		for (int i = 0; i < testingData1.length; i++)
			this.templateCrear((String) testingData1[i][0],
					(String) testingData1[i][1], (String) testingData1[i][2],
					(Class<?>) testingData1[i][3]);
	}

	// RF 12.1 An actor who is authenticated as an immigrant must be able to
	// close his or her open applications
	@Test
	public void driverClose() {
		final Object testingData2[][] = {
				{ "immigrant1", "application1", null },
				{ "immigrant1", "application2", ForbbidenActionException.class },
				{ "immigrant2", "application2", IllegalArgumentException.class } };

		for (int i = 0; i < testingData2.length; i++)
			this.templateClose((String) testingData2[i][0],
					(String) testingData2[i][1], (Class<?>) testingData2[i][2]);
	}

	// RF 13.1 An actor who is authenticated as an officer must be able to list
	// the applications and self-assign one of them as long as it's not already
	// assigned to other officer
	@Test
	public void driverAssign() {
		final Object testingData3[][] = { { "application1", "officer3", null },
				{ "application3", "officer3", ForbbidenActionException.class },
				{ "application2", "officer1", ForbbidenActionException.class } };

		for (int i = 0; i < testingData3.length; i++)
			this.templateAssign((String) testingData3[i][0],
					(String) testingData3[i][1], (Class<?>) testingData3[i][2]);
	}

	// RF 12.1 An actor who is authenticated as an immigrant must be able to
	// link his or her applications to applications by other users
	// RNF 19 An application may be linked to an application by another user by
	// entering its corresponding ticker in an input box
	@Test
	public void driverLink() {
		final Object testingData[][] = {
				{ "application1", ApplicationLinkException.class },
				{ "application2", ApplicationLinkException.class },
				{ "application3", null } };

		for (int i = 0; i < testingData.length; i++)
			this.templateLink((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
	}

	/*
	 * RF 12.1 An actor who is authenticated as an immigrant must be able to
	 * edit his or her applications
	 */
	@Test
	public void driverEdit() {
		final Object testingData[][] = {
				{ "application3", ForbbidenActionException.class },
				{ "application2", ForbbidenActionException.class },
				{ "application1", null } };

		for (int i = 0; i < testingData.length; i++)
			this.templateEdit((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
	}

	// RF 12.3 An user who is authenticated as an immigrant must be able to list
	// his or her applications according to its status
	@Test
	public void driverSearchImmigrant() {
		final Object testingData[][] = {
				{ "immigrant1", "application1", "OPENED", null },
				{ "immigrant2", "application2", "ACCEPTED", null },
				{ "immigrant2", "application3", "ACCEPTED",
						IllegalArgumentException.class },
				{ "immigrant3", "application4", "OPENED",
						IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateSearchImmigrant((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}

	// RF 13.2 An user who is authenticated as an officer must be able to list
	// his or her applications according to its status
	@Test
	public void driverSearchOfficer() {
		final Object testingData[][] = {
				{ "officer2", "application2", "ACCEPTED", null },
				{ "officer2", "application3", "ACCEPTED",
						IllegalArgumentException.class },
				{ "officer3", "application4", "OPENED",
						IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateSearchOfficer((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}

	// RF 14.3.1 An actor who is authenticated as an administrator must be able
	// to display a dash board with some statistics about applications per user
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
			final Map<String, Double> statistics = this.applicationService
					.timeStadistics();
			Assert.isTrue(statistics.get(stat) != null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateSearchImmigrant(final String user,
			final String application, final String status,
			final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(user);
			Assert.isTrue(this.applicationService
					.applicationsFromStatus(status).contains(
							this.applicationService.findOne(super
									.getEntityId(application))));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateSearchOfficer(final String user,
			final String application, final String status,
			final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(user);
			Assert.isTrue(this.applicationService
					.applicationsFromOfficerStatus(status).contains(
							this.applicationService.findOne(super
									.getEntityId(application))));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateEdit(final String application,
			final Class<?> expected) {
		Class<?> caught;
		caught = null;
		super.unauthenticate();
		try {
			super.authenticate("immigrant1");
			final Application a = this.applicationService.findOne(super
					.getEntityId(application));
			final CreditCard c = new CreditCard();
			c.setHolderName("holder");
			c.setBrandName("VISA");
			c.setNumber(utilities.RandomUtilities.generarCCNumber());
			c.setExpirationMonth("09");
			c.setExpirationYear("20");
			c.setCVVCode(237);
			a.setCreditCard(c);
			this.applicationService.save(a);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();

		this.checkExceptions(expected, caught);

	}

	protected void templateLink(final String string, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		super.unauthenticate();
		try {
			super.authenticate("immigrant2");
			this.applicationService.link("180702-MFVU24",
					super.getEntityId(string));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.unauthenticate();

		this.checkExceptions(expected, caught);
	}

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
		c.setCVVCode(237);
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
