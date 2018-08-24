package controllers.immigrant;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ImmigrantService;
import utilities.ForbbidenActionException;
import controllers.AbstractController;
import domain.CreditCard;
import domain.Immigrant;

@Controller
@RequestMapping("/creditCard/immigrant")
public class CreditCardImmigrantController extends AbstractController {

	// SERVICES
	@Autowired
	private ImmigrantService immigrantService;

	// CONSTRUCTOR
	public CreditCardImmigrantController() {
		super();
	}

	// LISTING
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<CreditCard> creditCards;

		final Immigrant immigrant = this.immigrantService
				.getActorByUA(LoginService.getPrincipal());
		Assert.notNull(immigrant);

		creditCards = immigrant.getCreditCards();

		result = new ModelAndView("creditCard/list");
		result.addObject("cards", creditCards);
		result.addObject("requestURI", "creditCard/immigrant/list.do");
		return result;
	}

	// CREATION
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() throws ForbbidenActionException {
		ModelAndView result;
		CreditCard cc;
		final Immigrant i = this.immigrantService.getActorByUA(LoginService
				.getPrincipal());
		Assert.notNull(i);
		cc = new CreditCard();
		cc.setHolderName(i.getName());
		result = this.createEditModelAndView(cc);

		return result;

	}

	// EDITION
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final String creditCardNumber)
			throws ForbbidenActionException {
		final ModelAndView result;
		CreditCard cc = new CreditCard();

		final Immigrant i = this.immigrantService.getActorByUA(LoginService
				.getPrincipal());
		for (final CreditCard c : i.getCreditCards())
			if (c.getNumber().equals(creditCardNumber))
				cc = c;
		Assert.notNull(cc);
		result = this.createEditModelAndView(cc);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CreditCard cc,
			final BindingResult binding) throws ForbbidenActionException {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(cc);
		else
			try {
				final Immigrant i = this.immigrantService
						.getActorByUA(LoginService.getPrincipal());
				i.getCreditCards().add(cc);
				this.immigrantService.save(i);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(cc,
						"creditCard.commit.error");
			}
		return result;
	}

	// ANCILLARY
	protected ModelAndView createEditModelAndView(final CreditCard cc)
			throws ForbbidenActionException {
		ModelAndView result;
		result = this.createEditModelAndView(cc, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final CreditCard cc,
			final String messageCode) throws ForbbidenActionException {
		ModelAndView result;
		final Immigrant i = this.immigrantService.getActorByUA(LoginService
				.getPrincipal());

		if (i.getCreditCards().contains(cc)) {
			i.getCreditCards().remove(cc);
			this.immigrantService.save(i);
		}

		result = new ModelAndView("creditCard/edit");
		result.addObject("creditCard", cc);
		result.addObject("message", messageCode);
		return result;
	}
}
