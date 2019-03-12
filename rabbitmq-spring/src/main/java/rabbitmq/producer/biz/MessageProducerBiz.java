package rabbitmq.producer.biz;

/**
 * 文件名：MessageProducerBiz.java
 * 描述：
 * 作者：kj00023
 * 日期：2017年6月29日下午3:45:44
 */
public interface MessageProducerBiz {

	public void mqRoute(Object message);

	public void mqRoute(String exchange, String routingKey, Object message);
}
