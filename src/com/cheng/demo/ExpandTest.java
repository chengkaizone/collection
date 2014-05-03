package com.cheng.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.widget.SimpleExpandableListAdapter;

public class ExpandTest extends ExpandableListActivity {
	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.expand_list);
		List<Map<String, String>> groups = initGroup();
		List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
		childs.add(initChild());
		childs.add(initChild());
		childs.add(initChild());
		SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
				this, groups, R.layout.expand_group, new String[] { "group" },
				new int[] { R.id.expand_group }, childs, R.layout.expand_child,
				new String[] { "child" }, new int[] { R.id.expand_child });
		this.setListAdapter(adapter);
	}

	private List<Map<String, String>> initGroup() {
		List<Map<String, String>> groups = new ArrayList<Map<String, String>>();
		for (int i = 0; i < 3; i++) {
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("group", "组数据--->");
			groups.add(map1);
		}
		return groups;
	}

	private List<Map<String, String>> initChild() {
		List<Map<String, String>> childs = new ArrayList<Map<String, String>>();
		for (int i = 0; i < 5; i++) {
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("child", "子列表项--->");
			childs.add(map1);
		}
		return childs;
	}
}
