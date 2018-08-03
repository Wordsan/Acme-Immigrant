package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PrivateMessage;

@Component
@Transactional
public class PrivateMessageToStringConverter implements Converter<PrivateMessage, String> {

	@Override
	public String convert(final PrivateMessage privateMessage) {
		String result;

		if (privateMessage == null)
			result = null;
		else
			result = String.valueOf(privateMessage.getId());
		return result;
	}

}
