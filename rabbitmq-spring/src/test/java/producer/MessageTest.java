package producer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import rabbitmq.producer.biz.MessageProducerBiz;

/**
 * 文件名：MessageTest.java
 * 描述：
 * 作者：kj00023
 * 日期：2017年6月27日下午2:41:20
 */
public class MessageTest {

	private ApplicationContext context = null;

	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	/**
	 * 测试发送消息
	 * @throws Exception
	 * 作者：kj00023
	 * 日期：2017年6月29日上午11:27:06
	 */
	@Test
	public void TestSendMessage() throws Exception {
		MessageProducerBiz messageProducer = (MessageProducerBiz) context.getBean("messageProducerBiz");
		int a = 3;
		while (a > 0) {
			// messageProducer.sendMessage("Hello, I am amq sender num :" +
			// a--);
			/* 发布fanout消息 */
			messageProducer.mqRoute("testEx", "hello", "test fanoutExchange" + a--);
			try {
				// 暂停一下，好让消息消费者去取消息打印出来
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
