package controllers.investigator;

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
import services.InvestigatorService;
import utilities.ObjectNotFoundException;
import controllers.AbstractController;
import domain.Investigator;
import forms.FormActor;

@Controller
@RequestMapping("/investigator")
public class InvestigatorController extends AbstractController {

	// Services
	@Autowired
	InvestigatorService investigatorService;
	@Autowired
	ActorService actorService;

	// Constructors (Debugueo)
	public InvestigatorController() {
		super();
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		final ModelAndView result;
		Investigator r;

		r = this.investigatorService.getActorByUA(LoginService.getPrincipal());
		Assert.notNull(r);
		result = this.createEditModelAndView(r);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FormActor formActor,
			final BindingResult br) throws ObjectNotFoundException {
		final Investigator investigator = this.reconstruct(formActor);
		ModelAndView result;
		// Si el investigator actual no es el mismo al de la cuenta que intenta
		// editar
		// prohibe la accion
		if (!investigator.getUserAccount().equals(LoginService.getPrincipal())) {
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("message", "forbbiden.access.error");
			return result;
		}
		// Si el binding tiene errores y las contraseñas no coinciden muestra el
		// error de las contraseñas
		if (br.hasErrors()
				&& !formActor.getPassword().equals(formActor.getRepassword()))
			result = this.createEditModelAndView(investigator,
					"actor.password.confirm");
		else if (br.hasErrors())
			result = this.createEditModelAndView(investigator);
		else
			try {
				// Si el nombre de usuario existe el objeto devuelto al guardar
				// es null, por lo que se manda a la página de edición y se
				// indica
				if (this.investigatorService.save(investigator) == null) {
					result = this.createEditModelAndView(investigator,
							"actor.username.exists");
					return result;
				}
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(investigator,
						"actor.commit.error");
			}

		return result;

	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(
			final Investigator investigator) {

		final ModelAndView result;

		result = this.createEditModelAndView(investigator, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			final Investigator investigator, final String messageCode) {

		ModelAndView result;
		final FormActor actor = this.deconstruct(investigator);
		result = new ModelAndView("actor/edit");
		result.addObject("formActor", actor);
		result.addObject("formURI", "investigator/edit.do");
		result.addObject("messageCode", messageCode);

		return result;
	}

	public FormActor deconstruct(final Investigator a) {
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

	public Investigator reconstruct(final FormActor actor)
			throws ObjectNotFoundException {
		final Investigator investigator = this.investigatorService
				.findOne(actor.getId());
		investigator.setName(actor.getName());
		investigator.setName(actor.getName());
		investigator.setSurname(actor.getSurname());
		investigator.setEmail(actor.getEmail());
		investigator.setPhoneNumber(actor.getPhoneNumber());
		investigator.setAddress(actor.getAddress());
		investigator.getUserAccount().setUsername(actor.getUsername());
		investigator.getUserAccount().setPassword(actor.getPassword());
		return investigator;
	}

}
