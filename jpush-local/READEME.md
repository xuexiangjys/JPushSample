# 极光推送手动本地集成

去除了富媒体消息功能，只保留核心推送服务必要的功能。


---


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