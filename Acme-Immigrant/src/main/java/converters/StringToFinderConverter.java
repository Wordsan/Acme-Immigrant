package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.FinderRepository;
import domain.Finder;

@Component
@Transactional
public class StringToFinderConverter implements Converter<String, Finder> {

	@Autowired
	FinderRepository finderRepository;

	@Override
	public Finder convert(final String source) {
		Finder result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.finderRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
