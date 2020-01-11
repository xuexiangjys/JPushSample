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

package com.xuexiang.jpushsample.core.push;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * 极光推送消息接收器
 *
 * @author xuexiang
 * @since 2020-01-11 22:45
 */
public class PushMessageReceiver extends JPushMessageReceiver {

    private static final String TAG = "JPush-Receiver";

    /**
     * 自定义消息接收
     *
     * @param context
     * @param message
     */
    @Override
    public void onMessage(Context context, CustomMessage message) {
        Log.e(TAG, "[onMessage]:" + message);
    }

    /**
     * 通知消息被点击
     *
     * @param context
     * @param message
     */
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageOpened]:" + message);

    }

    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        Log.e(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮:" + intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA));
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageArrived]:" + message);

    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageArrived]:" + message);

    }

    @Override
    public void onRegister(Context context, String registrationId) {
        Log.e(TAG, "[onRegister]:" + registrationId);

    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        Log.e(TAG, "[onConnected]:" + isConnected);

    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e(TAG, "[onCommandResult]:" + cmdMessage);

    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e(TAG, "[onTagOperatorResult]:" + jPushMessage);
    }


    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e(TAG, "[onAliasOperatorResult]:" + jPushMessage);
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e(TAG, "[onCheckTagOperatorResult]:" + jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e(TAG, "[onMobileNumberOperatorResult]:" + jPushMessage);
    }

}
