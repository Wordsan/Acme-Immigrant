package services;

import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LawRepository;
import security.LoginService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Law;
import domain.Requirement;
import domain.Visa;

@Service
@Transactional
public class LawService {

	// Managed Repository
	@Autowired
	private LawRepository lawRepository;

	@Autowired
	private RequirementService requirementService;

	@Autowired
	private VisaService visaService;

	@Autowired
	private AdministratorService administratorService;

	// Constructors
	public LawService() {
		super();
	}

	// Simple CRUD methods

	public Law create() {
		final Law f = new Law();
		f.setCreatedAt(new Date(System.currentTimeMillis() - 1000));
		f.setRelatedLaws(new ArrayList<Law>());
		f.setRequirements(new ArrayList<Requirement>());
		return f;
	}

	public Collection<Law> findAll() throws ForbbidenActionException {
		// El �nico que puede listar las leyes es el Administrator
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		return this.lawRepository.findAll();
	}

	public Law findOne(final int lawId) throws ObjectNotFoundException {
		final Law a = this.lawRepository.findOne(lawId);
		if (a == null)
			throw new ObjectNotFoundException();
		return a;
	}

	public Law save(final Law law) throws ForbbidenActionException {
		// Se comprueba que el que la modifica sea un Administrator
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		Law f;
		Assert.notNull(law);
		if (law.getId() == 0)
			law.setCreatedAt(new Date(System.currentTimeMillis() - 1000));
		f = this.lawRepository.save(law);
		return f;
	}

	public void delete(final Law law) throws ForbbidenActionException,
			IllegalClassFormatException {
		// Se comprueba que el que la elimina sea un Administrator
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		/*
		 * A continuaci�n se eliminan las relaciones con los objetos
		 * relacionados o los propios objetos
		 */
		final Collection<Law> laws = law.getRelatedLaws();
		law.setRelatedLaws(new ArrayList<Law>());
		if (laws != null)
			for (final Law l : laws) {
				l.getRelatedLaws().remove(law);
				this.save(l);
			}
		final Collection<Requirement> reqs = law.getRequirements();
		law.setRequirements(new ArrayList<Requirement>());
		if (reqs != null)
			for (final Requirement r : reqs)
				this.requirementService.delete(r);
		final Law l = this.lawRepository.findOne(law.getId());
		if (l != null)
			this.lawRepository.delete(l);
	}

	// Devuelve 1 si ha ocurrido un error inesperado
	// Devuelve 0 si se ha anulado correctamente
	public int abrogate(final int lawId) throws ForbbidenActionException {
		// Se comprueba que el que quiere anular la Law sea un Administrator
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		try {
			final Law law = this.findOne(lawId);
			law.setAbrogatedAt(new Date(System.currentTimeMillis()));
			this.save(law);
			// Se propaga la anulaci�n por los Requirements y Visas
			final Collection<Requirement> reqs = law.getRequirements();
			for (final Requirement r : reqs) {
				r.setAbrogated(true);
				this.requirementService.save(r);
				for (final Visa v : r.getVisas()) {
					v.setAbrogated(true);
					this.visaService.save(v);
				}
			}
		} catch (final Exception e) {
			return 1;
		}
		return 0;
	}

}
