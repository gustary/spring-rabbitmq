package rabbitmq.producer.biz.impl;

import java.io.IOException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rabbitmq.producer.biz.MessageProducerBiz;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ReturnListener;

/**
 * 文件名：MessageProducer.java
 * 描述：
 * 作者：kj00023
 * 日期：2017年6月27日下午2:37:41
 */
@Service("messageProducerBiz")
public class MessageProducerBizImpl implements MessageProducerBiz {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Autowired
	private CachingConnectionFactory rabbitConnectionFactory;

	@Autowired
	private RabbitAdmin rabbitAdmin;

	/**
	 * 发布 message
	 * rabbitTemplate绑定exchange,routingKey属性值
	 * @param message
	 */
	public void mqRoute(Object message) {
		rabbitTemplate.convertAndSend(message);
	}

	/**
	 * 发布 message
	 * @param exchange 路由器
	 * @param routingKey 路由规则
	 * @param message
	 * 作者：kj00023
	 * 日期：2017年6月29日上午11:33:10
	 */
	public void mqRoute(String exchange, String routingKey, Object message) {
		rabbitTemplate.convertAndSend(exchange, routingKey, message);

	}

	/**
	 * 监听器监听方法参数
	 * @param message
	 * 作者：kj00023
	 * 日期：2017年6月27日下午3:56:08
	 */
	public void listenerMethodParamter(Object message) {
		rabbitTemplate.convertAndSend("topic.test", message);

	}

	/**
	 * 基于RPC(远程调用等待消息回复)
	 * @param exchange 路由器
	 * @param routingKey 路由规则
	 * @param message
	 * 作者：kj00023
	 * 日期：2017年6月28日上午10:47:28
	 */
	public Object sendAndReceive(String exchange, String routingKey, Object message) {
		return rabbitTemplate.convertSendAndReceive(message);
	}

	/**
	 * 发送message监听message是否会被路由到queue N 保存 message
	 * @param message
	 * 作者：kj00023
	 * 日期：2017年6月28日上午10:49:23
	 * @throws IOException 
	 */
	public void sendNoqueueMessage(Object message) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = (Connection) rabbitConnectionFactory.createConnection();
		Channel channel = connection.createChannel();
		// TODO 未处理具体发送

		rabbitTemplate.convertAndSend(message);

		// 问题 chanel不是同一,是否能监听到 预期不能
		channel.addReturnListener(new ReturnListener() {
			public void handleReturn(int arg0, String arg1, String arg2, String arg3, BasicProperties arg4, byte[] arg5)
					throws IOException {
				String message = new String(arg5);
				System.out.println("Basic.Return返回的结果:  " + message);
			}
		});
	}

}
