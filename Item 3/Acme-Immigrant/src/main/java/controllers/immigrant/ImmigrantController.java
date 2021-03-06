package controllers.immigrant;

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
import services.ImmigrantService;
import utilities.ObjectNotFoundException;
import controllers.AbstractController;
import domain.Immigrant;
import forms.FormActor;

@Controller
@RequestMapping("/immigrant")
public class ImmigrantController extends AbstractController {

	// Services
	@Autowired
	ImmigrantService immigrantService;
	@Autowired
	ActorService actorService;

	// Constructors (Debugueo)
	public ImmigrantController() {
		super();
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		final ModelAndView result;
		Immigrant r;

		r = this.immigrantService.getActorByUA(LoginService.getPrincipal());
		Assert.notNull(r);
		result = this.createEditModelAndView(r);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FormActor formActor,
			final BindingResult br) throws ObjectNotFoundException {
		final Immigrant immigrant = this.reconstruct(formActor);
		ModelAndView result;
		// Si el immigrant actual no es el mismo al de la cuenta que intenta
		// editar
		// prohibe la accion
		if (!immigrant.getUserAccount().equals(LoginService.getPrincipal())) {
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("message", "forbbiden.access.error");
			return result;
		}
		// Si el binding tiene errores y las contrase�as no coinciden muestra el
		// error de las contrase�as
		if (br.hasErrors()
				&& !formActor.getPassword().equals(formActor.getRepassword()))
			result = this.createEditModelAndView(immigrant,
					"actor.password.confirm");
		else if (br.hasErrors())
			result = this.createEditModelAndView(immigrant);
		else
			try {
				// Si el nombre de usuario existe el objeto devuelto al guardar
				// es null, por lo que se manda a la p�gina de edici�n y se
				// indica
				if (this.immigrantService.save(immigrant) == null) {
					result = this.createEditModelAndView(immigrant,
							"actor.username.exists");
					return result;
				}
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(immigrant,
						"actor.commit.error");
			}

		return result;

	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(final Immigrant immigrant) {

		final ModelAndView result;

		result = this.createEditModelAndView(immigrant, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Immigrant immigrant,
			final String messageCode) {

		ModelAndView result;
		final FormActor actor = this.deconstruct(immigrant);
		result = new ModelAndView("actor/edit");
		result.addObject("formActor", actor);
		result.addObject("formURI", "immigrant/edit.do");
		result.addObject("message", messageCode);

		return result;
	}

	public FormActor deconstruct(final Immigrant a) {
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

	public Immigrant reconstruct(final FormActor actor)
			throws ObjectNotFoundException {
		final Immigrant immigrant = this.immigrantService
				.findOne(actor.getId());
		immigrant.setName(actor.getName());
		immigrant.setName(actor.getName());
		immigrant.setSurname(actor.getSurname());
		immigrant.setEmail(actor.getEmail());
		immigrant.setPhoneNumber(actor.getPhoneNumber());
		immigrant.setAddress(actor.getAddress());
		immigrant.getUserAccount().setUsername(actor.getUsername());
		immigrant.getUserAccount().setPassword(actor.getPassword());
		return immigrant;
	}

}
