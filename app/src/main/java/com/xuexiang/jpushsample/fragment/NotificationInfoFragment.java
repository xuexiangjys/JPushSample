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

package com.xuexiang.jpushsample.fragment;

import android.widget.TextView;

import com.xuexiang.jpushsample.R;
import com.xuexiang.jpushsample.core.BaseFragment;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;

import butterknife.BindView;

/**
 * @author xuexiang
 * @since 2020-01-13 21:21
 */
@Page(name = "通知信息展示")
public class NotificationInfoFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_extra_msg)
    TextView tvExtraMsg;
    @BindView(R.id.tv_key_value)
    TextView tvKeyValue;

    @AutoWired
    String title;
    @AutoWired
    String content;
    @AutoWired
    String extraMsg;
    @AutoWired
    String keyValue;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notification_info;
    }

    @Override
    protected void initArgs() {
        XRouter.getInstance().inject(this);
    }

    @Override
    protected void initViews() {
        tvTitle.setText(title);
        tvContent.setText(content);
        tvExtraMsg.setText(extraMsg);
        tvKeyValue.setText(keyValue);
    }
}
