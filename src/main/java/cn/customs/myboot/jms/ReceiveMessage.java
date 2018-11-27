package cn.customs.myboot.jms;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.jms.BytesMessage;
import javax.jms.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.listener.adapter.MessagingMessageListenerAdapter;
import org.springframework.stereotype.Component;

import cn.customs.myboot.config.DirConfig;
import cn.customs.myboot.utils.DateUtils;
import cn.customs.myboot.utils.FileUtils;
import cn.customs.myboot.utils.SpringUtils;

//@Component
public class ReceiveMessage extends MessagingMessageListenerAdapter {
	//@Autowired
	JmsOperations jmsOperations;
	

	//@Override
	//@JmsListener(destination = "Q1")
	public void onMessage(Message message) {
		DirConfig dirConfig=SpringUtils.getBean(DirConfig.class);
		BytesMessage bytesMessage = (BytesMessage) message;
		try {
			int length=(int)bytesMessage.getBodyLength();
			byte[] content=new byte[length];
			bytesMessage.readBytes(content);
			String messageBody = new String(bytesMessage.toString());
			System.out.println("成功监听Q1消息队列，传来的值为:" + messageBody);
			String fileName=message.getStringProperty("fileName");
			Path filePath=Paths.get(dirConfig.getBackupDir(),"Recv",DateUtils.getDateString("yyyy-MM-dd"),DateUtils.getDateString("hhmmssSSS")+"_"+fileName);
			File file =filePath.toFile();
			if(!file.getParentFile().exists())
			{
				file.getParentFile().mkdirs();
			}
			FileUtils.writeFile(file, content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
