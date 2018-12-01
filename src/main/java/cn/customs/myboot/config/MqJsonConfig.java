package cn.customs.myboot.config;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class MqJsonConfig {
	
	private List<Send> send;

	public List<Send> findByFileType(String fileType) {
		List<Send> list = new ArrayList<Send>();
		for (Send temp : send) {
			if(temp.getFileType().toLowerCase().equals(fileType.toLowerCase()))
				list.add(temp);
		}
		return list;
	}

	
}
