package com.winning.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author xch
 * @date 2022/1/7 16:11
 */
public class FileUtil {

	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String getContent(String filePath) throws IOException {
		return getContent(new File(filePath));
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getContent(File file) throws IOException {
		return new String(Files.readAllBytes(file.toPath()),
				StandardCharsets.UTF_8);
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(String filePath) throws IOException {
		return Files.readAllBytes(new File(filePath).toPath());
	}
	
	/**
	 * 
	 * @param content
	 * @param file
	 * @throws IOException
	 */
	public static void saveContent(String content, File file)
			throws IOException {
		try (FileOutputStream fos = new FileOutputStream(file);
				ByteArrayInputStream bis = new ByteArrayInputStream(
						content.getBytes())) {
			copy(bis, fos);
		}
	}
	
	public static void saveContent(InputStream is, File file) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(file)) {
			IOUtil.copy(is, fos);
		}
	}

	/**
	 * 
	 * @param data
	 * @param file
	 * @throws IOException
	 */
	public static void saveContent(byte[] data, File file) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(file);
				ByteArrayInputStream bis = new ByteArrayInputStream(data)) {
			copy(bis, fos);
		}
	}
	
	/**
	 * 
	 * @param input
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public static long copy(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[4096];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
}
