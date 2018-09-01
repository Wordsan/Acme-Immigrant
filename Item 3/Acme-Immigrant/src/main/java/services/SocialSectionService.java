package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialSectionRepository;
import security.LoginService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.SocialSection;

@Service
@Transactional
public class SocialSectionService {

	// Managed Repository
	@Autowired
	private SocialSectionRepository socialSectionRepository;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ImmigrantService immigrantService;

	// Constructors
	public SocialSectionService() {
		super();
	}

	// Simple CRUD methods

	public SocialSection create() {
		final SocialSection f = new SocialSection();
		return f;
	}

	public Collection<SocialSection> findAll() {
		return this.socialSectionRepository.findAll();
	}

	public SocialSection findOne(final int socialSectionId)
			throws ObjectNotFoundException, ForbbidenActionException {
		final SocialSection a = this.socialSectionRepository
				.findOne(socialSectionId);
		if (a == null)
			throw new ObjectNotFoundException();
		// Se comprueba que el que realiza la accion es un Immigrant
		if (this.immigrantService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		return a;
	}

	public SocialSection save(final SocialSection socialSection)
			throws ForbbidenActionException {
		SocialSection f;
		Assert.notNull(socialSection);
		// Se comprueba que el que realiza la accion es un Immigrant
		if (this.immigrantService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		f = this.socialSectionRepository.save(socialSection);
		return f;
	}

	public void delete(final SocialSection socialS)
			throws ForbbidenActionException {
		// Se comprueba que el que realiza la accion es un Immigrant
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		this.socialSectionRepository.delete(socialS);
	}

}
