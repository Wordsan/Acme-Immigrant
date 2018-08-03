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

import repositories.OfficerRepository;
import security.Authority;
import security.UserAccount;
import domain.Application;
import domain.Evaluation;
import domain.Officer;

@Service
@Transactional
public class OfficerService {

	// Managed Repository
	@Autowired
	private OfficerRepository officerRepository;

	@Autowired
	private ActorService actorService;

	// Constructors
	public OfficerService() {
		super();
	}

	// Simple CRUD methods

	public Officer create() {
		final Officer f = new Officer();
		f.setEvaluations(new ArrayList<Evaluation>());
		f.setApplications(new ArrayList<Application>());
		final UserAccount ua = new UserAccount();
		final Authority a = new Authority();
		a.setAuthority("OFFICER");
		final List<Authority> as = new ArrayList<Authority>();
		as.add(a);
		ua.setAuthorities(as);
		f.setUserAccount(ua);
		return f;
	}

	public Collection<Officer> findAll() {
		return this.officerRepository.findAll();
	}

	public Officer findOne(final int officerId) {
		return this.officerRepository.findOne(officerId);
	}

	public Officer save(final Officer officer) {
		Officer f;
		Assert.notNull(officer);
		if (this.actorService.usernameExists(officer))
			return null;
		officer.setUserAccount(this.actorService.encodePassword(officer));
		f = this.officerRepository.save(officer);
		return f;
	}

	public Officer getActorByUA(final UserAccount ua) {
		return this.officerRepository.getOfficerFromUAId(ua.getId());
	}

	public Map<String, Double> applicationsStadistics() {
		final Double[] statistics = this.officerRepository
				.applicationsSizeStadistics();
		final Map<String, Double> res = new HashMap<>();

		res.put("AVG", statistics[0]);
		res.put("MIN", statistics[1]);
		res.put("MAX", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

}
