package com.cheng.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class FileUtil {

	public static String downFile(String url) {
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		String tmp = "";
		try {

			reader = new BufferedReader(new InputStreamReader(
					getInputStream(url)));
			while ((tmp = reader.readLine()) != null) {
				builder.append(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	/**
	 * ����0�����ļ��Ѿ����ڣ�����1 �����ļ����سɹ�������-1�����ļ�����ʧ��
	 * 
	 * @param url
	 *            ����url;
	 * @param path
	 *            �ֻ��洢·��;Ĭ��sd��Ϊ��·��
	 * @param fileName
	 *            �洢�ļ���
	 * @return
	 */
	public static int downFile(String url, String path, String fileName) {
		File dir = Environment.getExternalStorageDirectory();
		File dir1 = new File(dir + "/" + path);
		if (!dir1.exists()) {
			dir1.mkdir();
		}
		String tmp = dir1 + "/" + fileName;
		File file = new File(tmp);
		PrintStream ps = null;
		if (file.exists()) {
			return 0;
		} else {
			try {
				file.createNewFile();
				OutputStream out = new FileOutputStream(file);
				InputStream input = getInputStream(url);
				byte[] brr = new byte[4 * 1024];
				int i = 0;
				ps = new PrintStream(out);
				while ((i = input.read(brr)) != -1) {
					System.out.println("ƫ����--->" + i);
					ps.write(brr, 0, i);
				}
				ps.flush();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}
		return 1;
	}

	public static InputStream getInputStream(String url) {
		InputStream input = null;
		HttpClient client = new DefaultHttpClient();
		try {
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			input = response.getEntity().getContent();
		} catch (Exception e) {
			System.out.println("�Ƿ������쳣");
			e.printStackTrace();
		}
		return input;
	}

	public static Bitmap getBitmap(String url) {
		return BitmapFactory.decodeStream(getInputStream(url));
	}
}
