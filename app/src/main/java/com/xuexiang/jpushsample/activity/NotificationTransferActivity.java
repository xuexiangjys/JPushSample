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

package com.xuexiang.jpushsample.activity;

import android.net.Uri;
import android.os.Bundle;

import com.xuexiang.jpushsample.core.BaseActivity;
import com.xuexiang.jpushsample.utils.Utils;
import com.xuexiang.jpushsample.utils.XToastUtils;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.annotation.Router;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xutil.common.StringUtils;

/**
 * 通知栏点击后的容器页
 *
 * deeplink格式
 *
 * jpush://com.xuexiang.jpush/notification?pageName=通知信息展示&title=这是一个通知&content=这是通知的内容&extraMsg=xxxxxxxxx&keyValue={"param1": "1111", "param2": "2222"}
 *
 * @author xuexiang
 * @since 2020-01-13 17:28
 */
@Router(path = "/push/notification/transfer")
public class NotificationTransferActivity extends BaseActivity {

    @AutoWired
    String pageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XRouter.getInstance().inject(this);

        Uri uri = getIntent().getData();
        Bundle bundle = getIntent().getExtras();
        if (uri != null) {
            //deeplink跳转
            pageName = uri.getQueryParameter("pageName");
            bundle = Utils.parseNotificationDeepLinkUri(uri, bundle);
        }

        if (!StringUtils.isEmpty(pageName)) {
            //打开指定页面
            if (openPage(pageName, bundle) == null) {
                XToastUtils.toast("页面未找到！");
                finish();
            }
        } else {
            XToastUtils.toast("页面未找到！");
            finish();
        }
    }
}
