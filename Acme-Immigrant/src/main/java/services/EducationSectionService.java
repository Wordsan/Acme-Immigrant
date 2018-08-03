package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationSectionRepository;
import domain.EducationSection;

@Service
@Transactional
public class EducationSectionService {

	// Managed Repository
	@Autowired
	private EducationSectionRepository educationSectionRepository;

	// Constructors
	public EducationSectionService() {
		super();
	}

	// Simple CRUD methods

	public EducationSection create() {
		final EducationSection f = new EducationSection();
		return f;
	}

	public Collection<EducationSection> findAll() {
		return this.educationSectionRepository.findAll();
	}

	public EducationSection findOne(final int educationSectionId) {
		return this.educationSectionRepository.findOne(educationSectionId);
	}

	public EducationSection save(final EducationSection educationSection) {
		EducationSection f;
		Assert.notNull(educationSection);
		f = this.educationSectionRepository.save(educationSection);
		return f;
	}

}
