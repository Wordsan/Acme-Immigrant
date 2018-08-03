package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Supervisor;

@Component
@Transactional
public class SupervisorToStringConverter implements Converter<Supervisor, String> {

	@Override
	public String convert(final Supervisor supervisor) {
		String result;

		if (supervisor == null)
			result = null;
		else
			result = String.valueOf(supervisor.getId());
		return result;
	}

}
