package controllers.immigrant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import services.ImmigrantService;
import services.PersonalSectionService;
import services.SocialSectionService;
import controllers.AbstractController;
import controllers.WelcomeController;
import domain.Application;
import domain.CreditCard;
import domain.PersonalSection;
import domain.SocialSection;
import forms.ApplicationSections;

@Controller
@RequestMapping("/application/immigrant")
public class ApplicationImmigrantController extends AbstractController {

	// Services
	@Autowired
	ApplicationService applicationService;

	@Autowired
	ImmigrantService immigrantService;

	@Autowired
	PersonalSectionService personalSectionService;

	@Autowired
	SocialSectionService socialSectionService;

	// Constructors (Debugueo)
	public ApplicationImmigrantController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		ModelAndView result;
		final Application a;

		a = this.applicationService.findOne(applicationId);
		if (!a.getImmigrant().getUserAccount()
				.equals(LoginService.getPrincipal())) {
			result = WelcomeController.indice("forbbiden.access.error",
					this.immigrantService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		result = new ModelAndView("application/display");
		result.addObject("application", a);

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int visaId) {
		ModelAndView result;
		final ApplicationSections apps = new ApplicationSections();

		apps.setVisaId(visaId);
		final Collection<Application> as = this.immigrantService.getActorByUA(
				LoginService.getPrincipal()).getApplications();
		for (final Application a : as)
			if (a.getVisa().getId() == visaId) {
				result = WelcomeController.indice("resource.already.exists",
						this.immigrantService.getActorByUA(LoginService
								.getPrincipal()));
				return result;
			}
		result = this.createEditModelAndView(apps);

		return result;
	}

	// Edit
	// @RequestMapping(value = "/edit", method = RequestMethod.GET)
	// public ModelAndView edit(@RequestParam final int applicationId) {
	//
	// ModelAndView result;
	// Application a;
	//
	// a = this.applicationService.findOne(applicationId);
	// if (a.getClosedMoment() == null) {
	// result = new ModelAndView("application/list");
	// result.addObject("message", "application.notclosed");
	// }
	// result = this.createEditModelAndView(this.deconstruct(a));
	//
	// return result;
	// }

	// Close
	@RequestMapping(value = "/close", method = RequestMethod.GET)
	public ModelAndView close(@RequestParam final int applicationId) {
		ModelAndView result;

		if (!this.immigrantService.getActorByUA(LoginService.getPrincipal())
				.getApplications()
				.contains(this.applicationService.findOne(applicationId))) {
			result = WelcomeController.indice("forbbiden.access.error",
					this.immigrantService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		if (this.applicationService.close(applicationId))
			return this.display(applicationId);
		else {
			result = this.display(applicationId);
			result.addObject("message", "application.close.error");
		}
		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ApplicationSections apps,
			final BindingResult br) {

		ModelAndView result;
		CreditCard cc;
		final Collection<CreditCard> creditCards = this.immigrantService
				.getActorByUA(LoginService.getPrincipal()).getCreditCards();
		if (br.getFieldErrorCount() == 1
				&& br.getFieldError().getField().equals("creditCard")
				&& br.getFieldError("creditCard").getRejectedValue() != null)
			for (final CreditCard c : creditCards)
				if (c.getNumber().equals(
						br.getFieldError("creditCard").getRejectedValue()
								.toString())) {
					cc = c;
					try {
						final Calendar cal = new GregorianCalendar();
						cal.set(Integer.parseInt("20" + c.getExpirationYear()),
								Integer.parseInt(c.getExpirationMonth()),
								cal.get(Calendar.DAY_OF_MONTH));
						cal.add(Calendar.MONTH, -3);
						if (cal.before(new GregorianCalendar()))
							return this.createEditModelAndView(apps,
									"creditCard.expiration.error");
						apps.setCreditCard(cc);
						final Application a = this.reconstruct(apps);
						if (!this.immigrantService
								.getActorByUA(LoginService.getPrincipal())
								.getApplications().contains(a)) {
							result = WelcomeController.indice(
									"forbbiden.access.error",
									this.immigrantService
											.getActorByUA(LoginService
													.getPrincipal()));
							return result;
						}
						a.setStatus("OPENED");
						this.applicationService.save(a);
						result = new ModelAndView("redirect:/welcome/index.do");
					} catch (final IllegalArgumentException e) {
						result = this.createEditModelAndView(apps,
								"creditCard.commit.error");
					} catch (final Throwable ops) {
						result = this.createEditModelAndView(apps,
								"application.commit.error");
					}
					return result;
				}
		if (br.hasErrors()) {
			final Collection<CreditCard> creditCardsSource = this.immigrantService
					.getActorByUA(LoginService.getPrincipal()).getCreditCards();
			result = new ModelAndView("application/edit");
			result.addObject("apps", apps);
			result.addObject("creditCards", creditCardsSource);
			result.addObject("formURI", "application/immigrant/edit.do");
		} else
			try {
				final Application a = this.reconstruct(apps);
				if (!this.immigrantService
						.getActorByUA(LoginService.getPrincipal())
						.getApplications().contains(a)) {
					result = WelcomeController.indice("forbbiden.access.error",
							this.immigrantService.getActorByUA(LoginService
									.getPrincipal()));
					return result;
				}
				this.applicationService.save(a);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final IllegalArgumentException e) {
				result = this.createEditModelAndView(apps,
						"creditCard.commit.error");
			} catch (final Throwable ops) {
				result = this.createEditModelAndView(apps,
						"application.commit.error");
			}

		return result;

	}

	// Link
	@RequestMapping(value = "/link", method = RequestMethod.POST)
	public ModelAndView link(@RequestParam final String ticker,
			@RequestParam final int applicationId) {
		ModelAndView result;
		final int code = this.applicationService.link(ticker, applicationId);
		if (code == 0)
			return this.display(applicationId);
		else if (code == 1) {
			result = this.display(applicationId);
			result.addObject("message", "application.already.linked");
		} else if (code == 2) {
			result = this.display(applicationId);
			result.addObject("message", "application.country.distinct");
		} else {
			result = this.display(applicationId);
			result.addObject("message", "application.link.error");
		}
		return result;
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final String status) {
		ModelAndView result;
		List<Application> apps = new ArrayList<>();

		if (status == null || status == "")
			apps = this.applicationService.applicationsFromStatus("OPENED");
		else
			apps = this.applicationService.applicationsFromStatus(status);
		result = new ModelAndView("application/list");
		result.addObject("applications", apps);
		result.addObject("requestURI", "application/immigrant/list.do");

		return result;
	}

	// Ancillary Methods
	protected ModelAndView createEditModelAndView(final ApplicationSections apps) {

		final ModelAndView result;

		result = this.createEditModelAndView(apps, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			final ApplicationSections apps, final String messageCode) {

		ModelAndView result;

		final Collection<CreditCard> creditCards = this.immigrantService
				.getActorByUA(LoginService.getPrincipal()).getCreditCards();
		result = new ModelAndView("application/edit");
		result.addObject("apps", apps);
		result.addObject("creditCards", creditCards);
		result.addObject("formURI", "application/immigrant/edit.do");
		result.addObject("message", messageCode);

		return result;
	}

	private Application reconstruct(final ApplicationSections apps) {
		Application a;
		if (apps.getId() != 0)
			a = this.applicationService.findOne(apps.getId());
		else {
			a = this.applicationService.create(apps.getVisaId());
			final PersonalSection p = this.personalSectionService.create();
			p.setBirthDate(apps.getBirthDate());
			p.setBirthPlace(apps.getBirthPlace());
			p.setFullNames(apps.getFullNames());
			p.setPicture(apps.getPicture());
			final PersonalSection ps = this.personalSectionService.save(p);
			final SocialSection s = this.socialSectionService.create();
			s.setLinkProfile(apps.getLinkProfile());
			s.setNickname(apps.getNickname());
			s.setSocialNetwork(apps.getSocialNetwork());
			final SocialSection ss = this.socialSectionService.save(s);
			a.setPersonalSection(ps);
			a.getSocialSections().add(ss);
		}
		a.setCreditCard(apps.getCreditCard());

		return a;
	}

}
