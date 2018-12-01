package cn.customs.myboot.config;

import java.io.File;
import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.customs.myboot.utils.FileUtils;

@Configuration
public class BeanConfig {
	@Bean
	public MqJsonConfig getMqJsonConfig() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonConfigFilePath = Paths.get(System.getProperty("user.dir"),"conf/settings.json").toString();
		String jsonString = FileUtils.readFile(new File(jsonConfigFilePath));
		MqJsonConfig mqJsonConfig = mapper.readValue(jsonString, MqJsonConfig.class);
		return mqJsonConfig;

	}
}