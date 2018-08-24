package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import utilities.ForbbidenActionException;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class QuestionServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	QuestionService questionService;

	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		final Object testingData[][] = {
				{ "officer2", "application2", "pregunta", null },
				{ "officer1", "application2", "",
						ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}

	public void driverAnswer() {
		final Object testingData[][] = {
				{ "immigrant2", "question2", "respuesta", null },
				{ "immigrant1", "question2", "", ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateAnswer((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String user, final String application,
			final String statement, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			this.questionService.create(super.getEntityId(application),
					statement);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateAnswer(final String user, final String question,
			final String statement, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);
			this.questionService.answer(super.getEntityId(question), statement);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
