package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.ContactSectionRepository;
import domain.ContactSection;

@Component
@Transactional
public class StringToContactSectionConverter implements Converter<String, ContactSection> {

	@Autowired
	ContactSectionRepository contactSectionRepository;

	@Override
	public ContactSection convert(final String source) {
		ContactSection result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.contactSectionRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
