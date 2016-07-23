package com.niwodai.app.gateway.reqest.parse.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

/**
 * @author tim.yin
 * @date 2015年12月28日 上午11:22:03
 * @version 1.0
 * @Description:TODO
 */

@SuppressWarnings("rawtypes")
@Component
public final class StringToEnumConverterFactory implements
		ConverterFactory<String, Enum> {

	@SuppressWarnings("unchecked")
	public <T extends Enum> Converter<String, T> getConverter(
			Class<T> targetType) {
		return new StringToEnum(targetType);
	}

	private class StringToEnum<T extends Enum> implements Converter<String, T> {

		private final Class<T> enumType;

		public StringToEnum(Class<T> enumType) {
			this.enumType = enumType;
		}

		public T convert(String source) {
			if (source.length() == 0) {
				// It's an empty enum identifier: reset the enum value to null.
				return null;
			}

			for (T t : enumType.getEnumConstants()) {
				if (source.equals(t.toString())) {
					return t;
				}
			}

			return null;
			// return (T) Enum.valueOf(this.enumType, source.trim());
		}
	}

}