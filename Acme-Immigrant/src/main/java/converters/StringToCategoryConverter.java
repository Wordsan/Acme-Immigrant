package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.CategoryRepository;
import domain.Category;

@Component
@Transactional
public class StringToCategoryConverter implements Converter<String, Category> {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Category convert(final String source) {
		Category result;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				result = null;
			else {
				id = Integer.valueOf(source);
				result = this.categoryRepository.findOne(id);
			}
		} catch (final Throwable ops) {
			throw new IllegalArgumentException(ops);
		}
		return result;
	}

}
