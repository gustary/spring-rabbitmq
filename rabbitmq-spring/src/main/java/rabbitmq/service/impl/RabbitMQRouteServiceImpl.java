package rabbitmq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rabbitmq.producer.biz.MessageProducerBiz;
import rabbitmq.service.RabbitMQRouteService;

/**
 * 文件名：RabbitMQRouteServiceImpl.java
 * 描述：
 * 作者：kj00023
 * 日期：2017年6月29日下午3:35:54
 */
@Service
public class RabbitMQRouteServiceImpl implements RabbitMQRouteService {

	@Autowired
	private MessageProducerBiz messageProducerBiz;

	public void mqRoute(Object message) {
		messageProducerBiz.mqRoute(message);
	}

	public void mqRoute(String exchange, String routingKey, Object message) {
		messageProducerBiz.mqRoute(exchange, routingKey, message);
	}

}
