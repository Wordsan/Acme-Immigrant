package services;

import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.ResourceAccessException;

import repositories.RequirementRepository;
import security.LoginService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Law;
import domain.Requirement;
import domain.Visa;

@Service
@Transactional
public class RequirementService {

	// Managed Repository
	@Autowired
	private RequirementRepository requirementRepository;

	@Autowired
	private LawService lawService;

	@Autowired
	private VisaService visaService;

	@Autowired
	private AdministratorService administratorService;

	// Constructors
	public RequirementService() {
		super();
	}

	// Simple CRUD methods

	public Requirement create() {
		final Requirement f = new Requirement();
		f.setVisas(new ArrayList<Visa>());
		return f;
	}

	public Collection<Requirement> findAll() {
		return this.requirementRepository.findAll();
	}

	public Requirement findOne(final int requirementId)
			throws ObjectNotFoundException {
		final Requirement a = this.requirementRepository.findOne(requirementId);
		if (a == null)
			throw new ObjectNotFoundException();
		return a;
	}

	public Requirement save(final Requirement requirement)
			throws ForbbidenActionException {
		Requirement f;
		// Se comprueba que sea un Administrator el que guarda el Requirement
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		Assert.notNull(requirement);
		f = this.requirementRepository.save(requirement);
		if (requirement.getId() == 0) {
			f.getLaw().getRequirements().add(f);
			this.lawService.save(f.getLaw());
		}
		return f;
	}

	public void delete(final Requirement requirement)
			throws ForbbidenActionException, IllegalClassFormatException {
		// Se comprueba que sea un Administrator el que elimina el Requirement
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		final Law l = requirement.getLaw();
		final Collection<Requirement> reqs = l.getRequirements();
		if (reqs != null) {
			l.getRequirements().remove(requirement);
			this.lawService.save(l);
		}
		final Collection<Visa> visas = requirement.getVisas();
		requirement.setVisas(new ArrayList<Visa>());
		if (visas != null)
			for (final Visa v : visas)
				this.visaService.delete(v);
		if (this.requirementRepository.findOne(requirement.getId()) != null)
			this.requirementRepository.delete(requirement);
	}

	public int addVisa(final int requirementId, final int visaId)
			throws ObjectNotFoundException, ForbbidenActionException {
		try {
			final Visa v = this.visaService.findOne(visaId);
			final Requirement r = this.findOne(requirementId);
			// Se comprueba que no exista ya la relaci�n entre Visa y
			// Requirement
			if (r.getVisas().contains(v))
				throw new ResourceAccessException(null);
			v.getRequirements().add(r);
			this.visaService.save(v);
			r.getVisas().add(v);
			this.save(r);
		} catch (final ObjectNotFoundException d) {
			throw d;
		} catch (final ResourceAccessException d) {
			throw d;
		} catch (final ForbbidenActionException f) {
			throw f;
		} catch (final Exception e) {
			return 1;
		}
		return 0;
	}

}
