package services;

import java.lang.instrument.IllegalClassFormatException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QuestionRepository;
import security.LoginService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Application;
import domain.Immigrant;
import domain.Question;

@Service
@Transactional
public class QuestionService {

	// Managed Repository
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ImmigrantService immigrantService;

	@Autowired
	private OfficerService officerService;

	@Autowired
	private AdministratorService administratorService;

	// Constructors
	public QuestionService() {
		super();
	}

	// Simple CRUD methods

	public Question create(final int applicationId, final String statement)
			throws ForbbidenActionException, ObjectNotFoundException {
		final Question f = new Question();
		final Application a = this.applicationService.findOne(applicationId);
		f.setApplication(a);
		f.setImmigrant(a.getImmigrant());
		f.setMadeMoment(new Date(System.currentTimeMillis() - 1000));
		f.setStatement(statement);
		return f;
	}

	public Collection<Question> findAll() {
		return this.questionRepository.findAll();
	}

	public Question findOne(final int questionId)
			throws ObjectNotFoundException {
		final Question a = this.questionRepository.findOne(questionId);
		if (a == null)
			throw new ObjectNotFoundException();
		return a;
	}

	public Question save(final Question question)
			throws ForbbidenActionException {
		Question f;
		Assert.notNull(question);
		if (!question.getImmigrant().getUserAccount()
				.equals(LoginService.getPrincipal())
				&& this.officerService
						.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		if (question.getId() == 0)
			question.setMadeMoment(new Date(System.currentTimeMillis() - 1000));
		f = this.questionRepository.save(question);
		return f;
	}

	public int answer(final int questionId, final String answer)
			throws ForbbidenActionException, ObjectNotFoundException {
		try {
			final Question q = this.findOne(questionId);
			q.setAnswer(answer);
			q.setAnswerMoment(new Date(System.currentTimeMillis() - 1000));
			if (!q.getImmigrant().getUserAccount()
					.equals(LoginService.getPrincipal()))
				throw new ForbbidenActionException();
			this.save(q);
		} catch (final ForbbidenActionException f) {
			throw f;
		} catch (final ObjectNotFoundException d) {
			throw d;
		} catch (final Exception e) {
			return 1;
		}

		return 0;
	}

	public List<Question> questionsByImmigrantUA() {
		return this.questionRepository
				.selectQuestionFromImmigrantUserAccount(LoginService
						.getPrincipal().getId());
	}

	public void delete(final Question q) throws ForbbidenActionException,
			IllegalClassFormatException {
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		final Immigrant i = q.getImmigrant();
		i.getQuestions().remove(q);
		this.immigrantService.save(i);
		final Application a = q.getApplication();
		final Collection<Question> qs = a.getQuestions();
		if (qs != null)
			a.getQuestions().remove(q);
		this.applicationService.save(a);
		this.questionRepository.delete(q);
	}
}
