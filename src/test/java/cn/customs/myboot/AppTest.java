package cn.customs.myboot;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.customs.myboot.config.MqJsonConfig;
import cn.customs.myboot.config.Send;


/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest 
{
	@Autowired
	MqJsonConfig mqJsonConfig;
	@Test
	public void testJsonFile() throws Exception
	{
		String jsonString="{\r\n" + 
				"			\"fileType\": \"Rufus\",\r\n" + 
				"			\"mqccsid\": \"1381\",\r\n" + 
				"			\"mqchannel\": \"CLIENT-QM_APPLE\",\r\n" + 
				"			\"mqhost\": \"192.168.1.168\",\r\n" + 
				"			\"mqqueue\": \"Q1\",\r\n" + 
				"			\"mqqueuemanager\": \"QM_APPLE\",\r\n" + 
				"			\"mqreceivetimeout\": \"2000\",\r\n" + 
				"			\"mqusername\": \"Administrator\",\r\n" + 
				"			\"password\": \"lyh130182\"\r\n" + 
				"		}";
		ObjectMapper mapper=new ObjectMapper();
		Send send=mapper.readValue(jsonString, Send.class);
		List<Send> list=mqJsonConfig.getSend();
	    System.out.println(list.size());
	}
}
