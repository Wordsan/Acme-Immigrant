package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CreditCard;

@Component
@Transactional
public class CreditCardToStringConverter implements
		Converter<CreditCard, String> {

	@Override
	public String convert(final CreditCard source) {
		String result;
		StringBuilder builder;

		if (source == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(source.getHolderName(),
						"UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(source.getBrandName(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(source.getNumber(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(source.getExpirationMonth(),
						"UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(source.getExpirationYear(),
						"UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(
						Integer.toString(source.getCVVCode()), "UTF-8"));
				result = builder.toString();
			} catch (final Throwable ops) {
				throw new RuntimeException(ops);
			}
		return result;
	}

}
