package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CountryRepository;
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

	public Country findOne(final int countryId) {
		return this.countryRepository.findOne(countryId);
	}

	public Country save(final Country country) {
		Country f;
		Assert.notNull(country);
		f = this.countryRepository.save(country);
		return f;
	}

	public Map<String, Double> lawStadistics() {
		final Double[] statistics = this.countryRepository.lawStadistics();
		final Map<String, Double> res = new HashMap<>();

		res.put("AVG", statistics[0]);
		res.put("MIN", statistics[1]);
		res.put("MAX", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

	public void delete(final Country country) {
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
