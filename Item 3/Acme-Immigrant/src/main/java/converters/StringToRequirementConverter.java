package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.RequirementRepository;
import domain.Requirement;

@Component
@Transactional
public class StringToRequirementConverter implements Converter<String, Requirement> {

	@Autowired
	RequirementRepository requirementRepository;

	@Override
	public Requirement convert(final String source) {
		Requirement result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.requirementRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
