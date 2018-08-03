package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LawRepository;
import domain.Law;
import domain.Requirement;

@Service
@Transactional
public class LawService {

	// Managed Repository
	@Autowired
	private LawRepository lawRepository;

	@Autowired
	private RequirementService requirementService;

	// Constructors
	public LawService() {
		super();
	}

	// Simple CRUD methods

	public Law create() {
		final Law f = new Law();
		f.setCreatedAt(new Date(System.currentTimeMillis() - 1000));
		return f;
	}

	public Collection<Law> findAll() {
		return this.lawRepository.findAll();
	}

	public Law findOne(final int lawId) {
		return this.lawRepository.findOne(lawId);
	}

	public Law save(final Law law) {
		Law f;
		Assert.notNull(law);
		law.setCreatedAt(new Date(System.currentTimeMillis() - 1000));
		f = this.lawRepository.save(law);
		return f;
	}

	public void delete(final Law law) {
		final List<Law> laws = (List<Law>) law.getRelatedLaws();
		law.setRelatedLaws(null);
		for (final Law l : laws) {
			l.getRelatedLaws().remove(law);
			this.save(l);
		}
		final List<Requirement> reqs = (List<Requirement>) law
				.getRequirements();
		law.setRequirements(null);
		for (final Requirement r : reqs)
			this.requirementService.delete(r);
		this.lawRepository.delete(law);
	}

}
