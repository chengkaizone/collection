package com.cheng.demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.entity.User;
import com.cheng.provider.Collection;

public class ResolverContentDemo extends CommonActivity implements
		OnClickListener {
	private Button insert;
	private Button query;
	private EditText username;
	private EditText password;
	private EditText key;
	private ContentResolver resolver;
	private TextView content;
	private SimpleDateFormat sdf;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.resolver_main);
		resolver = this.getContentResolver();
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		insert = (Button) findViewById(R.id.resolver_insert);
		query = (Button) findViewById(R.id.resolver_query);
		username = (EditText) findViewById(R.id.resolver_username);
		key = (EditText) findViewById(R.id.resolver_key);
		password = (EditText) findViewById(R.id.resolver_password);
		content = (TextView) findViewById(R.id.resolver_content);
		insert.setOnClickListener(this);
		query.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.resolver_insert:
			String name = username.getText().toString().trim();
			String pass = password.getText().toString().trim();
			ContentValues cv = new ContentValues();
			cv.put(Collection.Coll.NAME, name);
			cv.put(Collection.Coll.PASSWORD, pass);
			cv.put(Collection.Coll.LOGINTIME, sdf.format(new Date()));
			resolver.insert(Collection.Coll.COL_CONTENT_URI, cv);
			Toast.makeText(ResolverContentDemo.this, "用户添加成功!", 2000).show();
			break;
		case R.id.resolver_query:
			String keyValue = key.getText().toString().trim();
			Cursor cursor = resolver.query(Collection.Coll.COLS_CONTENT_URI,
					null, "username like ? or password like ?", new String[] {
							"%" + keyValue + "%", "%" + keyValue + "%" }, null);
			List<User> users = query(cursor);
			String str = "";
			User user = null;
			for (int i = 0, j = 0; i < users.size(); i++) {
				j++;
				user = users.get(i);
				if (j != users.size()) {
					str += user.getId() + "\t" + user.getUserName() + "\t"
							+ user.getPassword() + "\t" + user.getLoginTime()
							+ "\n";
				} else {
					str += user.getId() + "\t" + user.getUserName() + "\t"
							+ user.getPassword() + "\t" + user.getLoginTime();
				}
			}
			content.setText(str);
			break;
		}
	}

	/**
	 * 获取匹配的条件封装成User对象;可能符合条件的不止一个
	 * 
	 * @param cursor
	 * @return
	 */
	private List<User> query(Cursor cursor) {
		List<User> users = new ArrayList<User>();
		User user = null;
		while (cursor.moveToNext()) {
			user = new User();
			user.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			user.setUserName(cursor.getString(cursor.getColumnIndex("username")));
			user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
			user.setLoginTime(cursor.getString(cursor
					.getColumnIndex("logintime")));
			users.add(user);
		}
		return users;
	}
}
