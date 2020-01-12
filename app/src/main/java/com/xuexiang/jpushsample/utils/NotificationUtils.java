/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.jpushsample.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;

import com.xuexiang.xutil.XUtil;

/**
 * 通知栏工具类
 *
 * @author xuexiang
 * @since 2019-08-16 10:19
 */
public final class NotificationUtils {

    private static NotificationManager sNotificationManager;

    private NotificationUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    //==================通知的api======================//



    //===================通知栏权限设置==================//

    /**
     * 通知栏权限是否获取
     *
     * @param context
     * @return
     */
    public static boolean isNotifyPermissionOpen(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return NotificationManagerCompat.from(context).getImportance() != NotificationManager.IMPORTANCE_NONE;
        }
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }

    /**
     * 打开通知栏权限设置页面
     *
     * @param context
     */
    public static void openNotifyPermissionSetting(Context context) {
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //直接跳转到应用通知设置的代码：
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, context.getApplicationInfo().uid);
                context.startActivity(intent);
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                intent.putExtra("app_package", context.getPackageName());
                intent.putExtra("app_uid", context.getApplicationInfo().uid);
                context.startActivity(intent);
                return;
            }
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivity(intent);
                return;
            }

            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                context.startActivity(intent);
                return;
            }

            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //=======================================================//

    /**
     * 通知
     *
     * @param id           通知ID
     * @param notification 通知的内容
     */
    public static void notify(int id, Notification notification) {
        if (sNotificationManager == null) {
            sNotificationManager = getNotificationManager();
        }
        sNotificationManager.notify(id, notification);
    }

    /**
     * 取消通知
     *
     * @param id 通知ID
     */
    public static void cancel(int id) {
        if (sNotificationManager == null) {
            sNotificationManager = getNotificationManager();
        }
        sNotificationManager.cancel(id);
    }

    /**
     * 取消所有通知
     */
    public static void cancelAll() {
        if (sNotificationManager == null) {
            sNotificationManager = getNotificationManager();
        }
        sNotificationManager.cancelAll();
    }

    public static NotificationManager getNotificationManager() {
        return (NotificationManager) XUtil.getContext().getSystemService(Activity.NOTIFICATION_SERVICE);
    }

    public static NotificationManager getManager() {
        if (sNotificationManager == null) {
            sNotificationManager = getNotificationManager();
        }
        return sNotificationManager;
    }
}
