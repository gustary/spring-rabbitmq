package rabbitmq.service;

/**
 * 文件名：RabbitMQRouteService.java
 * 描述：
 * 作者：kj00023
 * 日期：2017年6月29日下午3:37:38
 */
public interface RabbitMQRouteService {

	public void mqRoute(Object message);

	public void mqRoute(String exchange, String routingKey, Object message);
}
