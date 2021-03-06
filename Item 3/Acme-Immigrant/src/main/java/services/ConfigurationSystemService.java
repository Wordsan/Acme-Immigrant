package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ConfigurationSystemRepository;
import security.LoginService;
import utilities.ForbbidenActionException;
import domain.ConfigurationSystem;

@Service
@Transactional
public class ConfigurationSystemService {

	/* REPOSITORIES */
	@Autowired
	private ConfigurationSystemRepository configurationSystemRepository;

	/* SERVICES */
	@Autowired
	private AdministratorService adminService;

	/* CONSTRUCTOR */
	public ConfigurationSystemService() {
		super();
	}

	/* CRUD */

	public ConfigurationSystem save(final ConfigurationSystem cs)
			throws ForbbidenActionException {
		try {
			// Se comprueba que el que la intenta editar sea un Administrator
			if (this.adminService.getActorByUA(LoginService.getPrincipal()) == null)
				throw new ForbbidenActionException();
		} catch (final IllegalArgumentException e) {
			throw new ForbbidenActionException();
		}
		return this.configurationSystemRepository.save(cs);
	}

	/* OTHERS */

	public ConfigurationSystem get() {
		ConfigurationSystem result;
		List<ConfigurationSystem> cs = new ArrayList<>();

		cs = this.configurationSystemRepository.findAll();
		result = cs.get(0);

		return result;
	}
}
