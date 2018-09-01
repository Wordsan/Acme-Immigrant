package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InvestigatorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Application;
import domain.Immigrant;
import domain.Investigator;
import domain.Officer;
import domain.Report;

@Service
@Transactional
public class InvestigatorService {

	// Managed Repository
	@Autowired
	private InvestigatorRepository investigatorRepository;

	@Autowired
	private ActorService actorService;

	@Autowired
	private ImmigrantService immigrantService;

	@Autowired
	private OfficerService officerService;

	@Autowired
	private AdministratorService administratorService;

	// Constructors
	public InvestigatorService() {
		super();
	}

	// Simple CRUD methods

	public Investigator create() {
		final Investigator f = new Investigator();
		f.setImmigrants(new ArrayList<Immigrant>());
		f.setReports(new ArrayList<Report>());
		// f.setFolders(this.folderService.createNewFolder());
		final UserAccount ua = new UserAccount();
		final Authority a = new Authority();
		a.setAuthority("INVESTIGATOR");
		final List<Authority> as = new ArrayList<Authority>();
		as.add(a);
		ua.setAuthorities(as);
		f.setUserAccount(ua);
		return f;
	}

	public Collection<Investigator> findAll() {
		return this.investigatorRepository.findAll();
	}

	public Investigator findOne(final int investigatorId)
			throws ObjectNotFoundException {
		final Investigator a = this.investigatorRepository
				.findOne(investigatorId);
		if (a == null)
			throw new ObjectNotFoundException();
		return a;
	}

	public Investigator save(final Investigator investigator)
			throws ForbbidenActionException {
		Investigator f;
		try {
			if (investigator.getId() == 0)
				if (!(this.administratorService.getActorByUA(LoginService
						.getPrincipal()) != null))
					throw new ForbbidenActionException();
			if (!(investigator.getUserAccount().equals(
					LoginService.getPrincipal())
					|| this.administratorService.getActorByUA(LoginService
							.getPrincipal()) != null || this.officerService
						.getActorByUA(LoginService.getPrincipal()) != null))
				throw new ForbbidenActionException();
		} catch (final IllegalArgumentException e) {

		}
		Assert.notNull(investigator);
		if (this.actorService.usernameExists(investigator))
			return null;
		/*
		 * Esta comprobación se hace porque cuando se hacía save porque se
		 * modificaban otros objetos relacionados con el usuario siempre
		 * codificaba la contraseña actual, por lo que se cambiaba sin ser
		 * modificada por el usuario
		 */
		if (investigator.getUserAccount().getPassword().length() < 32)
			investigator.setUserAccount(this.actorService
					.encodePassword(investigator));
		f = this.investigatorRepository.save(investigator);
		return f;
	}

	public boolean assign(final int investigatorId, final int immigrantId,
			final UserAccount officerUA) throws ForbbidenActionException {
		try {
			final Investigator inv = this.findOne(investigatorId);
			final Immigrant imm = this.immigrantService.findOne(immigrantId);
			final Officer o = this.officerService.getActorByUA(officerUA);
			boolean aux = false;
			for (final Application a : imm.getApplications())
				if (a.getOfficer() != null) {
					if (a.getOfficer().equals(o))
						aux = true;
					break;
				}
			if (aux && imm.getInvestigator() == null) {
				inv.getImmigrants().add(imm);
				imm.setInvestigator(inv);
				this.save(inv);
				this.immigrantService.save(imm);
			} else
				throw new ForbbidenActionException();
		} catch (final ForbbidenActionException e) {
			throw e;
		} catch (final Exception e) {
			return false;
		}
		return true;
	}

	public Investigator getActorByUA(final UserAccount ua) {
		return this.investigatorRepository.getInvestigatorFromUAId(ua.getId());
	}

	public Map<String, Double> immigrantsStadistics()
			throws ForbbidenActionException {
		final Double[] statistics = this.investigatorRepository
				.immigrantsSizeStadistics();
		final Map<String, Double> res = new HashMap<>();
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();

		res.put("AVG", statistics[0]);
		res.put("MIN", statistics[1]);
		res.put("MAX", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

}