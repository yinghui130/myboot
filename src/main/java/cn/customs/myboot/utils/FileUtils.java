package cn.customs.myboot.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.KeyStore.TrustedCertificateEntry;

public class FileUtils {
	public static void moveFile(File file, String destDir) throws IOException {
		File destPath = new File(destDir);
		Path source = Paths.get(file.getPath());
		Path destFile = Paths.get(destDir, file.getName());
		if (!destPath.exists())
			destPath.mkdirs();
		Files.move(source, destFile, StandardCopyOption.REPLACE_EXISTING);
	}

	public static byte[] getRandomAccessFileContent(File file) throws Exception {
		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rws");
		int length = (int) file.length();
		byte[] content = new byte[length];
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

	public static String getExtensionName(File file) {
		String filename = file.getName();
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	public static String readFile(File file) throws Exception {
		StringBuffer buffer = new StringBuffer();
		InputStream is = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = reader.readLine();
		while (line != null) { // 如果 line 为空说明读完了
			buffer.append(line); // 将读到的内容添加到 buffer 中
			line = reader.readLine(); // 读取下一行
		}
		reader.close();
		is.close();
		return buffer.toString();
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
