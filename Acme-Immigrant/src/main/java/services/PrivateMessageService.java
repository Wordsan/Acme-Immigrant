package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PrivateMessageRepository;
import domain.PrivateMessage;

@Service
@Transactional
public class PrivateMessageService {

	// Managed Repository
	@Autowired
	private PrivateMessageRepository privateMessageRepository;

	// Constructors
	public PrivateMessageService() {
		super();
	}

	// Simple CRUD methods

	public PrivateMessage create() {
		final PrivateMessage f = new PrivateMessage();
		f.setMadeMoment(new Date(System.currentTimeMillis() - 1000));
		return f;
	}

	public Collection<PrivateMessage> findAll() {
		return this.privateMessageRepository.findAll();
	}

	public PrivateMessage findOne(final int privateMessageId) {
		return this.privateMessageRepository.findOne(privateMessageId);
	}

	public PrivateMessage save(final PrivateMessage privateMessage) {
		PrivateMessage f;
		Assert.notNull(privateMessage);
		privateMessage
				.setMadeMoment(new Date(System.currentTimeMillis() - 1000));
		f = this.privateMessageRepository.save(privateMessage);
		return f;
	}

}
