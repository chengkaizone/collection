package com.cheng.demo;

import android.os.Bundle;

import com.cheng.adapter.GalleryAdapter;
import com.cheng.widgets.D3Gallery;

public class GalleryTest extends CommonActivity {
	private D3Gallery gal;
	int[] draws = { R.drawable.skin1, R.drawable.skin2, R.drawable.skin3,
			R.drawable.skin4, R.drawable.skin5, R.drawable.alert_dialog_icon,
			R.drawable.app_icon, R.drawable.lashou, R.drawable.se };

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.gallery_main);
		gal = (D3Gallery) findViewById(R.id.gallery_gal);
		GalleryAdapter adapter = new GalleryAdapter(this, draws);
		gal.setAdapter(adapter);
		// gal.setOnItemSelectedListener(new OnItemSelectedListener() {
		// public void onItemSelected(AdapterView<?> parent, View view,
		// int position, long id) {
		// Toast.makeText(GalleryTest.this,"--->"+position,2000).show();
		// System.out.println("---->"+position);
		// }
		// public void onNothingSelected(AdapterView<?> parent) {
		//
		// }
		// });
	}
}
