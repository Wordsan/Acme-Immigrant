package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LawRepository;
import domain.Law;
import domain.Requirement;
import domain.Visa;

@Service
@Transactional
public class LawService {

	// Managed Repository
	@Autowired
	private LawRepository lawRepository;

	@Autowired
	private RequirementService requirementService;

	@Autowired
	private VisaService visaService;

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
		if (law.getId() == 0)
			law.setCreatedAt(new Date(System.currentTimeMillis() - 1000));
		f = this.lawRepository.save(law);
		return f;
	}

	public void delete(final Law law) {
		final Collection<Law> laws = law.getRelatedLaws();
		law.setRelatedLaws(new ArrayList<Law>());
		if (laws != null)
			for (final Law l : laws) {
				l.getRelatedLaws().remove(law);
				this.save(l);
			}
		final Collection<Requirement> reqs = law.getRequirements();
		law.setRequirements(new ArrayList<Requirement>());
		if (reqs != null)
			for (final Requirement r : reqs)
				this.requirementService.delete(r);
		final Law l = this.lawRepository.findOne(law.getId());
		if (l != null)
			this.lawRepository.delete(l);
	}

	public int abrogate(final int lawId) {
		try {
			final Law law = this.findOne(lawId);
			law.setAbrogatedAt(new Date(System.currentTimeMillis()));
			this.save(law);
			final Collection<Requirement> reqs = law.getRequirements();
			for (final Requirement r : reqs) {
				r.setAbrogated(true);
				this.requirementService.save(r);
				for (final Visa v : r.getVisas()) {
					v.setAbrogated(true);
					this.visaService.save(v);
				}
			}
		} catch (final Exception e) {
			return 1;
		}
		return 0;
	}

}
