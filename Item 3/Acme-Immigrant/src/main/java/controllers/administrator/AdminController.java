package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.AdministratorService;
import utilities.ObjectNotFoundException;
import controllers.AbstractController;
import domain.Administrator;
import forms.FormActor;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

	// Services
	@Autowired
	AdministratorService administratorService;
	@Autowired
	ActorService actorService;

	// Constructors (Debugueo)
	public AdminController() {
		super();
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		final ModelAndView result;
		Administrator r;

		r = this.administratorService.getActorByUA(LoginService.getPrincipal());
		Assert.notNull(r);
		result = this.createEditModelAndView(r);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FormActor formActor,
			final BindingResult br) throws ObjectNotFoundException {
		final Administrator admin = this.reconstruct(formActor);
		ModelAndView result;
		// Si el admin actual no es el mismo al de la cuenta que intenta editar
		// prohibe la accion
		if (!admin.getUserAccount().equals(LoginService.getPrincipal())) {
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("message", "forbbiden.access.error");
			return result;
		}
		// Si el binding tiene errores y las contrase�as no coinciden muestra el
		// error de las contrase�as
		if (br.hasErrors()
				&& !formActor.getPassword().equals(formActor.getRepassword()))
			result = this.createEditModelAndView(admin,
					"actor.password.confirm");
		else if (br.hasErrors())
			result = this.createEditModelAndView(admin);
		else
			try {
				// Si el nombre de usuario existe el objeto devuelto al guardar
				// es null, por lo que se manda a la p�gina de edici�n y se
				// indica
				if (this.administratorService.save(admin) == null) {
					result = this.createEditModelAndView(admin,
							"actor.username.exists");
					return result;
				}
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(admin,
						"actor.commit.error");
			}

		return result;

	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(final Administrator admin) {

		final ModelAndView result;

		result = this.createEditModelAndView(admin, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator admin,
			final String messageCode) {

		ModelAndView result;
		final FormActor actor = this.deconstruct(admin);
		result = new ModelAndView("actor/edit");
		result.addObject("formActor", actor);
		result.addObject("formURI", "admin/edit.do");
		result.addObject("messageCode", messageCode);

		return result;
	}

	public FormActor deconstruct(final Administrator a) {
		final FormActor actor = new FormActor();
		actor.setId(a.getId());
		actor.setName(a.getName());
		actor.setSurname(a.getSurname());
		actor.setEmail(a.getEmail());
		actor.setPhoneNumber(a.getPhoneNumber());
		actor.setAddress(a.getAddress());
		actor.setUsername(a.getUserAccount().getUsername());
		actor.setPassword(a.getUserAccount().getPassword());
		return actor;
	}

	public Administrator reconstruct(final FormActor actor)
			throws ObjectNotFoundException {
		final Administrator admin = this.administratorService.findOne(actor
				.getId());
		admin.setName(actor.getName());
		admin.setName(actor.getName());
		admin.setSurname(actor.getSurname());
		admin.setEmail(actor.getEmail());
		admin.setPhoneNumber(actor.getPhoneNumber());
		admin.setAddress(actor.getAddress());
		admin.getUserAccount().setUsername(actor.getUsername());
		admin.getUserAccount().setPassword(actor.getPassword());
		return admin;
	}

}
