package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;

@Service
@Transactional
public class ActorService {
	private static Md5PasswordEncoder encoder = new Md5PasswordEncoder();

	// Managed Repository
	@Autowired
	private ActorRepository actorRepository;

	@Autowired
	private UserAccountRepository uaRepository;

	// Constructors
	public ActorService() {
		super();
	}

	// Simple CRUD methods

	public Actor create() {
		final Actor f = new Actor();
		// f.setFolders(this.folderService.createNewFolder());
		return f;
	}

	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor findOne(final int actorId) {
		return this.actorRepository.findOne(actorId);
	}

	public Actor save(final Actor actor) {
		Actor f;
		Assert.notNull(actor);
		if (this.usernameExists(actor))
			return null;
		if (actor.getUserAccount().getPassword().length() < 32)
			actor.setUserAccount(this.encodePassword(actor));
		f = this.actorRepository.save(actor);
		return f;
	}

	public Actor getActorByUA(final UserAccount ua) {
		return this.actorRepository.getActorFromUAId(ua.getId());
	}

	public UserAccount encodePassword(final Actor actor) {
		actor.getUserAccount().setPassword(
				ActorService.encoder.encodePassword(actor.getUserAccount()
						.getPassword(), null));
		final UserAccount a = actor.getUserAccount();
		return this.uaRepository.save(a);
	}

	public boolean usernameExists(final Actor actor) {
		final boolean res = false;
		Actor a = new Actor();
		try {
			a = this.actorRepository.getActorFromUsername(actor
					.getUserAccount().getUsername());
		} catch (final org.springframework.dao.DataIntegrityViolationException e) {

		}
		if (a != null)
			if (a.getId() != actor.getId())
				return true;
		return res;
	}

}