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

	@Bean
	public ExitCodeGenerator exitCodeGenerator() {

		try {
			if (App.messageReceiver != null)
				App.messageReceiver.Close();
			if (App.mybootFileMonitor != null)
				App.mybootFileMonitor.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("App is ending!");
		return () -> 42;
	}

	public static void main(String[] args) {
		System.out.println("Hello World!");
		SpringApplication.run(App.class, args);

		/*
		 * try { Thread.sleep(1000 * 10); MybootFileMonitor monitor = new
		 * MybootFileMonitor(10000); monitor.monitor("d:/test", new
		 * MybootFileListener()); monitor.start(); } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
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
