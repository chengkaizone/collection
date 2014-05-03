package com.cheng.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cheng.demo.ActForResult;
import com.cheng.demo.ActivityMenu;
import com.cheng.demo.AidlClient;
import com.cheng.demo.AlarmTest;
import com.cheng.demo.AudioManagerTest;
import com.cheng.demo.BluetoothTest;
import com.cheng.demo.ButtonActivity;
import com.cheng.demo.CameraTest;
import com.cheng.demo.CheckableMenu;
import com.cheng.demo.CommonActivity;
import com.cheng.demo.CompassTest;
import com.cheng.demo.ContextMenuTest;
import com.cheng.demo.DateDialog;
import com.cheng.demo.DownTest;
import com.cheng.demo.ExpandTest;
import com.cheng.demo.ExpandableListViewTest;
import com.cheng.demo.FileAccess;
import com.cheng.demo.GestureScaleTest;
import com.cheng.demo.Get;
import com.cheng.demo.GifViewTest;
import com.cheng.demo.Gradienter;
import com.cheng.demo.Hint;
import com.cheng.demo.HorizontalTest;
import com.cheng.demo.ImageViewMain;
import com.cheng.demo.LeidianActivity;
import com.cheng.demo.LocationTest;
import com.cheng.demo.LoginDeclare;
import com.cheng.demo.MoniterMessage;
import com.cheng.demo.MutiTouchTest;
import com.cheng.demo.MyMenuTest;
import com.cheng.demo.NotifyCationTest;
import com.cheng.demo.Photos;
import com.cheng.demo.PictureTest;
import com.cheng.demo.PreferScreenTest;
import com.cheng.demo.ProviderResolverTest;
import com.cheng.demo.PullParserTest;
import com.cheng.demo.R;
import com.cheng.demo.RecorderTest;
import com.cheng.demo.RecorderVideoTest;
import com.cheng.demo.ResolverContentDemo;
import com.cheng.demo.SAXParseTest;
import com.cheng.demo.SearchableTest;
import com.cheng.demo.SensorsTest;
import com.cheng.demo.SetSkin;
import com.cheng.demo.ShaderTest;
import com.cheng.demo.ShortCutTest;
import com.cheng.demo.SlidingDrawerTest;
import com.cheng.demo.Sqlite3Test;
import com.cheng.demo.TextSwitcherTest;
import com.cheng.demo.TextToSpeechTest;
import com.cheng.demo.VibratorTest;
import com.cheng.demo.VideoViewTest;
import com.cheng.demo.WallpaperTest;
import com.cheng.demo.WarpTest;
import com.cheng.demo.WeatherInfoTest;
import com.cheng.demo.WifiTest;

/**
 * �Ż������ԭ��--���ȡ�����ͷ�---��������px--ʹ��dp�ı�ʹ��spzuo��λ
 * ������쳣�������ֻ��汾�йء�������google��ͼ��������δ�ҵ�ԭ�� ʹ��ʱ���Բο��÷������⽨����ʹ��google��ͼ
 * 
 * @author Administrator
 * 
 */
public class RootMain extends CommonActivity {
	private String[] srr = {
			"��Ӱ����",
			"pull��������",
			"ʵʱSAX��������",
			"ʵʱdom 4j��������",
			// "�ٶȵ�ͼ",
			"�����Ĳ˵�",
			"Activity�����˵�",
			"��ѡ�˵�",
			"aidlԶ�̵���",
			// "google��ͼ",//google��ͼһ��Ҫʹ��googleģ���������򱨴�;���ǳ���ԭ��δ֪
			"ģ��Launcher", "ָ����", "ˮƽ��", "�������ڷ��Ͷ���", "�ṩ��&������", "�Զ����ṩ����ʾ",
			"sqlite���ݿ�", "Ӧ���ļ�����", "����������", "��ݷ�ʽ", "¼����Ƶ", "�����", "¼������",
			"��Ƶ������", "��ʱ��ǽֽ", "��������", "����", "��Ƶ������", "�ı��ʶ�", "��㴥��", "��������ͼƬ",
			"��������", "Ť������", "�������", "�׵�ս��", "��ͼ��", "�ļ�����", "�û���λ", "���׿�չ����",
			"ʱ������", "wifi����",

			"3DЧ�����", "startForResult", "��������", "��չ����", "�Զ���������", "��ʾgifͼƬ",
			"����ת����", "��ʾ", "�ᶯ�İ�ť",

			"�Զ���˵�", "��¼��ʾ", "������ʾ��", "��ȡԪ����", "֪ͨ������", "Ƥ������", };
	private Class[] crr = { ImageViewMain.class,// ��Ӱ����
			PullParserTest.class,// ��������Ԥ���ļ�
			SAXParseTest.class,// SAX��������
			WeatherInfoTest.class,// ʵʱ����Ԥ��
			// MainClass.class,//�ٶȵ�ͼ
			ContextMenuTest.class,// �����Ĳ˵�
			ActivityMenu.class,// Activity�����˵�
			CheckableMenu.class,// ��ѡ�˵�
			// MyGoogleMap.class,//google��ͼ
			AidlClient.class,// aidlԶ�̵���
			SlidingDrawerTest.class,// �������ʹ��
			CompassTest.class,// ָ����
			Gradienter.class,// ˮƽ��
			MoniterMessage.class,// �������ڷ��Ͷ���
			ProviderResolverTest.class,// �ṩ��&������---
			ResolverContentDemo.class,// �Զ����ṩ����ʾ
			Sqlite3Test.class,// sqlite���ݿ�---
			FileAccess.class,// Ӧ���ļ�����---
			SensorsTest.class,// ������
			ShortCutTest.class,// ��ݷ�ʽ
			RecorderVideoTest.class,// ¼����Ƶ
			CameraTest.class,// �����---
			RecorderTest.class,// ¼������
			VideoViewTest.class,// ��Ƶ������
			WallpaperTest.class,// ��ʱ����ֽ
			AlarmTest.class,// ��ʱ��
			VibratorTest.class,// �񶯲���
			AudioManagerTest.class,// ��Ƶ������
			TextToSpeechTest.class,// �ı��ʶ�
			MutiTouchTest.class,// ��㴥��
			GestureScaleTest.class,// ��������
			SearchableTest.class,// ����������
			WarpTest.class,// Ť������
			ShaderTest.class,// �������
			LeidianActivity.class,// �׵�ս��
			PictureTest.class,// ��ͼ��
			DownTest.class,// �ļ�����
			LocationTest.class,// �û���λ
			ExpandTest.class,// ���׿�չ����
			DateDialog.class,// ʱ������
			WifiTest.class,// wifi����

			Photos.class,// 3D���
			ActForResult.class,// startForResult
			BluetoothTest.class,// ��������
			ExpandableListViewTest.class,// ��չ����
			PreferScreenTest.class,// �Զ�����
			GifViewTest.class,// ��������
			TextSwitcherTest.class,// ����ת����
			LoginDeclare.class,// ��¼����
			ButtonActivity.class,// �ᶯ�İ�ť

			MyMenuTest.class,// �Զ���˵�
			Hint.class,// ��ʾ
			HorizontalTest.class,// ��Ӱ������
			Get.class,// ��¼��ʾ
			NotifyCationTest.class,// ֪ͨ������
			SetSkin.class,// Ƥ������
	};
	private ListView list;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.root_cheng_main);
		list = (ListView) findViewById(R.id.root_cheng_list);
		TextView tv = (TextView) findViewById(R.id.root_cheng_info);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(new Intent(RootMain.this, crr[position]));
			}
		});
		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, srr);
		list.setAdapter(adapter);
		tv.setText(crr.length + "��ɼ�7 �����ع��ܣ����������������򡢵�����ء�С������ʵʱ�ļ��С���ݷ�ʽ�����ض���");
	}
}
