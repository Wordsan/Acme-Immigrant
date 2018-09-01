package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationSectionRepository;
import security.LoginService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.EducationSection;

@Service
@Transactional
public class EducationSectionService {

	// Managed Repository
	@Autowired
	private EducationSectionRepository educationSectionRepository;

	@Autowired
	private ImmigrantService immigrantService;

	@Autowired
	private AdministratorService administratorService;

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

	public EducationSection findOne(final int educationSectionId)
			throws ObjectNotFoundException, ForbbidenActionException {
		final EducationSection a = this.educationSectionRepository
				.findOne(educationSectionId);
		if (a == null)
			throw new ObjectNotFoundException();
		// Se comprueba que el que realiza la accion es un Immigrant
		if (this.immigrantService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		return a;
	}

	public EducationSection save(final EducationSection educationSection)
			throws ForbbidenActionException {
		EducationSection f;
		Assert.notNull(educationSection);
		// Se comprueba que el que realiza la accion es un Immigrant
		if (this.immigrantService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		f = this.educationSectionRepository.save(educationSection);
		return f;
	}

	public void delete(final EducationSection educationS)
			throws ForbbidenActionException {
		// Se comprueba que el que realiza la accion es un Immigrant
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		this.educationSectionRepository.delete(educationS);
	}

}
