package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DecisionRepository;
import domain.Application;
import domain.Decision;

@Service
@Transactional
public class DecisionService {

	// Managed Repository
	@Autowired
	private DecisionRepository decisionRepository;

	@Autowired
	private ApplicationService applicationService;

	// Constructors
	public DecisionService() {
		super();
	}

	// Simple CRUD methods

	public Decision create() {
		final Decision f = new Decision();
		f.setMadeMoment(new Date(System.currentTimeMillis() - 1000));
		return f;
	}

	public Collection<Decision> findAll() {
		return this.decisionRepository.findAll();
	}

	public Decision findOne(final int decisionId) {
		return this.decisionRepository.findOne(decisionId);
	}

	public Decision save(final Decision decision) {
		Decision f;
		Assert.notNull(decision);
		decision.setMadeMoment(new Date(System.currentTimeMillis() - 1000));
		f = this.decisionRepository.save(decision);
		return f;
	}

	public int createNew(final int applicationId, final boolean accepted,
			final String reason) {
		if (!accepted && reason == "")
			return 1;
		final Decision d = this.create();
		final Application a = this.applicationService.findOne(applicationId);
		d.setApplication(a);
		d.setComments(reason);
		d.setAccepted(accepted);
		try {
			final Decision decision = this.save(d);
			if (decision.isAccepted())
				a.setStatus("ACCEPTED");
			else
				a.setStatus("REJECTED");
			a.setDecision(decision);
			this.applicationService.save(a);
		} catch (final Exception e) {
			return 2;
		}
		return 0;
	}
}
