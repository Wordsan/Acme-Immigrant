package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import security.LoginService;
import domain.Folder;
import domain.PrivateMessage;

@Service
@Transactional
public class FolderService {

	// Managed Repository
	@Autowired
	private FolderRepository folderRepository;

	@Autowired
	private ActorService actorService;

	// Constructors
	public FolderService() {
		super();
	}

	// Simple CRUD methods

	public Folder create() {
		final Folder f = new Folder();
		return f;
	}

	public Collection<Folder> findAll() {
		return this.folderRepository.findAll();
	}

	public Folder findOne(final int folderId) {
		return this.folderRepository.findOne(folderId);
	}

	public Folder save(final Folder folder) {
		Folder f;
		Assert.notNull(folder);
		f = this.folderRepository.save(folder);
		return f;
	}

	public Collection<Folder> createNewFolder() {
		final Collection<Folder> res = new ArrayList<Folder>();
		final Folder f = new Folder();
		f.setTitle("Public");
		f.setType("Public");
		f.setOwner(this.actorService.getActorByUA(LoginService.getPrincipal()));
		f.setPrivateMessages(new ArrayList<PrivateMessage>());
		res.add(f);
		return res;
	}
}
