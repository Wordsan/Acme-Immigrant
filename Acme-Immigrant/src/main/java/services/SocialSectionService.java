package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialSectionRepository;
import domain.SocialSection;

@Service
@Transactional
public class SocialSectionService {

	// Managed Repository
	@Autowired
	private SocialSectionRepository socialSectionRepository;

	// Constructors
	public SocialSectionService() {
		super();
	}

	// Simple CRUD methods

	public SocialSection create() {
		final SocialSection f = new SocialSection();
		return f;
	}

	public Collection<SocialSection> findAll() {
		return this.socialSectionRepository.findAll();
	}

	public SocialSection findOne(final int socialSectionId) {
		return this.socialSectionRepository.findOne(socialSectionId);
	}

	public SocialSection save(final SocialSection socialSection) {
		SocialSection f;
		Assert.notNull(socialSection);
		f = this.socialSectionRepository.save(socialSection);
		return f;
	}

}
