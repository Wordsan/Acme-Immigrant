package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.AdministratorRepository;
import domain.Administrator;

@Component
@Transactional
public class StringToAdministratorConverter implements Converter<String, Administrator> {

	@Autowired
	AdministratorRepository administratorRepository;

	@Override
	public Administrator convert(final String source) {
		Administrator result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.administratorRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
