package cn.customs.myboot.jms;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.bouncycastle.asn1.cmc.BodyPartID;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsOperations;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import cn.customs.myboot.jms.config.SendJmsConfig;
import cn.customs.myboot.utils.SpringUtils;

public class MessageSender {
	Connection connection = null;
	Session session = null;
	Destination destination = null;
	MessageProducer producer = null;
	JmsOperations jmsOperations;
	
	public MessageSender(SendJmsConfig jmsConfig) throws JMSException {
		MQQueueConnectionFactory mqQueueConnectionFactory = jmsConfig.mqQueueConnectionFactory();
		UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = jmsConfig
				.userCredentialsConnectionFactoryAdapter(mqQueueConnectionFactory);

		CachingConnectionFactory cachingConnectionFactory = jmsConfig
				.cachingConnectionFactory(userCredentialsConnectionFactoryAdapter);
		jmsOperations = jmsConfig.jmsOperations(cachingConnectionFactory);
		connection = userCredentialsConnectionFactoryAdapter.createConnection();
		connection.start();
		session = connection.createSession();
		Destination destination = session.createQueue("Q1");
		producer = session.createProducer(destination);
		System.out.println("MessageReceiver end");
	}

	public BytesMessage getBytesMessage() throws JMSException {
		return this.session.createBytesMessage();
	}

	public void Send(Message message) throws JMSException {
		System.out.println("Message Sender");
		producer.send(message);
		//session.commit();
	}

	public void Close() throws JMSException {
		if (producer != null)
			producer.close();
		if (session != null)
			session.close();
		if (connection != null)
			connection.close();
	}
}
