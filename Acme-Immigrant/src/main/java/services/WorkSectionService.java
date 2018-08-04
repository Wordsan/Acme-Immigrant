package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WorkSectionRepository;
import domain.WorkSection;

@Service
@Transactional
public class WorkSectionService {

	// Managed Repository
	@Autowired
	private WorkSectionRepository workSectionRepository;

	// Constructors
	public WorkSectionService() {
		super();
	}

	// Simple CRUD methods

	public WorkSection create() {
		final WorkSection f = new WorkSection();
		return f;
	}

	public Collection<WorkSection> findAll() {
		return this.workSectionRepository.findAll();
	}

	public WorkSection findOne(final int workSectionId) {
		return this.workSectionRepository.findOne(workSectionId);
	}

	public WorkSection save(final WorkSection workSection) {
		WorkSection f;
		Assert.notNull(workSection);
		f = this.workSectionRepository.save(workSection);
		return f;
	}

	public void delete(final WorkSection workS) {
		this.workSectionRepository.delete(workS);
	}

}
