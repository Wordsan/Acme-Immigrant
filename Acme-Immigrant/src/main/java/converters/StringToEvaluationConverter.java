package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.EvaluationRepository;
import domain.Evaluation;

@Component
@Transactional
public class StringToEvaluationConverter implements Converter<String, Evaluation> {

	@Autowired
	EvaluationRepository evaluationRepository;

	@Override
	public Evaluation convert(final String source) {
		Evaluation result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.evaluationRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
