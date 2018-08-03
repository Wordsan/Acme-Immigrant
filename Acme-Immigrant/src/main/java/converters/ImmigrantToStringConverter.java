package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Immigrant;

@Component
@Transactional
public class ImmigrantToStringConverter implements Converter<Immigrant, String> {

	@Override
	public String convert(final Immigrant immigrant) {
		String result;

		if (immigrant == null)
			result = null;
		else
			result = String.valueOf(immigrant.getId());
		return result;
	}

}
