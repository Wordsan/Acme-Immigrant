package controllers.anonymous;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ImmigrantService;
import controllers.AbstractController;
import domain.Immigrant;
import forms.FormActor;

@Controller
@RequestMapping("/nonAuth/immigrant")
public class ImmigrantNonAuthController extends AbstractController {

	// Services
	@Autowired
	ImmigrantService immigrantService;
	@Autowired
	ActorService actorService;

	// Constructors (Debugueo)
	public ImmigrantNonAuthController() {
		super();
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final FormActor actor = new FormActor();
		result = new ModelAndView("actor/edit");
		result.addObject("formActor", actor);
		result.addObject("formUri", "nonAuth/immigrant/edit.do");
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
				// Si el usuario no acepta los terminos se le indica que es
				// obligatorio
				if (!actor.isTerms()) {
					result = new ModelAndView("actor/edit");
					result.addObject("formActor", actor);
					result.addObject("message", "actor.terms.false");
					return result;
					// Si las contraseñas no coinciden se le indica al usuario
				} else if (!actor.getPassword().equals(actor.getRepassword())) {
					result = new ModelAndView("actor/edit");
					result.addObject("formActor", actor);
					result.addObject("message", "actor.password.confirm");
					return result;
				}
				final Immigrant i = this.immigrantService.save(this
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

	public Immigrant reconstruct(final FormActor actor) {
		final Immigrant immigrant = this.immigrantService.create();
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
