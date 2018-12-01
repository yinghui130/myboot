package cn.customs.myboot.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JsonSettings {

	private String backupDir;
	private List<DestInfo> destList;
	private static JsonSettings jsonSettings;
	private static List<DestInfo> recvDests = new ArrayList<DestInfo>();

	public void setBackupDir(String backupDir) {
		this.backupDir = backupDir;
	}

	public String getBackupDir() {
		return backupDir;
	}

	public void setDestList(List<DestInfo> destList) {
		this.destList = destList;
	}

	public List<DestInfo> getDestList() {
		return destList;
	}

	public DestInfo getDestByWorkDir(String workDir) {
		DestInfo destInfo = null;
		if (destList == null || destList.size() == 0)
			return destInfo;
		for (DestInfo dest : destList) {
			if (dest.getWorkDir() == workDir) {
				destInfo = dest;
				break;
			}
		}
		return destInfo;
	}

	public List<DestInfo> getAllRecvDests() {
		if (recvDests == null) {
			if (destList == null || destList.size() == 0) {
				return recvDests;
			}
		}
		for (DestInfo dest : destList) {
			if (dest.getSendFlag() == false) {
				recvDests.add(dest);
			}
		}
		return recvDests;

	}

	@Bean
	public JsonSettings getJsonSettings() throws JsonParseException, JsonMappingException, IOException {
		if (jsonSettings == null) {
			ObjectMapper mapper = new ObjectMapper();
			jsonSettings = mapper.readValue(new File("/home/tom/test/settings.json"), JsonSettings.class);
		}
		return jsonSettings;
	}
}
