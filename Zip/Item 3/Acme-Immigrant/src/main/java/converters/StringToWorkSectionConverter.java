package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.WorkSectionRepository;
import domain.WorkSection;

@Component
@Transactional
public class StringToWorkSectionConverter implements Converter<String, WorkSection> {

	@Autowired
	WorkSectionRepository workSectionRepository;

	@Override
	public WorkSection convert(final String source) {
		WorkSection result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.workSectionRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
