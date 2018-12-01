package cn.customs.myboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class DirConfig {
	@Value("${project.file.sendDir}")
	private String sendDir;

	@Value("${project.file.backupDir}")
	private String backupDir;
	
	@Value("${project.enableReceiver}")
	private Boolean enableReceiver;
}
