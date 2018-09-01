package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.InvestigatorService;
import controllers.AbstractController;
import domain.Investigator;
import forms.FormActor;

@Controller
@RequestMapping("/investigator/admin")
public class InvestigatorAdminController extends AbstractController {

	// Services
	@Autowired
	InvestigatorService investigatorService;
	@Autowired
	ActorService actorService;

	// Constructors (Debugueo)
	public InvestigatorAdminController() {
		super();
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final FormActor actor = new FormActor();
		result = new ModelAndView("actor/edit");
		result.addObject("formActor", actor);
		result.addObject("formUri", "investigator/admin/edit.do");
		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FormActor actor,
			final BindingResult br) {
		ModelAndView result;

		if (br.hasErrors()) {
			result = new ModelAndView("actor/edit");
			result.addObject("formActor", actor);
		} else
			try {
				// Si las contraseñas no coinciden manda a la página de edicion
				// y lo indica
				if (!actor.getPassword().equals(actor.getRepassword())) {
					result = new ModelAndView("actor/edit");
					result.addObject("formActor", actor);
					result.addObject("message", "actor.password.confirm");
					return result;
				}
				final Investigator i = this.investigatorService.save(this
						.reconstruct(actor));
				// Al guardar el usuario si el username ya existe devuelve nulo,
				// asi que se manda a la página de edición y se indica
				if (i == null) {
					result = new ModelAndView("actor/edit");
					result.addObject("formActor", actor);
					result.addObject("message", "actor.username.exists");
					return result;
				}
				result = new ModelAndView("redirect:/welcome/index.do");
				return result;
			} catch (final Throwable ops) {
				result = new ModelAndView("actor/edit");
				result.addObject("formActor", actor);
				result.addObject("message", "actor.commit.error");
			}

		return result;

	}

	public Investigator reconstruct(final FormActor actor) {
		final Investigator investigator = this.investigatorService.create();
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
