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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.NotificationMessage;

/**
 * @author xuexiang
 * @since 2020-01-12 19:24
 */
public final class Utils {


    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * String数组转Set
     *
     * @param values
     * @return
     */
    public static Set<String> array2Set(String... values) {
        return new HashSet<>(Arrays.asList(values));
    }

    /**
     * 以“,”隔开的字符串转数组
     *
     * @param values
     * @return
     */
    public static String[] string2Array(String values) {
        if (!TextUtils.isEmpty(values)) {
            return values.split(",");
        }
        return new String[0];
    }


    /**
     *
     * DeepLink的格式：
     *
     *      jpush://com.xuexiang.jpush/notification?pageName=xxxxx&title=这是一个通知&content=这是通知的内容&extraMsg=xxxxxxxxx&keyValue={"param1": "1111", "param2": "2222"}
     *
     * @param uri
     * @param bundle
     * @return
     */
    public static Bundle parseNotificationDeepLinkUri(@NonNull Uri uri, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }

        bundle.putString("pageName", uri.getQueryParameter("pageName"));
        //通知标题
        bundle.putString("title", uri.getQueryParameter("title"));
        //通知内容
        bundle.putString("content", uri.getQueryParameter("content"));
        //通知附带拓展内容
        bundle.putString("extraMsg", uri.getQueryParameter("extraMsg"));
        //通知附带键值对
        bundle.putString("keyValue", uri.getQueryParameter("keyValue"));
        return bundle;
    }

    /**
     *
     * 解析极光通知消息：NotificationMessage
     *
     * @param message
     * @return
     */
    public static Intent parseNotificationMessage(@NonNull Intent intent, NotificationMessage message) {
        //这只是一个例子，暂时把跳转的目标页设为 "通知信息展示"
        intent.putExtra("pageName", "通知信息展示");
        //通知标题
        intent.putExtra("title", message.notificationTitle);
        //通知内容
        intent.putExtra("content", message.notificationContent);
        //通知附带拓展内容
        intent.putExtra("extraMsg", message.notificationExtras);
        //通知附带键值对
        intent.putExtra("keyValue", message.notificationExtras);
        return intent;
    }

}
