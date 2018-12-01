package cn.customs.myboot.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsOperations;

import com.ibm.mq.jms.MQQueueConnectionFactory;

import cn.customs.myboot.jms.config.ReceiverJmsConfig;
import cn.customs.myboot.jms.listener.MyJmsListener;
import cn.customs.myboot.utils.SpringUtils;

public class MessageReceiver {
	JmsOperations jmsOperations;
	Session session;
	MessageConsumer messageConsumer;
	Connection connection;
	MyJmsListener listener;
	Destination destination;

	public MessageReceiver() throws JMSException {
		ReceiverJmsConfig jmsConfig = SpringUtils.getBean(ReceiverJmsConfig.class);
		MQQueueConnectionFactory mqQueueConnectionFactory = jmsConfig.mqQueueConnectionFactory();
		UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = jmsConfig
				.userCredentialsConnectionFactoryAdapter(mqQueueConnectionFactory);

		CachingConnectionFactory cachingConnectionFactory = jmsConfig
				.cachingConnectionFactory(userCredentialsConnectionFactoryAdapter);
		jmsOperations = jmsConfig.jmsOperations(cachingConnectionFactory);
		connection = userCredentialsConnectionFactoryAdapter.createConnection();
		session = connection.createSession();
		connection.start();
		Destination destination = session.createQueue(jmsConfig.getQueue());
		messageConsumer = session.createConsumer(destination);
		listener = new MyJmsListener();
		listener.setQueue(jmsConfig.getQueue());
		messageConsumer.setMessageListener(listener);
		System.out.println("MessageReceiver end");
	}

	public void Close() throws JMSException {
		if (messageConsumer != null)
			messageConsumer.close();
		if(session!=null)
			session.close();
		if(connection!=null)
			connection.close();
	}

}
