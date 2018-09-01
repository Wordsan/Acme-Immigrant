package converters;

import java.net.URLDecoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CreditCard;

@Component
@Transactional
public class StringToCreditCardConverter implements
		Converter<String, CreditCard> {

	@Override
	public CreditCard convert(final String source) {
		CreditCard result;
		String parts[];

		if (source.equals("null"))
			result = null;
		else
			try {
				parts = source.split("\\|");
				result = new CreditCard();
				result.setHolderName(URLDecoder.decode(parts[0], "UTF-8"));
				result.setBrandName((URLDecoder.decode(parts[1], "UTF-8")));
				result.setNumber(URLDecoder.decode(parts[2], "UTF-8"));
				result.setExpirationMonth((URLDecoder.decode(parts[3], "UTF-8")));
				result.setExpirationYear((URLDecoder.decode(parts[4], "UTF-8")));
				result.setCVVCode((URLDecoder.decode(parts[5], "UTF-8")));
			} catch (final Throwable ops) {
				throw new RuntimeException(ops);
			}
		return result;
	}

}
