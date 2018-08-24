package services;

import java.lang.instrument.IllegalClassFormatException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.ResourceAccessException;

import repositories.ApplicationRepository;
import security.LoginService;
import security.UserAccount;
import utilities.ApplicationLinkException;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Actor;
import domain.Application;
import domain.ContactSection;
import domain.CreditCard;
import domain.Decision;
import domain.EducationSection;
import domain.Immigrant;
import domain.Officer;
import domain.PersonalSection;
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

	@Autowired
	private DecisionService decisionService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private PersonalSectionService personalSectionService;

	@Autowired
	private ContactSectionService contactSectionService;

	@Autowired
	private WorkSectionService workSectionService;

	@Autowired
	private EducationSectionService educationSectionService;

	@Autowired
	private SocialSectionService socialSectionService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private AdministratorService administratorService;

	// Constructors
	public ApplicationService() {
		super();
	}

	// Simple CRUD methods

	public Application create(final int visaId) throws ObjectNotFoundException {
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

	public Application findOne(final int applicationId)
			throws ForbbidenActionException, ObjectNotFoundException {
		Application a;
		a = this.applicationRepository.findOne(applicationId);
		final Actor actor = this.actorService.getActorByUA(LoginService
				.getPrincipal());
		if (a == null)
			throw new ObjectNotFoundException();
		if (!(a.getOfficer() != null && a.getOfficer().getId() == actor.getId()
				|| a.getOfficer() == null
				|| (a.getImmigrant().getId() == actor.getId()) || (a
				.getImmigrant().getInvestigator() != null && a.getImmigrant()
				.getInvestigator().getId() == actor.getId())))
			throw new ForbbidenActionException();
		else
			return a;

	}

	public Application save(final Application application)
			throws ForbbidenActionException, IllegalClassFormatException {
		Application f;
		final Actor actor = this.actorService.getActorByUA(LoginService
				.getPrincipal());
		if (application.getId() == 0) {
			if (this.immigrantService.getActorByUA(LoginService.getPrincipal()) == null)
				throw new ForbbidenActionException();
			application.setImmigrant(this.immigrantService
					.getActorByUA(LoginService.getPrincipal()));
			Application app;
			String ticker;
			do {
				ticker = this.createTicker();
				app = this.applicationRepository.getApplicationByTicker(ticker);
			} while (app != null);
			application.setTicker(ticker);
			final Collection<Application> as = this.immigrantService
					.getActorByUA(LoginService.getPrincipal())
					.getApplications();
			for (final Application a : as)
				if (a.getVisa().getId() == application.getVisa().getId())
					throw new ResourceAccessException(null);
		}

		Assert.notNull(application);
		if (application.getVisa().getPrice() != 0
				&& application.getCreditCard() == null)
			throw new IllegalArgumentException(
					"Se necesita una tarjeta de credito");
		else if (application.getCreditCard() != null) {
			final CreditCard c = application.getCreditCard();
			final Calendar cal = new GregorianCalendar();
			cal.set(Integer.parseInt("20" + c.getExpirationYear()),
					Integer.parseInt(c.getExpirationMonth()),
					cal.get(Calendar.DAY_OF_MONTH));
			cal.add(Calendar.MONTH, -3);
			if (cal.before(new GregorianCalendar()))
				throw new IllegalClassFormatException();
		}
		if (application.getId() == 0)
			application.setOpenedMoment(new Date(
					System.currentTimeMillis() - 1000));
		else if (!(application.getOfficer() != null
				&& application.getOfficer().getId() == actor.getId()
				|| (application.getImmigrant().getId() == actor.getId()) || (application
				.getImmigrant().getInvestigator() != null
				&& application.getImmigrant().getInvestigator().getId() == actor
						.getId() || this.administratorService
					.getActorByUA(LoginService.getPrincipal()) != null)))
			throw new ForbbidenActionException();
		f = this.applicationRepository.save(application);
		return f;
	}

	public void delete(final Application a) throws ForbbidenActionException,
			IllegalClassFormatException {
		final Actor actor = this.actorService.getActorByUA(LoginService
				.getPrincipal());
		if (!(a.getImmigrant().getId() == actor.getId() || this.administratorService
				.getActorByUA(LoginService.getPrincipal()) != null))
			throw new ForbbidenActionException();
		final Immigrant i = a.getImmigrant();
		final Collection<Application> appsI = i.getApplications();
		if (appsI != null) {
			i.getApplications().remove(a);
			this.immigrantService.save(i);
		}
		final Officer o = a.getOfficer();
		if (o != null) {
			final Collection<Application> appsO = o.getApplications();
			if (appsO != null) {
				o.getApplications().remove(a);
				this.officerService.save(o);
			}
		}
		final Decision d = a.getDecision();
		if (d != null) {
			a.setDecision(null);
			this.decisionService.delete(d);
		}
		final Collection<Question> questions = a.getQuestions();
		if (questions != null) {
			a.setQuestions(new ArrayList<Question>());
			for (final Question q : questions)
				this.questionService.delete(q);
		}
		final PersonalSection p = a.getPersonalSection();
		if (p != null) {
			a.setPersonalSection(null);
			this.personalSectionService.delete(p);
		}
		final Collection<ContactSection> cSections = a.getContactSections();
		if (cSections != null) {
			a.setContactSections(new ArrayList<ContactSection>());
			for (final ContactSection contactS : cSections)
				this.contactSectionService.delete(contactS);
		}
		final Collection<WorkSection> wSections = a.getWorkSections();
		if (wSections != null) {
			a.setWorkSections(new ArrayList<WorkSection>());
			for (final WorkSection workS : wSections)
				this.workSectionService.delete(workS);
		}
		final Collection<EducationSection> eSections = a.getEducationSections();
		if (eSections != null) {
			a.setEducationSections(new ArrayList<EducationSection>());
			for (final EducationSection educationS : eSections)
				this.educationSectionService.delete(educationS);
		}
		final Collection<SocialSection> sSections = a.getSocialSections();
		if (sSections != null) {
			a.setSocialSections(new ArrayList<SocialSection>());
			for (final SocialSection socialS : sSections)
				this.socialSectionService.delete(socialS);
		}
		this.applicationRepository.delete(a);
	}

	public boolean close(final int applicationId)
			throws ForbbidenActionException, ObjectNotFoundException,
			IllegalClassFormatException {
		final Application a = this.findOne(applicationId);
		if (!a.getStatus().equals("OPENED"))
			throw new IllegalArgumentException();
		try {
			a.setClosedMoment(new Date(System.currentTimeMillis()));
			a.setStatus("AWAITING");
			this.save(a);
			if (!a.getLinkedApplications().isEmpty())
				for (final Application app : a.getLinkedApplications()) {
					app.setClosedMoment(new Date(System.currentTimeMillis()));
					app.setStatus("AWAITING");
					this.save(app);
				}
		} catch (final IllegalArgumentException c) {
			return false;
		} catch (final ForbbidenActionException e) {
			throw e;
		}
		return true;

	}

	// Devuelve un numero por cada situacion
	// 0 -> Todo correcto
	// 1 -> Ya estan unidas
	// 2 -> Solicitudes de distinto pais, prohibido
	// 3 -> Error
	public String link(final String ticker, final int applicationId)
			throws ApplicationLinkException {
		try {
			final Application uno = this.findOne(applicationId);
			final Application dos = this.applicationRepository
					.getApplicationByTicker(ticker);
			if (uno.getLinkedApplications().contains(dos)
					&& dos.getLinkedApplications().contains(uno))
				throw new ApplicationLinkException("1");
			if (!uno.getVisa().getCountry().equals(dos.getVisa().getCountry()))
				throw new ApplicationLinkException("2");
			if (!uno.getLinkedApplications().contains(dos))
				uno.getLinkedApplications().add(dos);
			if (!dos.getLinkedApplications().contains(uno))
				dos.getLinkedApplications().add(uno);
			this.save(uno);
			try {
				this.save(dos);
			} catch (final ForbbidenActionException z) {
				return "0";
			}
		} catch (final ApplicationLinkException e) {
			throw e;
		} catch (final Exception e) {
			return "3";
		}
		return "0";
	}

	// Devuelve un numero por cada situacion
	// 0 -> Todo correcto
	// 1 -> Ya estan asignadas
	// 2 -> Error
	public Integer assign(final int applicationId)
			throws ObjectNotFoundException, ForbbidenActionException {
		final UserAccount ua = LoginService.getPrincipal();
		try {
			final Application a = this.findOne(applicationId);
			if (a == null)
				throw new ObjectNotFoundException();
			final Officer o = this.officerService.getActorByUA(ua);
			if (a.getOfficer() != null)
				throw new ForbbidenActionException();
			o.getApplications().add(a);
			a.setOfficer(o);
			if (!a.getLinkedApplications().isEmpty())
				for (final Application app : a.getLinkedApplications()) {
					if (app.getOfficer() != null)
						throw new ForbbidenActionException();
					o.getApplications().add(app);
					app.setOfficer(o);
					this.save(app);
				}
			this.save(a);
			this.officerService.save(o);
		} catch (final ObjectNotFoundException d) {
			throw d;
		} catch (final ForbbidenActionException f) {
			throw f;
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

	public Map<String, Double> timeStadistics() throws ForbbidenActionException {
		final Double[] statistics = this.applicationRepository.dateStadistics();
		final Map<String, Double> res = new HashMap<>();
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();

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
