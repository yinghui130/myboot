package cn.customs.myboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.customs.myboot.file.MybootFileListener;
import cn.customs.myboot.file.MybootFileMonitor;

/**
 * Hello world!
 *
 */
@RestController
@SpringBootApplication
@EnableTransactionManagement
public class App {
//	@Autowired
//	private SendMessage sender;

	@RequestMapping("/")
	String home() {
//		sender.send("liu");
		return "hello wolrd!";
	}

	public static void main(String[] args) {
		System.out.println("Hello World!");
		SpringApplication.run(App.class, args);

		try {
			Thread.sleep(1000 * 10);
			MybootFileMonitor monitor = new MybootFileMonitor(10000);
			monitor.monitor("d:/test", new MybootFileListener());
			monitor.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
