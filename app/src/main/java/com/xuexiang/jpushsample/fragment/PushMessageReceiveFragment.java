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
import com.xuexiang.jpushsample.core.push.event.PushMessage;
import com.xuexiang.rxutil2.rxbus.RxBusUtils;
import com.xuexiang.xpage.annotation.Page;

import butterknife.BindView;

import static com.xuexiang.jpushsample.core.push.event.PushMessage.KEY_PUSH_MESSAGE;

/**
 * @author xuexiang
 * @since 2020-01-13 00:35
 */
@Page(name = "推送消息接收\n自定义消息、普通通知消息")
public class PushMessageReceiveFragment extends BaseFragment {

    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_message)
    TextView tvMessage;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_push_message_receive;
    }

    @Override
    protected void initViews() {


    }

    @Override
    protected void initListeners() {
        RxBusUtils.get().onMainThread(KEY_PUSH_MESSAGE, PushMessage.class, this::handlePushMessage);

    }

    /**
     * 处理接收到的推送消息
     * @param pushMessage
     */
    private void handlePushMessage(PushMessage pushMessage) {
        tvType.setText(pushMessage.getMessageType());
        tvMessage.setText(pushMessage.getMessage().toString());
    }


    @Override
    public void onDestroyView() {
        RxBusUtils.get().unregisterAll(KEY_PUSH_MESSAGE);
        super.onDestroyView();
    }
}
