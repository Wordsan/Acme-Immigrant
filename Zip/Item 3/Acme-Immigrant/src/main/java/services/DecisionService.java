package services;

import java.lang.instrument.IllegalClassFormatException;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DecisionRepository;
import security.LoginService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Application;
import domain.Decision;
import domain.Officer;

@Service
@Transactional
public class DecisionService {

	// Managed Repository
	@Autowired
	private DecisionRepository decisionRepository;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private OfficerService officerService;

	// Constructors
	public DecisionService() {
		super();
	}

	// Simple CRUD methods

	public Decision create() {
		final Decision f = new Decision();
		f.setMadeMoment(new Date(System.currentTimeMillis() - 1000));
		return f;
	}

	public Collection<Decision> findAll() {
		return this.decisionRepository.findAll();
	}

	public Decision findOne(final int decisionId)
			throws ObjectNotFoundException {
		final Decision a = this.decisionRepository.findOne(decisionId);
		if (a == null)
			throw new ObjectNotFoundException();
		return a;
	}

	public Decision save(final Decision decision) {
		Decision f;
		Assert.notNull(decision);
		decision.setMadeMoment(new Date(System.currentTimeMillis() - 1000));
		f = this.decisionRepository.save(decision);
		return f;
	}

	public int createNew(final int applicationId, final boolean accepted,
			final String reason) throws ForbbidenActionException,
			ObjectNotFoundException {
		// Se comprueba que si la Decision es rechazar la solicitud se aporte
		// una razon
		if (!accepted && reason == "")
			throw new IllegalArgumentException();
		final Decision d = this.create();
		Application a = null;
		try {
			final Officer o = this.officerService.getActorByUA(LoginService
					.getPrincipal());
			a = this.applicationService.findOne(applicationId);
			// Se comprueba que el que la crea sea un Officer
			if (o == null)
				throw new ForbbidenActionException();
			// Se comprueba que el Officer que crea la Decision tenga asignada
			// la Application
			if (a.getOfficer() == null || !a.getOfficer().equals(o))
				throw new ForbbidenActionException();
			// Por seguridad se comprueba que la Application esté abierta y no
			// tenga ya una Decision
			if (a.getStatus().equals("OPENED") || a.getDecision() != null)
				throw new ForbbidenActionException();
		} catch (final ForbbidenActionException f) {
			throw f;
		} catch (final ObjectNotFoundException e1) {
			throw e1;
		}
		d.setApplication(a);
		d.setComments(reason);
		d.setAccepted(accepted);
		try {
			final Decision decision = this.save(d);
			if (decision.isAccepted())
				a.setStatus("ACCEPTED");
			else
				a.setStatus("REJECTED");
			a.setDecision(decision);
			this.applicationService.save(a);
		} catch (final Exception e) {
			return 2;
		}
		return 0;
	}

	public void delete(final Decision d) throws ForbbidenActionException,
			IllegalClassFormatException {
		// Se comprueba que el que realiza la accion sea un Officer o un
		// Administrator
		if (!(this.officerService.getActorByUA(LoginService.getPrincipal()) != null || this.administratorService
				.getActorByUA(LoginService.getPrincipal()) != null))
			throw new ForbbidenActionException();
		d.getApplication().setDecision(null);
		this.applicationService.save(d.getApplication());
		this.decisionRepository.delete(d);
	}
}
