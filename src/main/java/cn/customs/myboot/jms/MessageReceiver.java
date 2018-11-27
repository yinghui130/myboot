package cn.customs.myboot.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.springframework.jms.core.JmsOperations;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

public class MessageReceiver {
	JmsOperations jmsOperations;
	Session session;
	MessageConsumer messageConsumer;
	Connection connection;
	MyJmsListener listener;
	Destination destination;

	public MessageReceiver() throws JMSException {
		System.out.println("MessageReceiver");
		// 使用IBM的例子
		JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
		JmsConnectionFactory cf = ff.createConnectionFactory();
		cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, "192.168.1.168");
		cf.setIntProperty(WMQConstants.WMQ_PORT, 1414);
		cf.setStringProperty(WMQConstants.WMQ_CHANNEL, "CLIENT.QM_APPLE");
		cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
		cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, "QM_APPLE");
		cf.setStringProperty(WMQConstants.USERID, "Administrator");
		cf.setStringProperty(WMQConstants.PASSWORD, "lyh130182");
		cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
		connection = cf.createConnection();
		connection.start();
		session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue("Q1");

		/*// spring boot 的操作方式
		JmsConfig jmsConfig = SpringUtils.getBean(JmsConfig.class);
		MQQueueConnectionFactory mqQueueConnectionFactory = jmsConfig.mqQueueConnectionFactory();
		UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = jmsConfig
				.userCredentialsConnectionFactoryAdapter(mqQueueConnectionFactory);

		CachingConnectionFactory cachingConnectionFactory = jmsConfig
				.cachingConnectionFactory(userCredentialsConnectionFactoryAdapter);
		jmsOperations = jmsConfig.jmsOperations(cachingConnectionFactory);
		connection = userCredentialsConnectionFactoryAdapter.createConnection();
		connection.start();
		session = connection.createSession();
		Destination destination = session.createQueue("Q1");*/
		session.createConsumer(destination);
		messageConsumer = session.createConsumer(destination);
		listener = new MyJmsListener();
		listener.setQueue("Q1");
		messageConsumer.setMessageListener(listener);
		System.out.println("MessageReceiver end");
	}

}
