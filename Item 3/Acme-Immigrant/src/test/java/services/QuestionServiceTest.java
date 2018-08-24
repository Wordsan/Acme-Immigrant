package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintDefinitionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import utilities.ForbbidenActionException;
import domain.Question;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class QuestionServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	QuestionService questionService;

	// Tests ------------------------------------------------------------------

	// RF 13.3 An actor who is authenticated as an officer must be able to ask
	// questions regarding an application that he or she has self-assigned
	@Test
	public void driver() {
		final Object testingData[][] = {
				{ "officer2", "application2", "pregunta", null },
				{ "officer1", "application2", "",
						ForbbidenActionException.class },
				{ "officer3", "application2", "pregunta",
						ForbbidenActionException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
	}

	// RF 12.2 An actor who is authenticated as an immigrant must be able to
	// answer the questions that an officer has posed on any of his or her
	// applications
	@Test
	public void driverAnswer() {
		final Object testingData[][] = {
				{ "immigrant2", "question2", "respuesta", null },
				{ "immigrant2", "question2", null,
						ConstraintDefinitionException.class },
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
			final Question q = this.questionService.create(
					super.getEntityId(application), statement);
			this.questionService.save(q);
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
