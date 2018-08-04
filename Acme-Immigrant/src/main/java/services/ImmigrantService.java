
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
	private ImmigrantRepository	immigrantRepository;

	@Autowired
	private ActorService		actorService;


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

	public Immigrant findOne(final int immigrantId) {
		return this.immigrantRepository.findOne(immigrantId);
	}

	public Immigrant save(final Immigrant immigrant) {
		Immigrant f;
		Assert.notNull(immigrant);
		if (this.actorService.usernameExists(immigrant))
			return null;
		immigrant.setUserAccount(this.actorService.encodePassword(immigrant));
		f = this.immigrantRepository.save(immigrant);
		return f;
	}

	public Immigrant getActorByUA(final UserAccount ua) {
		return this.immigrantRepository.getImmigrantFromUAId(ua.getId());
	}

	public List<Immigrant> immigrantsNotInvestigated() {
		return this.immigrantRepository.immigrantsNotInvestigated(this.getActorByUA(LoginService.getPrincipal()).getId());
	}

	public Map<String, Double> applicationsStadistics() {
		final Double[] statistics = this.immigrantRepository.applicationsSizeStadistics();
		final Map<String, Double> res = new HashMap<>();

		res.put("AVG", statistics[0]);
		res.put("MIN", statistics[1]);
		res.put("MAX", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}
}
