package services;

import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.VisaRepository;
import security.LoginService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Application;
import domain.Category;
import domain.Country;
import domain.Requirement;
import domain.Visa;

@Service
@Transactional
public class VisaService {

	// Managed Repository
	@Autowired
	private VisaRepository visaRepository;

	@Autowired
	private RequirementService requirementService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private AdministratorService administratorService;

	// Constructors
	public VisaService() {
		super();
	}

	// Simple CRUD methods

	public Visa create() {
		final Visa f = new Visa();
		f.setApplications(new ArrayList<Application>());
		f.setRequirements(new ArrayList<Requirement>());
		return f;
	}

	public Collection<Visa> findAll() throws ForbbidenActionException {
		// El unico que puede listar todos los Visados del sistema es el
		// Administrator, el resto lista las Visas disponibles
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		return this.visaRepository.findAll();
	}

	public Visa findOne(final int visaId) throws ObjectNotFoundException {
		final Visa a = this.visaRepository.findOne(visaId);
		if (a == null)
			throw new ObjectNotFoundException();
		return a;
	}

	public Visa save(final Visa visa) throws ForbbidenActionException {
		Visa f;
		// Se comprueba que sea un Administrator el que guarda la Visa
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		Assert.notNull(visa);
		// Por el tipo de codificación la base de datos no guarda el símbolo del
		// euro, por lo que se ha sustituido por el símbolo '?' y se controla en
		// las vistas
		if (visa.getCurrency().equals("&#8364;"))
			visa.setCurrency("?");
		f = this.visaRepository.save(visa);
		return f;
	}

	public void delete(final Visa v) throws ForbbidenActionException,
			IllegalClassFormatException {
		// Se comprueba que sea un Administrator el que borra la Visa
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		/*
		 * Se elimina la Visa de los objetos relacionados o se borra el objeto
		 */
		final Country c = v.getCountry();
		final Collection<Visa> visas = c.getVisas();
		if (visas != null) {
			visas.remove(v);
			this.countryService.save(c);
		}
		final Category cat = v.getCategory();
		final Collection<Visa> visas2 = cat.getVisas();
		if (visas2 != null) {
			visas2.remove(v);
			this.categoryService.save(cat);
		}
		final Collection<Requirement> reqs = v.getRequirements();
		v.setRequirements(new ArrayList<Requirement>());// aqui
		if (reqs != null)
			for (final Requirement r : reqs)
				this.requirementService.delete(r);
		final Collection<Application> apps = v.getApplications();
		v.setApplications(new ArrayList<Application>());
		if (apps != null)
			for (final Application a : apps)
				this.applicationService.delete(a);
		if (this.visaRepository.findOne(v.getId()) != null)
			this.visaRepository.delete(v);
	}

	public List<Visa> getAvailableVisas() {
		return this.visaRepository.getAvailableVisas();
	}

	public boolean abrogate(final Visa v) throws ForbbidenActionException {
		// Se comprueba que el que anula la Visa sea un Administrator
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		try {
			v.setAbrogated(true);
			this.save(v);
		} catch (final Exception e) {
			return false;
		}
		return true;
	}

	public List<Visa> searchVisaByKeyword(final String keyword) {
		// Si no se proporciona una keyword se devuelven las Visas disponibles,
		// es decir, las no anuladas
		if (keyword != null && !keyword.equals(""))
			return this.visaRepository.searchVisaFromKeyWordClase(keyword);
		else
			return this.visaRepository.getAvailableVisas();
	}

	public Map<String, String> priceStadistics()
			throws ForbbidenActionException {
		final String[] statistics = this.visaRepository.priceStadistics();
		final Map<String, String> res = new HashMap<>();
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();

		res.put("AVG", statistics[0]);
		res.put("MIN", statistics[1]);
		res.put("MAX", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

	public Map<String, Double> requirementsStadistics()
			throws ForbbidenActionException {
		final Double[] statistics = this.visaRepository
				.requirementsStadistics();
		final Map<String, Double> res = new HashMap<>();
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();

		res.put("AVG", statistics[0]);
		res.put("MIN", statistics[1]);
		res.put("MAX", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

}
