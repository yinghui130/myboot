package cn.customs.myboot.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {
	public static void moveFile(File file,String destDir) throws IOException
	{
		File destPath=new File(destDir);
		Path source = Paths.get(file.getPath());
		Path destFile = Paths.get(destDir,file.getName());
		if(!destPath.exists())
			destPath.mkdirs();
		Files.move(source,destFile,StandardCopyOption.REPLACE_EXISTING);
	}
	public static byte[] getRandomAccessFileContent(File file) throws Exception
	{
		RandomAccessFile randomAccessFile=	new RandomAccessFile(file, "rws");
		int length = (int)file.length();
		byte[] content= new byte[length];
		randomAccessFile.readFully(content);
		randomAccessFile.close();
		return content;
	}
	public static byte[] getFileContent(File file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);
		int length = (int) file.length();
		byte[] contentBytes = new byte[length];
		dis.readFully(contentBytes);
		fis.close();
		dis.close();
		return contentBytes;
	}

	public static void writeFile(File file, Object obj) throws Exception {
		byte[] content = toByteArray(obj);
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file.getPath())));
		dos.write(content);
		dos.flush();
		dos.close();
	}

	public static void writeFile(File file, byte[] content) throws Exception {
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file.getPath())));
		dos.write(content);
		dos.flush();
		dos.close();
	}

	public static byte[] toByteArray(Object obj) throws Exception {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(obj);
		oos.flush();
		bytes = bos.toByteArray();
		oos.close();
		bos.close();
		return bytes;
	}

}
