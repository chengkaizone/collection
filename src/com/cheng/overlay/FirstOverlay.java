package com.cheng.overlay;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class FirstOverlay extends ItemizedOverlay<OverlayItem> {
	private Context context;
	private List<OverlayItem> items = new ArrayList<OverlayItem>();

	public FirstOverlay(Context context, Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		this.context = context;
	}

	public FirstOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	public void addOverlay(OverlayItem lay) {
		items.add(lay);
		this.populate();
	}

	protected OverlayItem createItem(int i) {
		return items.get(i);
	}

	public int size() {
		return items.size();
	}

	protected boolean onTap(int index) {
		OverlayItem item = createItem(index);
		new AlertDialog.Builder(context).setTitle(item.getTitle())
				.setMessage(item.getSnippet()).create().show();
		return true;
	}
}
