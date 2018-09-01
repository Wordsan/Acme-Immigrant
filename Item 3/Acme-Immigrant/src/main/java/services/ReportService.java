package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import security.LoginService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed Repository
	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private InvestigatorService investigatorService;

	@Autowired
	private ImmigrantService immigrantService;

	// Constructors
	public ReportService() {
		super();
	}

	// Simple CRUD methods

	public Report create(final int immigrantId) throws ObjectNotFoundException {
		final Report f = new Report();
		f.setInvestigator(this.investigatorService.getActorByUA(LoginService
				.getPrincipal()));
		f.setImmigrant(this.immigrantService.findOne(immigrantId));
		return f;
	}

	public Collection<Report> findAll() {
		return this.reportRepository.findAll();
	}

	public Report findOne(final int reportId) throws ObjectNotFoundException {
		final Report a = this.reportRepository.findOne(reportId);
		if (a == null)
			throw new ObjectNotFoundException();
		return a;
	}

	public Report save(final Report report) throws ForbbidenActionException {
		Report f;
		// Se comprueba que el que guarda el Report sea un Investigator y que
		// ademas sea el que está asignado al Immigrant del Report
		if (this.investigatorService.getActorByUA(LoginService.getPrincipal()) == null
				|| !this.investigatorService
						.getActorByUA(LoginService.getPrincipal())
						.getImmigrants().contains(report.getImmigrant()))
			throw new ForbbidenActionException();
		Assert.notNull(report);
		f = this.reportRepository.save(report);
		if (report.getId() == 0) {
			f.getImmigrant().getReports().add(f);
			this.immigrantService.save(f.getImmigrant());
		}
		return f;
	}
}
