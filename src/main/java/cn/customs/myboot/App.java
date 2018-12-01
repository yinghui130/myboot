package cn.customs.myboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.customs.myboot.config.DirConfig;
import cn.customs.myboot.file.MybootFileListener;
import cn.customs.myboot.file.MybootFileMonitor;
import cn.customs.myboot.jms.MessageReceiver;
import cn.customs.myboot.utils.SpringUtils;

/**
 * Hello world!
 *
 */
@RestController
@SpringBootApplication
@EnableTransactionManagement
public class App implements CommandLineRunner {
	public static MybootFileMonitor mybootFileMonitor;
	public static MessageReceiver messageReceiver;

	@RequestMapping("/")
	String home() {
		return "hello wolrd!";
	}
	public static void main(String[] args) {
		System.out.println("Hello World!");
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		DirConfig dirConfig = SpringUtils.getBean(DirConfig.class);
		try {
			messageReceiver = new MessageReceiver();
			mybootFileMonitor = new MybootFileMonitor(10000);
			mybootFileMonitor.monitor(dirConfig.getSendDir(), new MybootFileListener());
			mybootFileMonitor.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
