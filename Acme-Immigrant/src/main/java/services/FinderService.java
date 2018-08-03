package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;
import domain.PrivateMessage;

@Service
@Transactional
public class FinderService {

	// Managed Repository
	@Autowired
	private FinderRepository finderRepository;

	// Constructors
	public FinderService() {
		super();
	}

	// Simple CRUD methods

	public Finder create() {
		final Finder f = new Finder();
		f.setResults(new ArrayList<PrivateMessage>());
		return f;
	}

	public Collection<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne(final int finderId) {
		return this.finderRepository.findOne(finderId);
	}

	public Finder save(final Finder finder) {
		Finder f;
		Assert.notNull(finder);
		f = this.finderRepository.save(finder);
		return f;
	}

}