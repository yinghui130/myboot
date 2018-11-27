package cn.customs.myboot.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

public class MessageSender {
	Connection connection = null;
	Session session = null;
	Destination destination = null;
	MessageProducer producer = null;

	public MessageSender(JmsConfig jmsConfig) throws JMSException {
		JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
		JmsConnectionFactory cf = ff.createConnectionFactory();

		// Set the properties
		cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, jmsConfig.getHost());
		cf.setIntProperty(WMQConstants.WMQ_PORT, jmsConfig.getPort());
		cf.setStringProperty(WMQConstants.WMQ_CHANNEL, jmsConfig.getChannel());
		cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
		cf.setStringProperty(WMQConstants.USERID, jmsConfig.getUsername());
		cf.setStringProperty(WMQConstants.PASSWORD, jmsConfig.getPassword());
		cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
		connection = cf.createConnection();
		session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue("Q1");
		producer = session.createProducer(destination);
	}

	public void Send(Message message) throws JMSException {
		connection.start();
		producer.send(message);
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
