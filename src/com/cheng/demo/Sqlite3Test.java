package com.cheng.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.adapter.PhoneAdapter;
import com.cheng.util.PhoneDatabaseHelper;

public class Sqlite3Test extends CommonActivity implements
		OnItemLongClickListener {

	private Cursor cursor;
	private SQLiteDatabase db;
	private PhoneDatabaseHelper helper;
	private LayoutInflater inflater;
	private ListView list;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.phone_data_main);
		this.list = ((ListView) findViewById(R.id.phone_data_list));
		setAnimation(list);
		this.inflater = getLayoutInflater();
		this.list.setOnItemLongClickListener(this);
		this.helper = new PhoneDatabaseHelper(this, "collections.db3", 2);
		this.db = this.helper.getWritableDatabase();
		String[] columns = { "name", "phone" };
		this.cursor = db.query("phones", columns, null, null, null, null, null);
		initList();
	}

	private void addUser() {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		View localView = this.inflater.inflate(R.layout.phone_add, null);
		final EditText input1 = (EditText) localView
				.findViewById(R.id.phone_add_name);
		final EditText input2 = (EditText) localView
				.findViewById(R.id.phone_add_phone);
		localBuilder.setTitle("添加用户").setView(localView)
				.setPositiveButton("确定", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String name = input1.getText().toString().trim();
						String phone = input2.getText().toString().trim();
						if (name.equals("") || phone.equals("")) {
							Toast.makeText(Sqlite3Test.this, "请输入完整信息！", 2000)
									.show();
						} else {
							helper.insertUser(name, phone);
							updateUI();
						}
					}
				}).create().show();
	}

	private void delUser(View paramView) {
		final String str1 = ((TextView) paramView
				.findViewById(R.id.phone_data_name)).getText().toString()
				.trim();
		final String str2 = ((TextView) paramView
				.findViewById(R.id.phone_data_phone)).getText().toString()
				.trim();
		new AlertDialog.Builder(this).setTitle("温馨提示！").setMessage("确定删除该用户！")
				.setView(null).setPositiveButton("确定", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Sqlite3Test.this.helper.deleteUser(str1, str2);
						Sqlite3Test.this.updateUI();
					}
				}).setNegativeButton("取消", null).create().show();
	}

	private void initList() {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (int i = 0; i < this.cursor.getCount(); ++i) {
			Map<String, String> map = new HashMap<String, String>();
			cursor.moveToPosition(i);
			String name = this.cursor.getString(cursor.getColumnIndex("name"));
			String phone = this.cursor
					.getString(cursor.getColumnIndex("phone"));
			map.put("name", name);
			map.put("phone", phone);
			data.add(map);
		}
		PhoneAdapter localPhoneAdapter = new PhoneAdapter(this, data);
		this.list.setAdapter(localPhoneAdapter);
	}

	private void updateUI() {
		this.cursor.requery();
		initList();
	}

	private void updateUser(View paramView) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		final String str1 = ((TextView) paramView
				.findViewById(R.id.phone_data_name)).getText().toString()
				.trim();
		final String str2 = ((TextView) paramView
				.findViewById(R.id.phone_data_phone)).getText().toString()
				.trim();
		View localView = this.inflater.inflate(R.layout.phone_add, null);
		final EditText localEditText1 = (EditText) localView
				.findViewById(R.id.phone_add_name);
		final EditText localEditText2 = (EditText) localView
				.findViewById(R.id.phone_add_phone);
		localEditText1.setText(str1);
		localEditText2.setText(str2);
		localBuilder.setTitle("更新用户用户").setView(localView)
				.setPositiveButton("确定", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String name = localEditText1.getText().toString()
								.trim();
						String phone = localEditText2.getText().toString()
								.trim();
						if ((!str2.equals("")) && (!str1.equals(""))) {
							Sqlite3Test.this.helper.updateUser(str1, str2,
									name, phone);
							Sqlite3Test.this.updateUI();
						} else {
							Toast.makeText(Sqlite3Test.this, "请输入完整信息！", 2000)
									.show();
						}
					}
				}).create().show();
	}

	private void createSelect(final View view) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setTitle("请选择需要执行的操作！");
		String[] srr = { "添加用户", "删除用户", "修改用户" };
		localBuilder.setSingleChoiceItems(srr, -1, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (which >= 0)
					dialog.cancel();
				switch (which) {
				case 0:
					addUser();
					break;
				case 1:
					delUser(view);
					break;
				case 2:
					updateUser(view);
				}
			}
		}).create().show();
	}

	public boolean onItemLongClick(AdapterView<?> paramAdapterView,
			View paramView, int paramInt, long paramLong) {
		System.out.println("位置--->" + paramInt);
		createSelect(paramView);
		return true;
	}

	protected void onDestroy() {
		this.helper.close();
		super.onDestroy();
	}

	private void setAnimation(ListView list) {
		AnimationSet as = new AnimationSet(true);// 使用这个类可以嵌入多种动画效果
		ScaleAnimation scale = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f);
		// 设置动画时间
		scale.setDuration(1000);
		as.addAnimation(scale);
		LayoutAnimationController controller = new LayoutAnimationController(as);
		controller.setDelay(0.2f);// 设置延迟
		// 设置顺序显示
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		list.setLayoutAnimation(controller);
	}
}
