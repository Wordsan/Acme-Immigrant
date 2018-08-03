package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QuestionRepository;
import security.LoginService;
import domain.Application;
import domain.Question;

@Service
@Transactional
public class QuestionService {

	// Managed Repository
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private ApplicationService applicationService;

	// Constructors
	public QuestionService() {
		super();
	}

	// Simple CRUD methods

	public Question create(final int applicationId, final String statement) {
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

	public Question findOne(final int questionId) {
		return this.questionRepository.findOne(questionId);
	}

	public Question save(final Question question) {
		Question f;
		Assert.notNull(question);
		if (question.getId() == 0)
			question.setMadeMoment(new Date(System.currentTimeMillis() - 1000));
		f = this.questionRepository.save(question);
		return f;
	}

	public int answer(final int questionId, final String answer) {
		try {
			final Question q = this.findOne(questionId);
			q.setAnswer(answer);
			q.setAnswerMoment(new Date(System.currentTimeMillis() - 1000));
			this.save(q);
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
}
