package cn.customs.myboot.file;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.customs.myboot.config.DirConfig;
import cn.customs.myboot.config.MqJsonConfig;
import cn.customs.myboot.config.Send;
import cn.customs.myboot.file.filter.SendFilter;
import cn.customs.myboot.file.filter.impl.FileSendFilter;
import cn.customs.myboot.jms.MessageSender;
import cn.customs.myboot.jms.config.SendJmsConfig;
import cn.customs.myboot.utils.DateUtils;
import cn.customs.myboot.utils.FileUtils;
import cn.customs.myboot.utils.SpringUtils;

public class MybootFileListener implements FileAlterationListener {
	private FileSendFilter fileSendFilter;
	public MybootFileListener() {
		this.fileSendFilter=SpringUtils.getBean(FileSendFilter.class);
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
		System.out.println("onFileCreate :" + file.getName());
		try {
			fileSendFilter.SendHandler(file.getParent());
		} catch (Exception e) {
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
