package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.OfficerService;
import controllers.AbstractController;
import domain.Officer;
import forms.FormActor;

@Controller
@RequestMapping("/officer/admin")
public class OfficerAdminController extends AbstractController {

	// Services
	@Autowired
	OfficerService officerService;
	@Autowired
	ActorService actorService;

	// Constructors (Debugueo)
	public OfficerAdminController() {
		super();
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final FormActor actor = new FormActor();
		result = new ModelAndView("actor/edit");
		result.addObject("formActor", actor);
		result.addObject("formUri", "officer/admin/edit.do");
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
				final Officer i = this.officerService.save(this
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

	public Officer reconstruct(final FormActor actor) {
		final Officer officer = this.officerService.create();
		officer.setName(actor.getName());
		officer.setName(actor.getName());
		officer.setSurname(actor.getSurname());
		officer.setEmail(actor.getEmail());
		officer.setPhoneNumber(actor.getPhoneNumber());
		officer.setAddress(actor.getAddress());
		officer.getUserAccount().setUsername(actor.getUsername());
		officer.getUserAccount().setPassword(actor.getPassword());
		return officer;
	}
}
