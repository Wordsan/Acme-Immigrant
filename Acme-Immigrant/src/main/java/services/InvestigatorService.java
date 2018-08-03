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
import security.UserAccount;
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

	public Investigator findOne(final int investigatorId) {
		return this.investigatorRepository.findOne(investigatorId);
	}

	public Investigator save(final Investigator investigator) {
		Investigator f;
		Assert.notNull(investigator);
		if (this.actorService.usernameExists(investigator))
			return null;
		investigator.setUserAccount(this.actorService
				.encodePassword(investigator));
		f = this.investigatorRepository.save(investigator);
		return f;
	}

	public boolean assign(final int investigatorId, final int immigrantId,
			final UserAccount officerUA) {
		try {
			final Investigator inv = this.findOne(investigatorId);
			final Immigrant imm = this.immigrantService.findOne(immigrantId);
			final Officer o = this.officerService.getActorByUA(officerUA);
			boolean aux = false;
			for (final Application a : imm.getApplications()) {
				if (a.getOfficer().equals(o))
					aux = true;
				break;
			}
			if (aux) {
				inv.getImmigrants().add(imm);
				imm.setInvestigator(inv);
				this.save(inv);
				this.immigrantService.save(imm);
			} else
				return false;
		} catch (final Exception e) {
			return false;
		}
		return true;
	}

	public Investigator getActorByUA(final UserAccount ua) {
		return this.investigatorRepository.getInvestigatorFromUAId(ua.getId());
	}

	public Map<String, Double> immigrantsStadistics() {
		final Double[] statistics = this.investigatorRepository
				.immigrantsSizeStadistics();
		final Map<String, Double> res = new HashMap<>();

		res.put("AVG", statistics[0]);
		res.put("MIN", statistics[1]);
		res.put("MAX", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

}