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
 * 优化程序的原则--晚获取；早释放---不用像素px--使用dp文本使用spzuo单位
 * 照相机异常可能与手机版本有关、、关于google地图报错；至今未找到原因； 使用时可以参考用法；另外建工程使用google地图
 * 
 * @author Administrator
 * 
 */
public class RootMain extends CommonActivity {
	private String[] srr = {
			"倒影测试",
			"pull解析天气",
			"实时SAX解析天气",
			"实时dom 4j解析天气",
			// "百度地图",
			"上下文菜单",
			"Activity关联菜单",
			"复选菜单",
			"aidl远程调用",
			// "google地图",//google地图一定要使用google模拟器；否则报错;总是出错；原因未知
			"模拟Launcher", "指南针", "水平仪", "监听正在发送短信", "提供器&解析器", "自定义提供器演示",
			"sqlite数据库", "应用文件访问", "传感器集合", "快捷方式", "录制视频", "照相机", "录制声音",
			"视频播放器", "定时换墙纸", "闹铃设置", "振动器", "音频管理器", "文本朗读", "多点触控", "手势缩放图片",
			"搜索测试", "扭曲测试", "渐变测试", "雷电战机", "画图板", "文件下载", "用户定位", "简易可展开项",
			"时间设置", "wifi操作",

			"3D效果相册", "startForResult", "蓝牙操作", "可展开项", "自动控制设置", "显示gif图片",
			"文字转换器", "提示", "会动的按钮",

			"自定义菜单", "登录提示", "流量提示条", "获取元数据", "通知管理器", "皮肤管理", };
	private Class[] crr = { ImageViewMain.class,// 倒影测试
			PullParserTest.class,// 解析天气预报文件
			SAXParseTest.class,// SAX解析天气
			WeatherInfoTest.class,// 实时天气预报
			// MainClass.class,//百度地图
			ContextMenuTest.class,// 上下文菜单
			ActivityMenu.class,// Activity关联菜单
			CheckableMenu.class,// 复选菜单
			// MyGoogleMap.class,//google地图
			AidlClient.class,// aidl远程调用
			SlidingDrawerTest.class,// 抽屉类的使用
			CompassTest.class,// 指南针
			Gradienter.class,// 水平仪
			MoniterMessage.class,// 监听正在发送短信
			ProviderResolverTest.class,// 提供器&解析器---
			ResolverContentDemo.class,// 自定义提供器演示
			Sqlite3Test.class,// sqlite数据库---
			FileAccess.class,// 应用文件访问---
			SensorsTest.class,// 传感器
			ShortCutTest.class,// 快捷方式
			RecorderVideoTest.class,// 录制视频
			CameraTest.class,// 照相机---
			RecorderTest.class,// 录音测试
			VideoViewTest.class,// 视频播放器
			WallpaperTest.class,// 定时换壁纸
			AlarmTest.class,// 定时器
			VibratorTest.class,// 振动测试
			AudioManagerTest.class,// 音频管理器
			TextToSpeechTest.class,// 文本朗读
			MutiTouchTest.class,// 多点触控
			GestureScaleTest.class,// 手势缩放
			SearchableTest.class,// 呼出搜索框
			WarpTest.class,// 扭曲测试
			ShaderTest.class,// 渐变测试
			LeidianActivity.class,// 雷电战机
			PictureTest.class,// 画图板
			DownTest.class,// 文件下载
			LocationTest.class,// 用户定位
			ExpandTest.class,// 简易可展开项
			DateDialog.class,// 时间设置
			WifiTest.class,// wifi操作

			Photos.class,// 3D相册
			ActForResult.class,// startForResult
			BluetoothTest.class,// 蓝牙操作
			ExpandableListViewTest.class,// 可展开项
			PreferScreenTest.class,// 自动设置
			GifViewTest.class,// 动画测试
			TextSwitcherTest.class,// 文字转换器
			LoginDeclare.class,// 登录测试
			ButtonActivity.class,// 会动的按钮

			MyMenuTest.class,// 自定义菜单
			Hint.class,// 提示
			HorizontalTest.class,// 电影流量条
			Get.class,// 登录提示
			NotifyCationTest.class,// 通知管理器
			SetSkin.class,// 皮肤管理
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
		tv.setText(crr.length + "项可见7 项隐藏功能；开机启动、搜索框、电量监控、小部件、实时文件夹、快捷方式、拦截短信");
	}
}
