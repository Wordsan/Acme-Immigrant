package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.SupervisorRepository;
import domain.Supervisor;

@Component
@Transactional
public class StringToSupervisorConverter implements Converter<String, Supervisor> {

	@Autowired
	SupervisorRepository supervisorRepository;

	@Override
	public Supervisor convert(final String source) {
		Supervisor result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.supervisorRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
