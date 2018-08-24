package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.ImmigrantRepository;
import domain.Immigrant;

@Component
@Transactional
public class StringToImmigrantConverter implements Converter<String, Immigrant> {

	@Autowired
	ImmigrantRepository immigrantRepository;

	@Override
	public Immigrant convert(final String source) {
		Immigrant result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.immigrantRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
