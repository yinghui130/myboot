package cn.customs.myboot.jms;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class JmsRunner implements ApplicationRunner {
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("liuyinghui running!");
		new MessageReceiver();
	}

}
