package rabbitmq.consumer.queue.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import rabbitmq.util.GlobalVar;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

/**
 * 文件名：DirectMessage.java
 * 描述：
 * 作者：kj00023
 * 日期：2017年6月27日下午2:26:09
 */
@Service
public class DirectQueueMessageListener implements ChannelAwareMessageListener {

	public void onMessage(Message message, Channel channel) throws Exception {
		String messageHandleStatus = "message_handle_accept";
		try {
			System.out.println("dircet---" + message);
		} catch (Exception e) {
			// 根据异常种类决定是ACCEPT、RETRY还是 REJECT

		} finally {
			/*
			 * basicNack,basicReject的区别:
			 * basicNack可以拒绝多条消息，而basicReject一次只能拒绝一条消息
			 * basicAck，basicNack都可以作为消息回复将消息重新入队列 basicAck将消息重新入队没意义
			 * 每次重新入队都是入队首不是队尾，部分消息处理可能出现一直处理失败在处理问题，进而堵塞队列
			 * 部分处理异常的数据可以选择丢弃或保存，也可以重新入队到队尾，先手动确认应答消息在重新入队
			 * 处理成功用basicAck，处理失败可重试用basicNack重新入队，不可重试basicNack丢弃
			 */
			if (GlobalVar.MESSAGE_HANDLE_ACCEPT.equals(messageHandleStatus)) {
				// 确认消息，false只确认当前一个消息收到，true确认所有consumer获得的消息
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			} else if (GlobalVar.MESSAGE_HANDLE_RETRY.equals(messageHandleStatus)) {
				// ack返回false，并重新回到队列 第一个boolean参数：是否批量 ，第二个参数：被拒绝的是否重新入队列
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
			} else {
				/** 入队尾  循环再试*/
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				channel.basicPublish(message.getMessageProperties().getReceivedExchange(), message
						.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
						message.getBody());
				/** 发布到新死信队列 */
				// channel.basicReject(message.getMessageProperties().getDeliveryTag(),
				// false);

			}
		}
	}
}
