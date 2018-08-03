package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EvaluationRepository;
import domain.Evaluation;

@Service
@Transactional
public class EvaluationService {

	// Managed Repository
	@Autowired
	private EvaluationRepository evaluationRepository;

	// Constructors
	public EvaluationService() {
		super();
	}

	// Simple CRUD methods

	public Evaluation create() {
		final Evaluation f = new Evaluation();
		f.setDateOf(new Date(System.currentTimeMillis() - 1000));
		return f;
	}

	public Collection<Evaluation> findAll() {
		return this.evaluationRepository.findAll();
	}

	public Evaluation findOne(final int evaluationId) {
		return this.evaluationRepository.findOne(evaluationId);
	}

	public Evaluation save(final Evaluation evaluation) {
		Evaluation f;
		Assert.notNull(evaluation);
		evaluation.setDateOf(new Date(System.currentTimeMillis() - 1000));
		f = this.evaluationRepository.save(evaluation);
		return f;
	}
}
