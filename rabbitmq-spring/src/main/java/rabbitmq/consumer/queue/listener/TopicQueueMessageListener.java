package rabbitmq.consumer.queue.listener;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import rabbitmq.util.GlobalVar;

import com.rabbitmq.client.Channel;

/**
 * 文件名：TopicMessageListener.java
 * 描述：
 * 作者：kj00023
 * 日期：2017年6月27日下午2:27:14
 */
@Component("topicQueueMessageListener")
public class TopicQueueMessageListener implements ChannelAwareMessageListener {

	public void onMessage(Message message, Channel channel) throws IOException {
		// Object obj = null;
		String messageHandleStatus = "message_handle_accept";
		try {
			// obj = msgConverter.fromMessage(message);
			System.out.println("TopicQueueMessageListener onMessage method do topic--" + message);
		} catch (Exception e) {
			// 根据异常种类决定是ACCEPT、RETRY还是 REJECT

		} finally {
			if (GlobalVar.MESSAGE_HANDLE_ACCEPT.equals(messageHandleStatus)) {
				// 确认消息，false只确认当前一个消息收到，true确认所有consumer获得的消息
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			} else if (GlobalVar.MESSAGE_HANDLE_RETRY.equals(messageHandleStatus)) {
				// ack返回false，并重新回到队列 第一个boolean参数：是否批量 ，第二个参数：被拒绝的是否重新入队列
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
			} else {
				/** 入队尾 */
				// channel.basicAck(message.getMessageProperties().getDeliveryTag(),
				// false);
				// channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
				// message
				// .getMessageProperties().getReceivedRoutingKey(),
				// MessageProperties.PERSISTENT_TEXT_PLAIN,
				// message.getBody());
				/** 拒绝消息 */
				// channel.basicReject(message.getMessageProperties().getDeliveryTag(),
				// false);
			}
		}

	}

}
