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

	@Test
	public void driver() {
		final Object testingData[][] = {
				{ "investigator2", "immigrant2", null },
				{ "investigator3", "immigrant2", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void getReports() throws ObjectNotFoundException {
		super.authenticate("officer2");
		Assert.isTrue(this.immigrantService
				.findOne(super.getEntityId("immigrant2"))
				.getReports()
				.contains(
						this.reportService.findOne(super.getEntityId("report2"))));
	}

	// Ancillary methods ------------------------------------------------------

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
