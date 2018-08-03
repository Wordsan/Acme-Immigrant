package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.VisaRepository;
import domain.Application;
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

	// Constructors
	public VisaService() {
		super();
	}

	// Simple CRUD methods

	public Visa create() {
		final Visa f = new Visa();
		return f;
	}

	public Collection<Visa> findAll() {
		return this.visaRepository.findAll();
	}

	public Visa findOne(final int visaId) {
		return this.visaRepository.findOne(visaId);
	}

	public Visa save(final Visa visa) {
		Visa f;
		Assert.notNull(visa);
		f = this.visaRepository.save(visa);
		return f;
	}

	public void delete(final Visa v) {
		final Collection<Requirement> reqs = v.getRequirements();
		v.setRequirements(null);// aqui
		for (final Requirement r : reqs)
			this.requirementService.delete(r);
		final Collection<Application> apps = v.getApplications();
		v.setApplications(null);
		for (final Application a : apps)
			this.applicationService.delete(a);
		this.visaRepository.delete(v);
	}

	public List<Visa> getAvailableVisas() {
		return this.visaRepository.getAvailableVisas();
	}

	public boolean abrogate(final Visa v) {
		try {
			v.setAbrogated(true);
			this.save(v);
		} catch (final Exception e) {
			return false;
		}
		return true;
	}

	public List<Visa> searchVisaByKeyword(final String keyword) {
		return this.visaRepository.searchVisaFromKeyWordClase(keyword);
	}

	public Map<String, Double> priceStadistics() {
		final Double[] statistics = this.visaRepository.priceStadistics();
		final Map<String, Double> res = new HashMap<>();

		res.put("AVG", statistics[0]);
		res.put("MIN", statistics[1]);
		res.put("MAX", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

	public Map<String, Double> lawStadistics() {
		final Double[] statistics = this.visaRepository
				.requirementsStadistics();
		final Map<String, Double> res = new HashMap<>();

		res.put("AVG", statistics[0]);
		res.put("MIN", statistics[1]);
		res.put("MAX", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

}
