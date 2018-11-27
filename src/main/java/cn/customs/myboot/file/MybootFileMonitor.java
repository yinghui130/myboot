package cn.customs.myboot.file;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class MybootFileMonitor {
	FileAlterationMonitor fileAlterationMonitor;

	public MybootFileMonitor(long interval) {
		fileAlterationMonitor = new FileAlterationMonitor(interval);
	}

	public void monitor(String path, FileAlterationListener listener) {
		FileAlterationObserver observer = new FileAlterationObserver(new File(path));
		fileAlterationMonitor.addObserver(observer);
		observer.addListener(listener);
	}

	public void stop() throws Exception {
		fileAlterationMonitor.stop();
	}

	public void start() throws Exception {
		fileAlterationMonitor.start();
	}
	
	public static void main1(String[] args) throws Exception{
		MybootFileMonitor monitor=new MybootFileMonitor(10000);
		monitor.monitor("d:/test",new MybootFileListener());
		monitor.start();
	}
}



























