package cn.customs.myboot.file.filter.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.jms.BytesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.customs.myboot.config.DirConfig;
import cn.customs.myboot.config.MqJsonConfig;
import cn.customs.myboot.config.Send;
import cn.customs.myboot.file.filter.SendFilter;
import cn.customs.myboot.jms.MessageSender;
import cn.customs.myboot.jms.config.SendJmsConfig;
import cn.customs.myboot.utils.DateUtils;
import cn.customs.myboot.utils.FileUtils;
import cn.customs.myboot.utils.SpringUtils;

@Service
public class FileSendFilter implements SendFilter {
	@Autowired
	private DirConfig dirConfig;
	@Autowired
	private MqJsonConfig mqJsonConfig;

	@Override
	public void SendHandler(String dirPath) throws Exception{
		File[] fileList = new File(dirPath).listFiles();
		for (File f : fileList) {
			if (f.isFile()) {
				String fileExt = FileUtils.getExtensionName(f);
				List<Send> sendList = mqJsonConfig.findByFileType(fileExt);
				byte[] content = FileUtils.getRandomAccessFileContent(f);
				MessageSender tempMessageSender =null;
				try {
					for (Send send : sendList) {
						SendJmsConfig tempJmsConfig = SendJmsConfig.getJmsConfig(send);
						tempMessageSender= new MessageSender(tempJmsConfig);
						BytesMessage tempBytesMessage = tempMessageSender.getBytesMessage();

						if (content.length > 0) {
							tempBytesMessage.clearBody();
							tempBytesMessage.clearProperties();
							tempBytesMessage.setStringProperty("fileName", f.getName());
							tempBytesMessage.setJMSMessageID(f.getName());
							tempBytesMessage.writeBytes(content);
							tempMessageSender.Send(tempBytesMessage);
							tempMessageSender.Close();
							tempBytesMessage=null;
						}
					}
				} catch (Exception e) {
					throw e;
				} finally {
					if (tempMessageSender != null) {
						tempMessageSender.Close();
					}
				}
				Path backup = Paths.get(dirConfig.getBackupDir(), "Send", DateUtils.getDateString("yyyy-MM-dd"));
				FileUtils.moveFile(f, backup.toString());
			}
		}

	}
}
