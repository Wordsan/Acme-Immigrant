package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.VisaRepository;
import domain.Visa;

@Component
@Transactional
public class StringToVisaConverter implements Converter<String, Visa> {

	@Autowired
	VisaRepository visaRepository;

	@Override
	public Visa convert(final String source) {
		Visa result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.visaRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
