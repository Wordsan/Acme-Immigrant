package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.DecisionRepository;
import domain.Decision;

@Component
@Transactional
public class StringToDecisionConverter implements Converter<String, Decision> {

	@Autowired
	DecisionRepository decisionRepository;

	@Override
	public Decision convert(final String source) {
		Decision result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.decisionRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
