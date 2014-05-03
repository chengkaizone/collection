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
		tv.setText("��ϵ���б�...");
		list = ((ListView) findViewById(R.id.phone_data_list));
		setAdapter();
	}

	private void setAdapter() {
		List<Contact> contacts = getData();
		if (contacts.size() > 0) {
			ContactAdapter adapter = new ContactAdapter(this, contacts);
			list.setAdapter(adapter);
		} else {
			Toast.makeText(this, "û�������ϵ��", 2000).show();
		}
	}

	private void addContact(String name, String phone, String email) {
		// ����һ���յ�ContentValues
		ContentValues values = new ContentValues();
		// ��RawContacts.CONTENT_URIִ��һ����ֵ���룬
		// Ŀ���ǻ�ȡϵͳ���ص�rawContactId
		Uri rawContactUri = getContentResolver().insert(
				RawContacts.CONTENT_URI, values);
		long rawContactId = ContentUris.parseId(rawContactUri);
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		// ������������
		values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
		// ������ϵ������
		values.put(StructuredName.GIVEN_NAME, name);
		// ����ϵ��URI�����ϵ������
		getContentResolver().insert(
				android.provider.ContactsContract.Data.CONTENT_URI, values);
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		// ������ϵ�˵ĵ绰����
		values.put(Phone.NUMBER, phone);
		// ���õ绰����
		values.put(Phone.TYPE, Phone.TYPE_MOBILE);
		// ����ϵ�˵绰����URI��ӵ绰����
		getContentResolver().insert(
				android.provider.ContactsContract.Data.CONTENT_URI, values);
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
		// ������ϵ�˵�Email��ַ
		values.put(Email.DATA, email);
		// ���øõ����ʼ�������
		values.put(Email.TYPE, Email.TYPE_WORK);
		// ����ϵ��Email URI���Email����
		getContentResolver().insert(
				android.provider.ContactsContract.Data.CONTENT_URI, values);
		Toast.makeText(ProviderResolverTest.this, "��ϵ��������ӳɹ�", 8000).show();
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
		localBuilder.setTitle("����û�").setView(localView)
				.setPositiveButton("ȷ��", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String str1 = input1.getText().toString().trim();
						String str2 = input2.getText().toString().trim();
						String str3 = input3.getText().toString().trim();
						if (str1.equals("") || str2.equals("")) {
							Toast.makeText(ProviderResolverTest.this,
									"������������Ϣ��", 2000).show();
						} else {
							addContact(str1, str2, str3);
						}
					}
				}).setNegativeButton("ȡ��", null).create().show();
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
				// ������ѯ�������ȡ����ϵ�˵Ķ���绰����
				while (phones.moveToNext()) {
					// ��ȡ��ѯ����е绰�����������ݡ�
					String phoneNumber = phones
							.getString(phones
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					phone += phoneNumber + " ";
				}
				c.setPhone(phone);
				phones.close();
				// ʹ��ContentResolver������ϵ�˵�Email��ַ
				Cursor emails = getContentResolver().query(
						ContactsContract.CommonDataKinds.Email.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Email.CONTACT_ID
								+ " = " + cursorId, null, null);
				String email = "";
				// ������ѯ�������ȡ����ϵ�˵Ķ��Email��ַ
				while (emails.moveToNext()) {
					// ��ȡ��ѯ�����Email��ַ�������ݡ�
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
		paramMenu.add(0, 0, 0, "�����ϵ��");
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
