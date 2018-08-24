package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.SocialSectionRepository;
import domain.SocialSection;

@Component
@Transactional
public class StringToSocialSectionConverter implements Converter<String, SocialSection> {

	@Autowired
	SocialSectionRepository socialSectionRepository;

	@Override
	public SocialSection convert(final String source) {
		SocialSection result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.socialSectionRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
