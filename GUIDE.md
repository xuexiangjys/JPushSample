## 快速集成指南

> 本文是基于`jpush:3.5.4`和`jcore:2.2.6`版本介绍的，暂只介绍最新推荐的使用方法，那些过时的用法这里我就不多介绍了，想了解的可以去[极光推送官方文档](https://docs.jiguang.cn/jpush/client/Android/android_sdk/)查看。

### 集成前的准备工作

> 在接入极光推送前，首先需要获取到应用的AppKey，它是应用的唯一标识。

1.创建极光推送开发者帐号

要创建极光推送开发者帐号，请访问极光推送官方网站: https://www.jiguang.cn/push

![](./art/prepare_1.png)

2.创建应用

进入极光控制台后，点击“创建应用”按钮，填写应用名称即可创建应用成功。同时点击“推送设置”，在 Android 版块填上你的应用包名，选择保存即可。

![](./art/prepare_2.png)
![](./art/prepare_3.png)

3.获取应用的AppKey

在极光控制台点击"应用设置"中的"应用信息"，获取应用的AppKey。

![](./art/prepare_4.png)

### 引入依赖库

#### 方法一 jcenter自动集成

> 使用 jcenter 自动集成的开发者，不需要在项目中添加 jar 和 so，jcenter 会自动完成依赖；在 AndroidManifest.xml 中不需要添加任何 JPush SDK 相关的配置，jcenter 会自动导入。

1.配置项目的`build.gradle`文件

```
android {

    defaultConfig {
        applicationId "com.xxx.xxx" //JPush平台上注册的应用包名.

        ...

        ndk {
            //选择要添加的对应 cpu 类型的 .so 库。
            abiFilters 'armeabi', 'armeabi-v7a', 'arm64-v8a'
            //,'x86', 'x86_64', 'mips', 'mips64'
        }
        manifestPlaceholders = [
                JPUSH_PKGNAME: defaultConfig.applicationId,
                JPUSH_APPKEY : "你的 Appkey ",//值来自开发者平台取得的AppKey
                JPUSH_CHANNEL: "default_developer",
        ]
    }

}

dependencies {
    ...
    //引入JPush依赖库
    implementation 'cn.jiguang.sdk:jpush:3.5.4'
    implementation 'cn.jiguang.sdk:jcore:2.2.6'
}
```

2.配置项目的`AndroidManifest.xml`文件

```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.xuexiang.jpush">

    <application>

        <!-- 1.这个是自定义Service，要继承极光JCommonService，可以在更多手机平台上使得推送通道保持的更稳定 -->
        <service
            android:name=".PushService"
            android:enabled="true"
            android:exported="false"
            android:process=":pushcore">
            <intent-filter>
                <action android:name="cn.jiguang.user.service.action" />
            </intent-filter>
        </service>

        <!-- 2.用户自定义接收消息器,所有你想要知道的消息都在这里-->
        <receiver android:name=".core.push.PushMessageReceiver">
            <intent-filter>
                <action android:name="cn.jpush.android.intent.RECEIVE_MESSAGE" />
                <category android:name="${applicationId}" />
            </intent-filter>
        </receiver>

    </application>

</manifest>
```

[点击参见自动集成的项目源码](https://github.com/xuexiangjys/JPushSample/tree/master/jpush-remote)


#### 方法二 本地手动集成

1.首先你需要先去下载SDK，下载地址: https://docs.jiguang.cn/jpush/resources/

2.解压SDK，将压缩包下的libs内容复制到项目的libs下

3.配置项目的`build.gradle`文件

```
android {

    defaultConfig {
        applicationId "com.xxx.xxx" //JPush平台上注册的应用包名.

        ...

        ndk {
            //选择要添加的对应 cpu 类型的 .so 库。
            abiFilters 'armeabi', 'armeabi-v7a', 'arm64-v8a'
            //,'x86', 'x86_64', 'mips', 'mips64'
        }
        manifestPlaceholders = [
                JPUSH_PKGNAME: defaultConfig.applicationId,
                JPUSH_APPKEY : "你的 Appkey ",//值来自开发者平台取得的AppKey
                JPUSH_CHANNEL: "default_developer",
        ]
    }

    sourceSets {
        //设置libs目录为so包的加载目录
        main {
            jniLibs.srcDirs = ['libs']
        }
    }

}
```

4.配置项目的`AndroidManifest.xml`文件

```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.xxx.xxx">

    <permission
        android:name="${applicationId}.permission.JPUSH_MESSAGE"
        android:protectionLevel="signature" />

    <!-- Required  一些系统要求的权限，如访问网络等-->
    <uses-permission android:name="${applicationId}.permission.JPUSH_MESSAGE" />
    <uses-permission android:name="android.permission.RECEIVE_USER_PRESENT" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_SETTINGS" />
    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />

    <!-- 用于开启 debug 版本的应用在6.0 系统上 层叠窗口权限 -->
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    <!-- Optional for location -->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS" />
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
    <uses-permission android:name="android.permission.GET_TASKS" />

    <application>
        <!-- Required SDK核心功能-->
        <activity
            android:name="cn.jpush.android.ui.PushActivity"
            android:configChanges="orientation|keyboardHidden"
            android:exported="false"
            android:theme="@android:style/Theme.NoTitleBar">
            <intent-filter>
                <action android:name="cn.jpush.android.ui.PushActivity" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="${applicationId}" />
            </intent-filter>
        </activity>
        <!-- Required SDK 核心功能-->
        <!-- 可配置android:process参数将PushService放在其他进程中 -->
        <service
            android:name="cn.jpush.android.service.PushService"
            android:exported="false"
            android:process=":pushcore">
            <intent-filter>
                <action android:name="cn.jpush.android.intent.REGISTER" />
                <action android:name="cn.jpush.android.intent.REPORT" />
                <action android:name="cn.jpush.android.intent.PushService" />
                <action android:name="cn.jpush.android.intent.PUSH_TIME" />
            </intent-filter>
        </service>
        <!-- since 3.0.9 Required SDK 核心功能-->
        <provider
            android:name="cn.jpush.android.service.DataProvider"
            android:authorities="${applicationId}.DataProvider"
            android:exported="false" />
        <!-- since 1.8.0 option 可选项。用于同一设备中不同应用的JPush服务相互拉起的功能。 -->
        <!-- 若不启用该功能可删除该组件，将不拉起其他应用也不能被其他应用拉起 -->
        <service
            android:name="cn.jpush.android.service.DaemonService"
            android:enabled="true"
            android:exported="true">
            <intent-filter>
                <action android:name="cn.jpush.android.intent.DaemonService" />
                <category android:name="${applicationId}" />
            </intent-filter>
        </service>
        <!-- since 3.1.0 Required SDK 核心功能-->
        <provider
            android:name="cn.jpush.android.service.DownloadProvider"
            android:authorities="${applicationId}.DownloadProvider"
            android:exported="true" />
        <!-- Required SDK核心功能-->
        <receiver
            android:name="cn.jpush.android.service.PushReceiver"
            android:enabled="true"
            android:exported="false">
            <intent-filter android:priority="1000">
                <!--Required  显示通知栏 -->
                <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED_PROXY" />
                <category android:name="${applicationId}" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.USER_PRESENT" />
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
            </intent-filter>
            <!-- Optional -->
            <intent-filter>
                <action android:name="android.intent.action.PACKAGE_ADDED" />
                <action android:name="android.intent.action.PACKAGE_REMOVED" />

                <data android:scheme="package" />
            </intent-filter>
        </receiver>

        <!-- Required SDK核心功能-->
        <receiver
            android:name="cn.jpush.android.service.AlarmReceiver"
            android:exported="false" />

        <!--since 3.3.0 Required SDK核心功能-->
        <activity
            android:name="cn.jpush.android.service.JNotifyActivity"
            android:exported="true"
            android:taskAffinity="jpush.custom"
            android:theme="@android:style/Theme.Translucent.NoTitleBar">
            <intent-filter>
                <action android:name="cn.jpush.android.intent.JNotifyActivity" />
                <category android:name="${applicationId}" />
            </intent-filter>
        </activity>


        <!-- *********************下面这两个是需要你自己定义的**************************** -->


        <!-- since 3.3.0 Required SDK 核心功能-->
        <!-- 1.这个是自定义Service，要继承极光JCommonService，可以在更多手机平台上使得推送通道保持的更稳定 -->
        <service
            android:name=".PushService"
            android:enabled="true"
            android:exported="false"
            android:process=":pushcore">
            <intent-filter>
                <action android:name="cn.jiguang.user.service.action" />
            </intent-filter>
        </service>

        <!-- 2.用户自定义接收消息器,所有你想要知道的消息都在这里-->
        <receiver android:name=".core.push.PushMessageReceiver">
            <intent-filter>
                <action android:name="cn.jpush.android.intent.RECEIVE_MESSAGE" />
                <category android:name="${applicationId}" />
            </intent-filter>
        </receiver>


        <meta-data
            android:name="JPUSH_CHANNEL"
            android:value="${JPUSH_CHANNEL}" />
        <!-- 值来自开发者平台取得的AppKey-->
        <meta-data
            android:name="JPUSH_APPKEY"
            android:value="${JPUSH_APPKEY}" />

    </application>

</manifest>

```

[点击参见手动集成的项目源码](https://github.com/xuexiangjys/JPushSample/tree/master/jpush-local)

### 初始化

1.在Application中初始化JPush

```
public class MyApp extends Application {
@Override
    public void onCreate() {
        super.onCreate();
        initJPush();
    }
}

/**
 * 初始化极光推送
 */
private void initJPush() {
    JPushInterface.setDebugMode(BuildConfig.DEBUG);
    //只需要在应用程序启动时调用一次该 API 即可
    JPushInterface.init(this);
}
```

2.在应用的第一个页面申请权限（可选）

由于Android手机定制ROM太多，部分手机的通知栏权限默认是关闭的，需要用户手动打开。如果不打开通知栏权限的话，即使你连上了推送，也无法收到推送消息。

```
/**
 * 申请定位、存储和通知栏的权限
 *
 * @param activity
 */
public static void requestPermission(Activity activity) {
    //打开通知栏的权限
    if (JPushInterface.isNotificationEnabled(activity) == 0) {
        new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setMessage("通知权限未打开，是否前去打开？")
                .setPositiveButton("是", (d, w) -> JPushInterface.goToAppNotificationSettings(activity))
                .setNegativeButton("否", null)
                .show();
    }
    //申请定位、存储权限
    JPushInterface.requestPermission(activity);
}
```

### 运行调试

当完成以上步骤后，可直接运行程序，并查看logcat日志，设置过滤条件为"JIGUANG"，如果出现"Register succeed"和"registrationId:xxxxxxxxxxxxxx"字样，即为集成成功！如下图所示：

![](./art/jpush_init.png)

注意事项：

* 一定要保证配置的AppKey和应用的包名保持一致。
* 一定要保证运行的设备网络是可用的，否则无法连接推送。

### 混淆配置

配置项目的`proguard-rules.pro`文件。

```
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
-keep class cn.jiguang.** { *; }
-keep class * extends cn.jpush.android.service.JPushMessageReceiver{*;}
```
