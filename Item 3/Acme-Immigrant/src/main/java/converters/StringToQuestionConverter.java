package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.QuestionRepository;
import domain.Question;

@Component
@Transactional
public class StringToQuestionConverter implements Converter<String, Question> {

	@Autowired
	QuestionRepository questionRepository;

	@Override
	public Question convert(final String source) {
		Question result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.questionRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
