package com.niwodai.app.gateway.remote.call.util;

//import java.io.IOException;
//
//import org.codehaus.jackson.JsonGenerator;
//import org.codehaus.jackson.JsonProcessingException;
//import org.codehaus.jackson.map.JsonSerializer;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author tim.yin
 * @date 2016年2月26日 下午5:42:43
 * @version 1.0
 * @Description:json 最终返回值 null 转化为 "" 数字类型转换为 0
 */

public class JsonNullConvertToEmpty extends ObjectMapper {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public JsonNullConvertToEmpty() {
		super();

		// 设置null转换""
		getSerializerProvider().setNullValueSerializer(new NullSerializer());
	}

	// null的JSON序列
	private class NullSerializer extends JsonSerializer<Object> {
		public void serialize(Object value, JsonGenerator jgen,
				SerializerProvider provider) throws IOException,
				JsonProcessingException {

			jgen.writeString("");
		}
	}
}
