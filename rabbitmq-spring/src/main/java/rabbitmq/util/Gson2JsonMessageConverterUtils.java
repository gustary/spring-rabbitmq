package rabbitmq.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractJsonMessageConverter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.google.gson.Gson;

/**
 * 文件名：Gson2JsonMessageConverter.java
 * 描述：Message转化工具类
 * 作者：coco
 * 日期：2017年7月3日下午5:57:02
 */
public class Gson2JsonMessageConverterUtils extends AbstractJsonMessageConverter {

	private static Log log = LogFactory.getLog(Gson2JsonMessageConverterUtils.class);

	private static ClassMapper classMapper = new DefaultClassMapper();

	private static Gson gson = new Gson();

	public Gson2JsonMessageConverterUtils() {
		super();
	}

	@Override
	protected Message createMessage(Object object, MessageProperties messageProperties) {
		byte[] bytes = null;
		try {
			String jsonString = gson.toJson(object);
			bytes = jsonString.getBytes(getDefaultCharset());
		} catch (IOException e) {
			throw new MessageConversionException("Failed to convert Message content", e);
		}
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
		messageProperties.setContentEncoding(getDefaultCharset());
		if (bytes != null) {
			messageProperties.setContentLength(bytes.length);
		}
		classMapper.fromClass(object.getClass(), messageProperties);
		return new Message(bytes, messageProperties);
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		Object content = null;
		MessageProperties properties = message.getMessageProperties();
		if (properties != null) {
			String contentType = properties.getContentType();
			if (contentType != null && contentType.contains("json")) {
				String encoding = properties.getContentEncoding();
				if (encoding == null) {
					encoding = getDefaultCharset();
				}
				try {
					Class<?> targetClass = getClassMapper().toClass(message.getMessageProperties());
					content = convertBytesToObject(message.getBody(), encoding, targetClass);
				} catch (IOException e) {
					throw new MessageConversionException("Failed to convert Message content", e);
				}
			} else {
				/* 日志输出 contentType */
				log.warn("Could not convert incoming message with content-type [" + contentType + "]");
			}
		}
		if (content == null) {
			content = message.getBody();
		}
		return content;
	}

	private Object convertBytesToObject(byte[] body, String encoding, Class<?> clazz)
			throws UnsupportedEncodingException {
		String contentAsString = new String(body, encoding);
		return gson.fromJson(contentAsString, clazz);
	}

	@Override
	public ClassMapper getClassMapper() {
		return new DefaultClassMapper();

	}
}
