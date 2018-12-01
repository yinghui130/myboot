package cn.customs.myboot.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.customs.myboot.jms.SendMessage;
import cn.customs.myboot.utils.FileUtils;

@Configuration
public class BeanConfig {
	@Value("${project.jsonConfigFilePath}")
	private String jsonConfigFilePath;
	@Bean
	public MqJsonConfig getMqJsonConfig() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = FileUtils.readFile(new File(jsonConfigFilePath));
		MqJsonConfig mqJsonConfig = mapper.readValue(jsonString, MqJsonConfig.class);
		return mqJsonConfig;

	}
}