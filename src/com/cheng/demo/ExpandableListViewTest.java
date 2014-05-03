package com.cheng.demo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableListViewTest extends CommonActivity {
	ExpandableListView list;
	String[] srr = { "神族兵种", "虫族兵种", "人族兵种" };
	String[][] ssrr = { { "狂战士", "龙骑士", "黑暗神堂", "电兵" },
			{ "小狗", "刺蛇", "飞蛇", "自爆飞机" }, { "机枪兵", "护士MM", "幽灵" }, };

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.expandlist);
		list = (ExpandableListView) findViewById(R.id.expand_list);
		ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				View view = getLayoutInflater().inflate(R.layout.group_adapter,
						null);
				TextView tv1 = (TextView) view.findViewById(R.id.group_tv1);
				TextView tv2 = (TextView) view.findViewById(R.id.group_tv2);
				tv1.setText(getGroup(groupPosition));
				tv2.setText("[" + 0 + "/" + ssrr[groupPosition].length + "]");
				return view;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				View view = getLayoutInflater().inflate(R.layout.child_adapter,
						null);
				ImageView img = (ImageView) view.findViewById(R.id.child_img);
				TextView tv1 = (TextView) view.findViewById(R.id.child_tv1);
				TextView tv2 = (TextView) view.findViewById(R.id.child_tv2);
				tv1.setText(getChild(groupPosition, childPosition));
				return view;
			}

			@Override
			public int getGroupCount() {
				return srr.length;
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				return ssrr[groupPosition].length;
			}

			@Override
			public String getGroup(int groupPosition) {
				return srr[groupPosition];
			}

			@Override
			public String getChild(int groupPosition, int childPosition) {
				return ssrr[groupPosition][childPosition];
			}

			@Override
			public long getGroupId(int groupPosition) {
				return groupPosition;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				return childPosition;
			}

			@Override
			public boolean hasStableIds() {
				return true;
			}

			@Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				return true;
			}
		};
		list.setAdapter(adapter);
	}
}
