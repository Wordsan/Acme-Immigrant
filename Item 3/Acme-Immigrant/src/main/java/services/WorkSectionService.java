package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WorkSectionRepository;
import security.LoginService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import domain.WorkSection;

@Service
@Transactional
public class WorkSectionService {

	// Managed Repository
	@Autowired
	private WorkSectionRepository workSectionRepository;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ImmigrantService immigrantService;

	// Constructors
	public WorkSectionService() {
		super();
	}

	// Simple CRUD methods

	public WorkSection create() {
		final WorkSection f = new WorkSection();
		return f;
	}

	public Collection<WorkSection> findAll() {
		return this.workSectionRepository.findAll();
	}

	public WorkSection findOne(final int workSectionId)
			throws ObjectNotFoundException, ForbbidenActionException {
		final WorkSection a = this.workSectionRepository.findOne(workSectionId);
		if (a == null)
			throw new ObjectNotFoundException();
		if (this.immigrantService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		return a;
	}

	public WorkSection save(final WorkSection workSection)
			throws ForbbidenActionException {
		WorkSection f;
		Assert.notNull(workSection);
		if (this.immigrantService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		f = this.workSectionRepository.save(workSection);
		return f;
	}

	public void delete(final WorkSection workS) throws ForbbidenActionException {
		if (this.administratorService.getActorByUA(LoginService.getPrincipal()) == null)
			throw new ForbbidenActionException();
		this.workSectionRepository.delete(workS);
	}

}
