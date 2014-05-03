package com.cheng.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.LiveFolders;

/**
 * 实时文件夹需要过滤器属性
 * 
 * @author Administrator
 * 
 */
public class ContactsLiveFolder extends CommonActivity {

	public void onCreate(Bundle save) {
		super.onCreate(save);
		if (getIntent().getAction().equals(
				LiveFolders.ACTION_CREATE_LIVE_FOLDER)) {
			Intent intent = new Intent();
			intent.setData(Uri.parse("content://contacts/live_folders/people"));
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_BASE_INTENT,
					new Intent(Intent.ACTION_VIEW,
							ContactsContract.Contacts.CONTENT_URI));
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_NAME, "电话本");
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_ICON,
					Intent.ShortcutIconResource.fromContext(this,
							R.drawable.lashou));
			intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_DISPLAY_MODE,
					LiveFolders.DISPLAY_MODE_LIST);
			setResult(RESULT_OK, intent);
		} else {
			setResult(RESULT_CANCELED);
		}
		finish();
	}
}
