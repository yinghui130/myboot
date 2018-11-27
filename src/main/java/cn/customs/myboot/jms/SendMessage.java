package cn.customs.myboot.jms;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Component;

//@Component
public class SendMessage {
	@Autowired
	JmsOperations jmsOperations;
	@Autowired
	BytesMessage bytesMessage;
	@Value("${project.mq.queue}")
	private String queueName;

	// @PostConstruct在服务器加载Servle的时候运行，并且只会被服务器执行一次, @PreDestroy在destroy()方法执行执行之后执行
	//@PostConstruct
	public void send(String messageData) {
		System.out.println("开始发送消息");
		jmsOperations.convertAndSend(queueName, messageData);
	}
	public void send(Message message) throws JMSException {
		System.out.println("开始发送消息");
		jmsOperations.convertAndSend(queueName, message);
	}
	public void send(byte[] content) throws JMSException {
		System.out.println("开始发送消息");
		bytesMessage.writeBytes(content);
		jmsOperations.convertAndSend(queueName, bytesMessage);
	}
}
