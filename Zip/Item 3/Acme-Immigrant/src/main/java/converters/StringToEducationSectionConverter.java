package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.EducationSectionRepository;
import domain.EducationSection;

@Component
@Transactional
public class StringToEducationSectionConverter implements Converter<String, EducationSection> {

	@Autowired
	EducationSectionRepository educationSectionRepository;

	@Override
	public EducationSection convert(final String source) {
		EducationSection result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.educationSectionRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
