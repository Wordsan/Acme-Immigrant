package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalSectionRepository;
import domain.PersonalSection;

@Service
@Transactional
public class PersonalSectionService {

	// Managed Repository
	@Autowired
	private PersonalSectionRepository personalSectionRepository;

	// Constructors
	public PersonalSectionService() {
		super();
	}

	// Simple CRUD methods

	public PersonalSection create() {
		final PersonalSection f = new PersonalSection();
		return f;
	}

	public Collection<PersonalSection> findAll() {
		return this.personalSectionRepository.findAll();
	}

	public PersonalSection findOne(final int personalSectionId) {
		return this.personalSectionRepository.findOne(personalSectionId);
	}

	public PersonalSection save(final PersonalSection personalSection) {
		PersonalSection f;
		Assert.notNull(personalSection);
		f = this.personalSectionRepository.save(personalSection);
		return f;
	}

}
