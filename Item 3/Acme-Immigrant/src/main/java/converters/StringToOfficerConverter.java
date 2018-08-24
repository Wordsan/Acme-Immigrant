package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.OfficerRepository;
import domain.Officer;

@Component
@Transactional
public class StringToOfficerConverter implements Converter<String, Officer> {

	@Autowired
	OfficerRepository officerRepository;

	@Override
	public Officer convert(final String source) {
		Officer result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.officerRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
