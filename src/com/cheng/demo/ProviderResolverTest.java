package com.cheng.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.RawContacts.Data;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.adapter.ContactAdapter;
import com.cheng.entity.Contact;

public class ProviderResolverTest extends CommonActivity {
	LayoutInflater inflater;
	ListView list;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.phone_data_main);
		TextView tv = (TextView) findViewById(R.id.phone_data_declare);
		inflater = getLayoutInflater();
		tv.setText("联系人列表...");
		list = ((ListView) findViewById(R.id.phone_data_list));
		setAdapter();
	}

	private void setAdapter() {
		List<Contact> contacts = getData();
		if (contacts.size() > 0) {
			ContactAdapter adapter = new ContactAdapter(this, contacts);
			list.setAdapter(adapter);
		} else {
			Toast.makeText(this, "没有添加联系人", 2000).show();
		}
	}

	private void addContact(String name, String phone, String email) {
		// 创建一个空的ContentValues
		ContentValues values = new ContentValues();
		// 向RawContacts.CONTENT_URI执行一个空值插入，
		// 目的是获取系统返回的rawContactId
		Uri rawContactUri = getContentResolver().insert(
				RawContacts.CONTENT_URI, values);
		long rawContactId = ContentUris.parseId(rawContactUri);
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		// 设置内容类型
		values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
		// 设置联系人名字
		values.put(StructuredName.GIVEN_NAME, name);
		// 向联系人URI添加联系人名字
		getContentResolver().insert(
				android.provider.ContactsContract.Data.CONTENT_URI, values);
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		// 设置联系人的电话号码
		values.put(Phone.NUMBER, phone);
		// 设置电话类型
		values.put(Phone.TYPE, Phone.TYPE_MOBILE);
		// 向联系人电话号码URI添加电话号码
		getContentResolver().insert(
				android.provider.ContactsContract.Data.CONTENT_URI, values);
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
		// 设置联系人的Email地址
		values.put(Email.DATA, email);
		// 设置该电子邮件的类型
		values.put(Email.TYPE, Email.TYPE_WORK);
		// 向联系人Email URI添加Email数据
		getContentResolver().insert(
				android.provider.ContactsContract.Data.CONTENT_URI, values);
		Toast.makeText(ProviderResolverTest.this, "联系人数据添加成功", 8000).show();
	}

	private void addUserDialog() {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		View localView = this.inflater.inflate(R.layout.contact_add, null);
		final EditText input1 = (EditText) localView
				.findViewById(R.id.contact_add_name);
		final EditText input2 = (EditText) localView
				.findViewById(R.id.contact_add_phone);
		final EditText input3 = (EditText) localView
				.findViewById(R.id.contact_add_email);
		localBuilder.setTitle("添加用户").setView(localView)
				.setPositiveButton("确定", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String str1 = input1.getText().toString().trim();
						String str2 = input2.getText().toString().trim();
						String str3 = input3.getText().toString().trim();
						if (str1.equals("") || str2.equals("")) {
							Toast.makeText(ProviderResolverTest.this,
									"请输入完整信息！", 2000).show();
						} else {
							addContact(str1, str2, str3);
						}
					}
				}).setNegativeButton("取消", null).create().show();
	}

	private List<Contact> getData() {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		try {
			Cursor cursor = getContentResolver().query(
					ContactsContract.Contacts.CONTENT_URI, null, null, null,
					null);
			while (cursor.moveToNext()) {
				Contact c = new Contact();
				String cursorId = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				String contactName = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				Cursor phones = getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
								+ cursorId, null, null);
				c.setName(contactName);
				String phone = "";
				// 遍历查询结果，获取该联系人的多个电话号码
				while (phones.moveToNext()) {
					// 获取查询结果中电话号码列中数据。
					String phoneNumber = phones
							.getString(phones
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					phone += phoneNumber + " ";
				}
				c.setPhone(phone);
				phones.close();
				// 使用ContentResolver查找联系人的Email地址
				Cursor emails = getContentResolver().query(
						ContactsContract.CommonDataKinds.Email.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Email.CONTACT_ID
								+ " = " + cursorId, null, null);
				String email = "";
				// 遍历查询结果，获取该联系人的多个Email地址
				while (emails.moveToNext()) {
					// 获取查询结果中Email地址列中数据。
					String emailAddress = emails
							.getString(emails
									.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
					email += emailAddress + " ";
				}
				c.setEmail(email);
				emails.close();
				contacts.add(c);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contacts;
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 0, 0, "添加联系人");
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case 0:
			addUserDialog();
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}
}
