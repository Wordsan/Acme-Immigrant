package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalSectionRepository;
import security.LoginService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.PersonalSection;

@Service
@Transactional
public class PersonalSectionService {

	// Managed Repository
	@Autowired
	private PersonalSectionRepository personalSectionRepository;

	@Autowired
	private ImmigrantService immigrantService;

	@Autowired
	private AdministratorService administratorService;

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

	public PersonalSection findOne(final int personalSectionId)
			throws ObjectNotFoundException, ForbbidenActionException {
		final PersonalSection a = this.personalSectionRepository
				.findOne(personalSectionId);
		if (a == null)
			throw new ObjectNotFoundException();
		if (this.immigrantService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		return a;
	}

	public PersonalSection save(final PersonalSection personalSection)
			throws ForbbidenActionException {
		PersonalSection f;
		Assert.notNull(personalSection);
		if (this.immigrantService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		f = this.personalSectionRepository.save(personalSection);
		return f;
	}

	public void delete(final PersonalSection p) throws ForbbidenActionException {
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		this.personalSectionRepository.delete(p);
	}

}
