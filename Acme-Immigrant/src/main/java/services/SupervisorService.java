package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SupervisorRepository;
import domain.Actor;
import domain.Supervisor;

@Service
@Transactional
public class SupervisorService {

	// Managed Repository
	@Autowired
	private SupervisorRepository supervisorRepository;

	// Constructors
	public SupervisorService() {
		super();
	}

	// Simple CRUD methods

	public Supervisor create() {
		final Supervisor f = (Supervisor) new Actor();
		// f.setFolders(this.folderService.createNewFolder());
		return f;
	}

	public Collection<Supervisor> findAll() {
		return this.supervisorRepository.findAll();
	}

	public Supervisor findOne(final int supervisorId) {
		return this.supervisorRepository.findOne(supervisorId);
	}

	public Supervisor save(final Supervisor supervisor) {
		Supervisor f;
		Assert.notNull(supervisor);
		f = this.supervisorRepository.save(supervisor);
		return f;
	}

}