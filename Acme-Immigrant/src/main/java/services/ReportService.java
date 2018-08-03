package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import security.LoginService;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed Repository
	@Autowired
	private ReportRepository reportRepository;

	// Services
	@Autowired
	private OfficerService officerService;

	// Constructors
	public ReportService() {
		super();
	}

	// Simple CRUD methods

	public Report create() {
		final Report f = new Report();
		return f;
	}

	public Collection<Report> findAll() {
		return this.reportRepository.findAll();
	}

	public Report findOne(final int reportId) {
		return this.reportRepository.findOne(reportId);
	}

	public Report save(final Report report) {
		Report f;
		Assert.notNull(report);
		f = this.reportRepository.save(report);
		return f;
	}

	public List<Report> reportsFromImmigrant(final Integer immigrantId) {
		return this.reportRepository.selectReportsFromImmigrantId(immigrantId);
	}

	public List<Report> reportsOfAnOfficer() {
		return this.reportRepository.reportsOfAnOfficer(this.officerService
				.getActorByUA(LoginService.getPrincipal()).getId());
	}
}
