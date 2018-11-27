package cn.customs.myboot.file;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.BytesMessage;
import javax.print.attribute.standard.DateTimeAtCompleted;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import cn.customs.myboot.config.DirConfig;
import cn.customs.myboot.jms.SendMessage;
import cn.customs.myboot.utils.DateUtils;
import cn.customs.myboot.utils.FileUtils;
import cn.customs.myboot.utils.SpringUtils;

public class MybootFileListener implements FileAlterationListener {
	private SendMessage sender;
	private DirConfig dirConfig;
	private BytesMessage bytesMessage;

	public MybootFileListener() {
		// TODO Auto-generated constructor stub
		this.sender = SpringUtils.getBean(SendMessage.class);
		this.dirConfig = SpringUtils.getBean(DirConfig.class);
		this.bytesMessage = SpringUtils.getBean(BytesMessage.class);
	}

	@Override
	public void onStart(FileAlterationObserver observer) {
		// TODO Auto-generate method stub
		System.out.println("onStart");
	}

	@Override
	public void onDirectoryCreate(File directory) {
		// TODO Auto-generated method stub
		System.out.println("onDirectoryCreate:" + directory.getName());
	}

	@Override
	public void onDirectoryChange(File directory) {
		// TODO Auto-generated method stub
		System.out.println("onDirectoryChange:" + directory.getName());
	}

	@Override
	public void onDirectoryDelete(File directory) {
		// TODO Auto-generated method stub
		System.out.println("onDirectoryDelete:" + directory.getName());
	}

	@Override
	public void onFileCreate(File file) {
		// TODO Auto-generated method stub
		System.out.println("onFileCreate :" + file.getName());

		try {
			if (file.isFile()) {
				String dirPath = file.getParent();
				File[] fileList = new File(dirPath).listFiles();
				for (File f : fileList) {
					if (f.isFile()) {
						try {
							byte[] content = FileUtils.getRandomAccessFileContent(f);
							if (content.length > 0) {
								bytesMessage.clearBody();
								bytesMessage.clearProperties();
								bytesMessage.writeBytes(content);
								bytesMessage.setStringProperty("fileName", f.getName());
								bytesMessage.setJMSMessageID(f.getName());
								sender.send(bytesMessage);
								
							}
							Path backup = Paths.get(dirConfig.getBackupDir(), "Send",
									DateUtils.getDateString("yyyy-MM-dd"));
							FileUtils.moveFile(f, backup.toString());
						} catch (Exception e1) {
							// TODO: handle exception
							e1.printStackTrace();
						}

					}
				}
			}
		} catch (Exception e) { // TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onFileChange(File file) {
		// TODO Auto-generated method stub
		System.out.println("onFileChange :" + file.getName());
	}

	@Override
	public void onFileDelete(File file) {
		// TODO Auto-generated method stub
		System.out.println("onFileDelete :" + file.getName());
	}

	@Override
	public void onStop(FileAlterationObserver observer) {
		// TODO Auto-generated method stub
		System.out.println("onStop");
	}

}
