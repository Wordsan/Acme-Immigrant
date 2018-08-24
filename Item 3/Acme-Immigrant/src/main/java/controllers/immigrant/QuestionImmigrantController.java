package controllers.immigrant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ImmigrantService;
import services.QuestionService;
import utilities.ForbbidenActionException;
import utilities.ObjectNotFoundException;
import controllers.AbstractController;
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
	public ModelAndView display(@RequestParam final int questionId)
			throws ObjectNotFoundException {
		ModelAndView result;
		final Question question;

		question = this.questionService.findOne(questionId);
		result = new ModelAndView("question/display");
		result.addObject("question", question);

		return result;
	}

	// Save
	@RequestMapping(value = "/answer", method = RequestMethod.POST, params = "answerButton")
	public ModelAndView save(@RequestParam final int questionId,
			@RequestParam final String answer) throws ForbbidenActionException,
			ObjectNotFoundException {

		ModelAndView result;
		if (answer == "") {
			result = this.display(questionId);
			result.addObject("message",
					"org.hibernate.validator.constraints.NotEmpty.message");
			return result;
		}
		try {
			this.questionService.answer(questionId, answer);
			result = this.display(questionId);
		} catch (final ForbbidenActionException ops) {
			throw ops;
		} catch (final ObjectNotFoundException ops) {
			throw ops;
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
