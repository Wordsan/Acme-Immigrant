package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequirementRepository;
import domain.Law;
import domain.Requirement;
import domain.Visa;

@Service
@Transactional
public class RequirementService {

	// Managed Repository
	@Autowired
	private RequirementRepository requirementRepository;

	@Autowired
	private LawService lawService;

	@Autowired
	private VisaService visaService;

	// Constructors
	public RequirementService() {
		super();
	}

	// Simple CRUD methods

	public Requirement create() {
		final Requirement f = new Requirement();
		return f;
	}

	public Collection<Requirement> findAll() {
		return this.requirementRepository.findAll();
	}

	public Requirement findOne(final int requirementId) {
		return this.requirementRepository.findOne(requirementId);
	}

	public Requirement save(final Requirement requirement) {
		Requirement f;
		Assert.notNull(requirement);
		f = this.requirementRepository.save(requirement);
		return f;
	}

	public void delete(final Requirement requirement) {
		final Law l = requirement.getLaw();
		l.getRequirements().remove(requirement);
		this.lawService.save(l);
		final List<Visa> visas = (List<Visa>) requirement.getVisas();
		requirement.setVisas(null);
		for (final Visa v : visas)
			this.visaService.delete(v);
		this.requirementRepository.delete(requirement);
	}

}
