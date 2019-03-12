package rabbitmq.producer.biz.impl;

/**
 * 文件名：Producer.java
 * 描述：
 * 作者：kj00023
 * 日期：2017年6月27日上午9:23:27
 */
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//生产者  
public class ProducerTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		RabbitTemplate rabbitTemplate = (RabbitTemplate) context.getBean("rabbitTemplate");

		/* 模板配置exchange,routingKey */
		// rabbitTemplate.convertAndSend("test spring sync");
		// System.out.println("test spring sync");

		String message = "Hello World 22 ";
		rabbitTemplate.convertAndSend("topicExchangeTest", "topic.test", message);
		System.out.println("发送消息成功！内容为：" + message);
	}
}