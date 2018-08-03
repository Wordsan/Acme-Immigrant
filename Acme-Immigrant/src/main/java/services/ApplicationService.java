package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.ContactSection;
import domain.EducationSection;
import domain.Officer;
import domain.Question;
import domain.SocialSection;
import domain.WorkSection;

@Service
@Transactional
public class ApplicationService {

	// Managed Repository
	@Autowired
	private ApplicationRepository applicationRepository;

	// Services
	@Autowired
	private OfficerService officerService;

	@Autowired
	private VisaService visaService;

	@Autowired
	private ImmigrantService immigrantService;

	// Constructors
	public ApplicationService() {
		super();
	}

	// Simple CRUD methods

	public Application create(final int visaId) {
		final Application f = new Application();
		f.setOpenedMoment(new Date(System.currentTimeMillis() - 1000));
		f.setVisa(this.visaService.findOne(visaId));
		f.setContactSections(new ArrayList<ContactSection>());
		f.setSocialSections(new ArrayList<SocialSection>());
		f.setEducationSections(new ArrayList<EducationSection>());
		f.setWorkSections(new ArrayList<WorkSection>());
		f.setQuestions(new ArrayList<Question>());
		f.setLinkedApplications(new ArrayList<Application>());
		return f;
	}

	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(final int applicationId) {
		return this.applicationRepository.findOne(applicationId);
	}

	public Application save(final Application application) {
		Application f;
		if (application.getId() == 0) {
			application.setImmigrant(this.immigrantService
					.getActorByUA(LoginService.getPrincipal()));
			application.setTicker(this.createTicker());
		}
		Assert.notNull(application);
		if (application.getVisa().getPrice() != "0"
				&& application.getCreditCard() == null)
			throw new IllegalArgumentException(
					"Se necesita una tarjeta de credito");
		if (application.getId() == 0)
			application.setOpenedMoment(new Date(
					System.currentTimeMillis() - 1000));
		f = this.applicationRepository.save(application);
		return f;
	}

	public void delete(final Application a) {
		this.applicationRepository.delete(a);
	}

	public boolean close(final int applicationId) {
		try {
			final Application a = this.findOne(applicationId);
			a.setClosedMoment(new Date(System.currentTimeMillis()));
			a.setStatus("AWAITING");
			this.save(a);
			if (!a.getLinkedApplications().isEmpty())
				for (final Application app : a.getLinkedApplications()) {
					app.setClosedMoment(new Date(System.currentTimeMillis()));
					app.setStatus("AWAITING");
					this.save(app);
				}
		} catch (final IllegalArgumentException e) {
			return false;
		}
		return true;

	}

	// Devuelve un numero por cada situacion
	// 0 -> Todo correcto
	// 1 -> Ya estan unidas
	// 2 -> Solicitudes de distinto pais, prohibido
	// 3 -> Error
	public int link(final String ticker, final int applicationId) {
		try {
			final Application uno = this.findOne(applicationId);
			final Application dos = this.applicationRepository
					.getApplicationByTicker(ticker);
			if (uno.getLinkedApplications().contains(dos)
					&& dos.getLinkedApplications().contains(uno))
				return 1;
			if (!uno.getVisa().getCountry().equals(dos.getVisa().getCountry()))
				return 2;
			if (!uno.getLinkedApplications().contains(dos))
				uno.getLinkedApplications().add(dos);
			if (!dos.getLinkedApplications().contains(uno))
				dos.getLinkedApplications().add(uno);
			this.save(uno);
			this.save(dos);
		} catch (final Exception e) {
			return 3;
		}
		return 0;
	}

	// Devuelve un numero por cada situacion
	// 0 -> Todo correcto
	// 1 -> Ya estan asignadas
	// 2 -> Error
	public int assign(final int applicationId) {
		final UserAccount ua = LoginService.getPrincipal();
		try {
			final Application a = this.findOne(applicationId);
			final Officer o = this.officerService.getActorByUA(ua);
			if (a.getOfficer() != null)
				return 2;
			o.getApplications().add(a);
			a.setOfficer(o);
			if (!a.getLinkedApplications().isEmpty())
				for (final Application app : a.getLinkedApplications()) {
					if (app.getOfficer() != null)
						return 2;
					o.getApplications().add(app);
					app.setOfficer(o);
					this.applicationRepository.save(app);
				}
			this.applicationRepository.save(a);
			this.officerService.save(o);
		} catch (final Exception e) {
			return 1;
		}
		return 0;
	}

	public List<Application> applicationsNotClosed() {
		return this.applicationRepository
				.selectApplicationsByImmigrantUANotClosed(LoginService
						.getPrincipal().getId());
	}

	public List<Application> applicationsFromStatus(final String status) {
		return this.applicationRepository.selectApplicationFromStatus(
				LoginService.getPrincipal().getId(), status);
	}

	public List<Application> applicationsFromOfficerStatus(final String status) {
		return this.applicationRepository.applicationFromOfficerStatus(
				LoginService.getPrincipal().getId(), status);
	}

	public List<Application> applicationsFromOfficer() {
		return this.applicationRepository.applicationFromOfficer(LoginService
				.getPrincipal().getId());
	}

	public List<Application> applicationsNotAssigned() {
		return this.applicationRepository.selectApplicationsNotAssigned();
	}

	// Falta la de los dias de diferencia

	public Map<String, Double> timeStadistics() {
		final Double[] statistics = this.applicationRepository.dateStadistics();
		final Map<String, Double> res = new HashMap<>();

		res.put("AVG", statistics[0]);
		res.put("MIN", statistics[1]);
		res.put("MAX", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

	public Map<String, Double> timeStadisticsByVisa(final Integer visaId) {
		final Double[] statistics = this.applicationRepository
				.dateDifferenceByAVisa(visaId);
		final Map<String, Double> res = new HashMap<>();

		res.put("AVG", statistics[0]);
		res.put("STD", statistics[1]);

		return res;

	}

	public String createTicker() {
		String result = "";
		final String alphas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final Random rnd = new Random();

		final DateFormat df = new SimpleDateFormat("YYMMdd");
		final Date date = new Date();

		result += df.format(date);
		int a, b, c, d, e;
		a = rnd.nextInt(alphas.length());
		b = rnd.nextInt(alphas.length());
		c = rnd.nextInt(alphas.length());
		d = rnd.nextInt(alphas.length());
		e = rnd.nextInt(100);
		result += "-" + alphas.charAt(a) + alphas.charAt(b) + alphas.charAt(c)
				+ alphas.charAt(d) + String.valueOf(e);

		return result;
	}
}
