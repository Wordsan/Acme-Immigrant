package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ContactSectionRepository;
import security.LoginService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.ContactSection;

@Service
@Transactional
public class ContactSectionService {

	// Managed Repository
	@Autowired
	private ContactSectionRepository contactRepository;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ImmigrantService immigrantService;

	// Constructors
	public ContactSectionService() {
		super();
	}

	// Simple CRUD methods

	public ContactSection create() {
		final ContactSection f = new ContactSection();
		return f;
	}

	public Collection<ContactSection> findAll() {
		return this.contactRepository.findAll();
	}

	public ContactSection findOne(final int contactId)
			throws ObjectNotFoundException, ForbbidenActionException {

		final ContactSection a = this.contactRepository.findOne(contactId);
		if (a == null)
			throw new ObjectNotFoundException();
		// Se comprueba que el que realiza la accion es un Immigrant
		if (this.immigrantService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		return a;
	}

	public ContactSection save(final ContactSection contact)
			throws ForbbidenActionException {
		ContactSection f;
		Assert.notNull(contact);
		// Se comprueba que el que realiza la accion es un Immigrant
		if (this.immigrantService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		f = this.contactRepository.save(contact);
		return f;
	}

	public void delete(final ContactSection contactS)
			throws ForbbidenActionException {
		// Se comprueba que el que realiza la accion es un Immigrant
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		this.contactRepository.delete(contactS);
	}

}
