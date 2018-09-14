package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.InvestigatorRepository;
import domain.Investigator;

@Component
@Transactional
public class StringToInvestigatorConverter implements
		Converter<String, Investigator> {

	@Autowired
	InvestigatorRepository investigatorRepository;

	@Override
	public Investigator convert(final String source) {
		Investigator result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.investigatorRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
