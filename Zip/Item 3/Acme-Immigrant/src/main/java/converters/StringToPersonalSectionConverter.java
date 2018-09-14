package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.PersonalSectionRepository;
import domain.PersonalSection;

@Component
@Transactional
public class StringToPersonalSectionConverter implements Converter<String, PersonalSection> {

	@Autowired
	PersonalSectionRepository personalSectionRepository;

	@Override
	public PersonalSection convert(final String source) {
		PersonalSection result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.personalSectionRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
