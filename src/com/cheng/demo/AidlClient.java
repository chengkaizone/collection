package com.cheng.demo;

import java.util.List;

import sutras.cheng.service.AndroidService;
import sutras.cheng.service.Person;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 作为aidl远程调用的客户端
 * @author Administrator
 *
 */
public class AidlClient extends Activity implements OnClickListener {
	private Button btn1;
	private Button btn2;
	private EditText name;
	private EditText pet;
	private AndroidService service;
	private ServiceConnection conn = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder binder) {
			// 获取远程Service的onBind方法返回的对象代理
			service = AndroidService.Stub.asInterface(binder);
		}

		public void onServiceDisconnected(ComponentName name) {
			// 当服务未连接时
			service = null;// 便于垃圾回收
		}
	};

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.aidl_main);
		btn1 = (Button) findViewById(R.id.aidl_btn1);
		btn2 = (Button) findViewById(R.id.aidl_btn2);
		name = (EditText) findViewById(R.id.aidl_name);
		pet = (EditText) findViewById(R.id.aidl_person);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		Intent intent = new Intent();
		//指向sutras应用的service组件
		intent.setAction("sutras.cheng.service.action.AIDL_SERVICE");
		this.bindService(intent, conn, Service.BIND_AUTO_CREATE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.aidl_btn1:
			try {
				String str = service.getName();
				double sal = service.getSalary();
				Person person=new Person("甘英杰", 27, "男");
				String info=service.print(person);
				Toast.makeText(AidlClient.this, info, 1).show();
				name.setText("姓名：" + str + "工资：" + sal);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		case R.id.aidl_btn2:
			try {
				List<Person> persons = service.getPersons();
				String str = "";
				Person person = null;
				for (int i = 0; i < persons.size(); i++) {
					person = persons.get(i);
					str += person.getName() + "\t" + person.getAge() + "\t"
							+ person.getSex() + "\n";
				}
				pet.setText(str);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unbindService(conn);
	}

}
