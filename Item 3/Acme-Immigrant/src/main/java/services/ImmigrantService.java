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

import repositories.ImmigrantRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Application;
import domain.CreditCard;
import domain.Immigrant;
import domain.Question;
import domain.Report;

@Service
@Transactional
public class ImmigrantService {

	// Managed Repository
	@Autowired
	private ImmigrantRepository immigrantRepository;

	@Autowired
	private ActorService actorService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private InvestigatorService investigatorService;

	@Autowired
	private OfficerService officerService;

	// Constructors
	public ImmigrantService() {
		super();
	}

	// Simple CRUD methods

	public Immigrant create() {
		final Immigrant f = new Immigrant();
		f.setCreditCards(new ArrayList<CreditCard>());
		f.setQuestions(new ArrayList<Question>());
		f.setApplications(new ArrayList<Application>());
		f.setReports(new ArrayList<Report>());
		// f.setFolders(this.folderService.createNewFolder());
		final UserAccount ua = new UserAccount();
		final Authority a = new Authority();
		a.setAuthority("IMMIGRANT");
		final List<Authority> as = new ArrayList<Authority>();
		as.add(a);
		ua.setAuthorities(as);
		f.setUserAccount(ua);
		return f;
	}

	public Collection<Immigrant> findAll() {
		return this.immigrantRepository.findAll();
	}

	public Immigrant findOne(final int immigrantId)
			throws ObjectNotFoundException {
		final Immigrant a = this.immigrantRepository.findOne(immigrantId);
		if (a == null)
			throw new ObjectNotFoundException();
		return a;
	}

	public Immigrant save(final Immigrant immigrant)
			throws ForbbidenActionException {
		Immigrant f;
		try {
			if (immigrant.getId() == 0)
				if (LoginService.getPrincipal() != null)
					throw new ForbbidenActionException();
			if (!(immigrant.getUserAccount()
					.equals(LoginService.getPrincipal())
					|| this.administratorService.getActorByUA(LoginService
							.getPrincipal()) != null || this.officerService
						.getActorByUA(LoginService.getPrincipal()) != null))
				if (immigrant.getInvestigator() != null
						&& this.investigatorService.getActorByUA(LoginService
								.getPrincipal()) != null) {
					if (!this.investigatorService
							.getActorByUA(LoginService.getPrincipal())
							.getImmigrants().contains(immigrant))
						throw new ForbbidenActionException();
				} else
					throw new ForbbidenActionException();
		} catch (final IllegalArgumentException e) {

		}
		Assert.notNull(immigrant);
		if (this.actorService.usernameExists(immigrant))
			return null;
		if (immigrant.getUserAccount().getPassword().length() < 32)
			immigrant.setUserAccount(this.actorService
					.encodePassword(immigrant));
		f = this.immigrantRepository.save(immigrant);
		return f;
	}

	public Immigrant getActorByUA(final UserAccount ua) {
		return this.immigrantRepository.getImmigrantFromUAId(ua.getId());
	}

	public List<Immigrant> immigrantsNotInvestigated() {
		return this.immigrantRepository.immigrantsNotInvestigated(this
				.getActorByUA(LoginService.getPrincipal()).getId());
	}

	public Map<String, Double> applicationsStadistics()
			throws ForbbidenActionException {
		final Double[] statistics = this.immigrantRepository
				.applicationsSizeStadistics();
		final Map<String, Double> res = new HashMap<>();
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();

		res.put("AVG", statistics[0]);
		res.put("MIN", statistics[1]);
		res.put("MAX", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

	public List<Immigrant> immigrantsOfInvestigator() {
		return this.immigrantRepository.immigrantsOfInvestigator(LoginService
				.getPrincipal());
	}
}
