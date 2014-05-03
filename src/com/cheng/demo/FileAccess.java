package com.cheng.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FileAccess extends CommonActivity implements OnClickListener {
	String file = "files.txt";
	TextView info;
	EditText input;
	Button read;
	Button readSD;
	Button write;
	Button writeSD;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.file_access_main);
		this.read = ((Button) findViewById(R.id.file_read));
		this.write = ((Button) findViewById(R.id.file_write));
		this.readSD = ((Button) findViewById(R.id.file_readsd));
		this.writeSD = ((Button) findViewById(R.id.file_writesd));
		this.input = ((EditText) findViewById(R.id.file_access_main_input));
		this.info = ((TextView) findViewById(R.id.file_access_info));
		this.read.setOnClickListener(this);
		this.write.setOnClickListener(this);
		this.readSD.setOnClickListener(this);
		this.writeSD.setOnClickListener(this);
	}

	private String read(String file) {
		String str1 = "";
		try {
			InputStream in = openFileInput(file);
			BufferedReader buff = new BufferedReader(new InputStreamReader(in));
			String s = null;
			while ((s = buff.readLine()) != null) {
				str1 += s;
			}
			buff.close();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return str1;
	}

	private String readSD(String file) {
		StringBuilder sb = new StringBuilder();
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String path = Environment.getExternalStorageDirectory()
						.getCanonicalPath() + "/" + file;
				BufferedReader buff = new BufferedReader(new InputStreamReader(
						new FileInputStream(path)));
				String str = "";
				while ((str = buff.readLine()) != null) {
					sb.append(str + "\n");
				}
				System.out.println(sb.toString());
				buff.close();
			} else {
				showHint("sd卡不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private boolean write(String file, String content) {
		try {
			OutputStream out = openFileOutput(file, Context.MODE_APPEND);
			PrintStream ps = new PrintStream(out);
			ps.println(content);
			ps.flush();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean writeSD(String file, String content) {
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String path = Environment.getExternalStorageDirectory()
						.getCanonicalPath() + "/" + file;
				File tmp = new File(path);
				if (!tmp.exists()) {
					tmp.createNewFile();
					System.out.println("创建文件--->");
				}
				// 此处使用的随机流写入
				RandomAccessFile raf = new RandomAccessFile(tmp, "rw");
				raf.seek(tmp.length());
				raf.write(content.getBytes());
				raf.writeBytes("\n");
				raf.close();
				// 使用打印流更加方便----但是内容会被覆盖；只能用随机访问流
				// OutputStream out=new FileOutputStream(path);
				// PrintStream ps=new PrintStream(out);
				// System.out.println(content+"---");
				// ps.println(content);
				// ps.flush();
				// ps.close();
			}
			return true;
		} catch (Exception e) {
			System.out.println("数据写入失败");
			e.printStackTrace();
		}
		return false;
	}

	public void onClick(View paramView) {
		int id = paramView.getId();
		String inputContent = this.input.getText().toString().trim();
		String readContent = "";
		switch (id) {
		case R.id.file_write:
			if (inputContent.equals("")) {
				showHint("请输入文件内容！");
			} else {
				if (write(this.file, inputContent)) {
					this.input.setText("");
				} else {
					showHint("数据写入失败！");
				}
			}
			break;
		case R.id.file_read:
			this.info.setText("");
			readContent = read(this.file);
			if (readContent.equals("")) {
				this.info.setText("没有数据");
			} else {
				this.info.setText(readContent);
			}
			break;
		case R.id.file_writesd:
			if (inputContent.equals("")) {
				showHint("请输入文件内容！");
			} else {
				if (writeSD(this.file, inputContent)) {
					showHint("数据写入成功！");
					this.input.setText("");
				} else {
					showHint("数据写入失败！");
				}
			}
			break;
		case R.id.file_readsd:
			info.setText("");
			readContent = readSD(file);
			if (readContent.equals("")) {
				this.info.setText("文件还没有创建！");
			} else {
				this.info.setText(readContent);
			}
		}
	}
}
