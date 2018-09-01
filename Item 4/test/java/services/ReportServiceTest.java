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
import domain.Report;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReportServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	ReportService reportService;

	@Autowired
	ImmigrantService immigrantService;

	// Tests ------------------------------------------------------------------

	// RF An actor who is authenticated as an investigator must be able to write
	// reports about the immigrants that he or she has assigned
	// PD: No aparece el requisito pero se supone necesario tambien
	@Test
	public void driver() {
		final Object testingData[][] = {
				{ "investigator2", "immigrant2", null },
				{ "investigator3", "immigrant2", ForbbidenActionException.class },
				{ "investigator1", "immigrant2", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// RF 13.6 An actor who is authenticated as an officer must be able to read
	// the reports written by the investigators that he or she's requested to
	// investigate an immigrant
	@Test
	public void driverGet() {
		final Object testingData[][] = { { "immigrant2", "report1", null },
				{ "immigrant1", "report2", IllegalArgumentException.class },
				{ "immigrant3", "report2", IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateGet((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateGet(final String immigrant, final String report,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		super.authenticate("officer1");
		try {
			Assert.isTrue(this.immigrantService
					.findOne(super.getEntityId(immigrant))
					.getReports()
					.contains(
							this.reportService.findOne(super
									.getEntityId(report))));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void template(final String investigator, final String immigrant,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(investigator);
			final Report r = this.reportService.create(super
					.getEntityId(immigrant));
			r.setText("texto");
			this.reportService.save(r);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
