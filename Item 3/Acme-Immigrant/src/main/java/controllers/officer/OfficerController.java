package controllers.officer;

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
import services.OfficerService;
import utilities.ObjectNotFoundException;
import controllers.AbstractController;
import domain.Officer;
import forms.FormActor;

@Controller
@RequestMapping("/officer")
public class OfficerController extends AbstractController {

	// Services
	@Autowired
	OfficerService officerService;
	@Autowired
	ActorService actorService;

	// Constructors (Debugueo)
	public OfficerController() {
		super();
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		final ModelAndView result;
		Officer r;

		r = this.officerService.getActorByUA(LoginService.getPrincipal());
		Assert.notNull(r);
		result = this.createEditModelAndView(r);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FormActor formActor,
			final BindingResult br) throws ObjectNotFoundException {
		final Officer officer = this.reconstruct(formActor);
		ModelAndView result;
		if (br.hasErrors()
				|| !formActor.getPassword().equals(formActor.getRepassword()))
			result = this.createEditModelAndView(officer,
					"actor.password.confirm");
		else
			try {
				if (this.officerService.save(officer) == null) {
					result = this.createEditModelAndView(officer,
							"actor.username.exists");
					return result;
				}
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(officer,
						"actor.commit.error");
			}

		return result;

	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(final Officer officer) {

		final ModelAndView result;

		result = this.createEditModelAndView(officer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Officer officer,
			final String messageCode) {

		ModelAndView result;
		final FormActor actor = this.deconstruct(officer);
		result = new ModelAndView("actor/edit");
		result.addObject("formActor", actor);
		result.addObject("formURI", "officer/edit.do");
		result.addObject("messageCode", messageCode);

		return result;
	}

	public FormActor deconstruct(final Officer a) {
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

	public Officer reconstruct(final FormActor actor)
			throws ObjectNotFoundException {
		final Officer officer = this.officerService.findOne(actor.getId());
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
