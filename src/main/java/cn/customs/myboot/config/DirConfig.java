package cn.customs.myboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class DirConfig {
	@Value("${project.file.sendDir}")
	private String sendDir;

	@Value("${project.file.backupDir}")
	private String backupDir;
	
	public String getSendDir() {
		return sendDir;
	}
	public String getBackupDir() {
		return backupDir;
	}
}
