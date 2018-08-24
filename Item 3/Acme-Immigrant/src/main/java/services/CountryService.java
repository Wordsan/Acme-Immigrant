package services;

import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CountryRepository;
import security.LoginService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.Country;
import domain.Law;
import domain.Visa;

@Service
@Transactional
public class CountryService {

	// Managed Repository
	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private LawService lawService;

	@Autowired
	private VisaService visaService;

	@Autowired
	private AdministratorService administratorService;

	// Constructors
	public CountryService() {
		super();
	}

	// Simple CRUD methods

	public Country create() {
		final Country f = new Country();
		return f;
	}

	public Collection<Country> findAll() {
		return this.countryRepository.findAll();
	}

	public Country findOne(final int countryId) throws ObjectNotFoundException {
		final Country a = this.countryRepository.findOne(countryId);
		if (a == null)
			throw new ObjectNotFoundException();
		return a;
	}

	public Country save(final Country country) throws ForbbidenActionException {
		Country f;
		Assert.notNull(country);
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		f = this.countryRepository.save(country);
		return f;
	}

	public Map<String, Double> lawStadistics() throws ForbbidenActionException {
		final Double[] statistics = this.countryRepository.lawStadistics();
		final Map<String, Double> res = new HashMap<>();
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();

		res.put("AVG", statistics[0]);
		res.put("MIN", statistics[1]);
		res.put("MAX", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

	public void delete(final Country country) throws ForbbidenActionException,
			IllegalClassFormatException {
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		final Collection<Law> laws = country.getLaws();
		country.setLaws(new ArrayList<Law>());
		if (laws != null)
			for (final Law law : laws)
				this.lawService.delete(law);
		final Collection<Visa> visas = country.getVisas();
		country.setVisas(new ArrayList<Visa>());
		if (visas != null)
			for (final Visa visa : visas)
				this.visaService.delete(visa);
		this.countryRepository.delete(country);
	}

}
