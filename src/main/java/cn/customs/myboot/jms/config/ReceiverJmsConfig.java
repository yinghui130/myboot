package cn.customs.myboot.jms.config;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import cn.customs.myboot.config.Send;
import lombok.Data;

@Configuration
@Data
public class ReceiverJmsConfig {
	@Value("${project.mq.queue}")
	private String queue;
	@Value("${project.mq.host}")
	private String host;
	@Value("${project.mq.port}")
	private Integer port;
	@Value("${project.mq.queue-manager}")
	private String queueManager;
	@Value("${project.mq.channel}")
	private String channel;
	@Value("${project.mq.username}")
	private String username;
	@Value("${project.mq.password}")
	private String password;
	@Value("${project.mq.receive-timeout}")
	private long receiveTimeout;
	@Value("${project.mq.ccsid}")
	private int ccsid;

	public static ReceiverJmsConfig getJmsConfig(Send send) {
		ReceiverJmsConfig jmsConfig = new ReceiverJmsConfig();
		jmsConfig.host = send.getMqhost();
		jmsConfig.port = send.getMqport();
		jmsConfig.receiveTimeout = send.getMqreceivetimeout();
		jmsConfig.queueManager = send.getMqqueuemanager();
		jmsConfig.ccsid = send.getMqccsid();
		jmsConfig.username = send.getMqusername();
		jmsConfig.password = send.getPassword();
		jmsConfig.channel = send.getMqchannel();
		return jmsConfig;
	}

	@Bean
	public MQQueueConnectionFactory mqQueueConnectionFactory() {
		MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
		mqQueueConnectionFactory.setHostName(host);
		try {// WMQConstants.WMQ_CM_CLIENT
			mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
			mqQueueConnectionFactory.setCCSID(ccsid);
			mqQueueConnectionFactory.setChannel(channel);
			mqQueueConnectionFactory.setPort(port);
			mqQueueConnectionFactory.setQueueManager(queueManager);
			mqQueueConnectionFactory.setAppName("myboot");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mqQueueConnectionFactory;
	}

	@Bean
	public UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter(
			MQQueueConnectionFactory mqQueueConnectionFactory) {
		UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
		userCredentialsConnectionFactoryAdapter.setUsername(username);
		userCredentialsConnectionFactoryAdapter.setPassword(password);
		userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionFactory);

		return userCredentialsConnectionFactoryAdapter;
	}

	@Bean
	@Primary
	public CachingConnectionFactory cachingConnectionFactory(
			UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter) {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setTargetConnectionFactory(userCredentialsConnectionFactoryAdapter);
		cachingConnectionFactory.setSessionCacheSize(500);
		cachingConnectionFactory.setReconnectOnException(true);
		return cachingConnectionFactory;
	}

	@Bean
	public PlatformTransactionManager jmsTransactionManager(CachingConnectionFactory cachingConnectionFactory) {
		JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
		jmsTransactionManager.setConnectionFactory(cachingConnectionFactory);
		return jmsTransactionManager;
	}

	@Bean
	BytesMessage usrBytesMessage(UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter)
			throws JMSException {
		Session session = userCredentialsConnectionFactoryAdapter.createConnection().createSession();
		return session.createBytesMessage();
	}

    @Bean
	public JmsOperations jmsOperations(CachingConnectionFactory cachingConnectionFactory) {
		JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
		jmsTemplate.setReceiveTimeout(receiveTimeout);
		return jmsTemplate;
	}
}
