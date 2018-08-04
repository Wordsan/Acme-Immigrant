package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ContactSectionRepository;
import domain.ContactSection;

@Service
@Transactional
public class ContactSectionService {

	// Managed Repository
	@Autowired
	private ContactSectionRepository contactRepository;

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

	public ContactSection findOne(final int contactId) {
		return this.contactRepository.findOne(contactId);
	}

	public ContactSection save(final ContactSection contact) {
		ContactSection f;
		Assert.notNull(contact);
		f = this.contactRepository.save(contact);
		return f;
	}

	public void delete(final ContactSection contactS) {
		this.contactRepository.delete(contactS);
	}

}
