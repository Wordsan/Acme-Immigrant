package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed Repository
	@Autowired
	private AdministratorRepository administratorRepository;

	@Autowired
	private ActorService actorService;

	// Constructors
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods

	public Administrator create() {
		final Administrator f = new Administrator();
		// f.setFolders(this.folderService.createNewFolder());
		final UserAccount ua = new UserAccount();
		final Authority a = new Authority();
		a.setAuthority("ADMIN");
		final List<Authority> as = new ArrayList<Authority>();
		as.add(a);
		ua.setAuthorities(as);
		f.setUserAccount(ua);
		return f;
	}

	public Collection<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final int administratorId)
			throws ObjectNotFoundException {
		final Administrator a = this.administratorRepository
				.findOne(administratorId);
		if (a == null)
			throw new ObjectNotFoundException();
		return a;
	}

	public Administrator save(final Administrator administrator)
			throws ForbbidenActionException {
		Administrator f;
		if (!administrator.getUserAccount().equals(LoginService.getPrincipal()))
			throw new ForbbidenActionException();
		Assert.notNull(administrator);
		if (this.actorService.usernameExists(administrator))
			return null;
		if (administrator.getUserAccount().getPassword().length() < 32)
			administrator.setUserAccount(this.actorService
					.encodePassword(administrator));
		f = this.administratorRepository.save(administrator);
		return f;
	}

	public Administrator getActorByUA(final UserAccount ua) {
		return this.administratorRepository
				.getAdministratorFromUAId(ua.getId());
	}
}
