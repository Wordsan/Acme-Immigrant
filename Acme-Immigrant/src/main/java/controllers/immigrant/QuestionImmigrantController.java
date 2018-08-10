package controllers.immigrant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ImmigrantService;
import services.QuestionService;
import controllers.AbstractController;
import controllers.WelcomeController;
import domain.Question;

@Controller
@RequestMapping("/question/immigrant")
public class QuestionImmigrantController extends AbstractController {

	// Services
	@Autowired
	QuestionService questionService;

	@Autowired
	ImmigrantService immigrantService;

	// Constructors (Debugueo)
	public QuestionImmigrantController() {
		super();
	}

	// Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int questionId) {
		ModelAndView result;
		final Question question;

		try {
			question = this.questionService.findOne(questionId);
			if (question == null)
				throw new Exception("object.not.fount");
		} catch (final Exception e) {
			result = WelcomeController.indice("object.not.found",
					this.immigrantService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		if (!question.getImmigrant().getUserAccount()
				.equals(LoginService.getPrincipal())) {
			result = WelcomeController.indice("forbbiden.access.error",
					this.immigrantService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		result = new ModelAndView("question/display");
		result.addObject("question", question);

		return result;
	}

	// Save
	@RequestMapping(value = "/answer", method = RequestMethod.POST, params = "answerButton")
	public ModelAndView save(@RequestParam final int questionId,
			@RequestParam final String answer) {

		ModelAndView result;
		if (!this.immigrantService.getActorByUA(LoginService.getPrincipal())
				.getQuestions()
				.contains(this.questionService.findOne(questionId))) {
			result = WelcomeController.indice("forbbiden.access.error",
					this.immigrantService.getActorByUA(LoginService
							.getPrincipal()));
			return result;
		}
		if (answer == "") {
			result = this.display(questionId);
			result.addObject("message",
					"org.hibernate.validator.constraints.NotEmpty.message");
			return result;
		}
		try {
			this.questionService.answer(questionId, answer);
			result = this.display(questionId);
		} catch (final Throwable ops) {
			result = this.display(questionId);
			result.addObject("message", "question.commit.error");
		}

		return result;

	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		List<Question> questions = new ArrayList<>();

		questions = this.questionService.questionsByImmigrantUA();
		result = new ModelAndView("question/list");
		result.addObject("questions", questions);
		result.addObject("requestURI", "question/immigrant/list.do");

		return result;
	}

}
