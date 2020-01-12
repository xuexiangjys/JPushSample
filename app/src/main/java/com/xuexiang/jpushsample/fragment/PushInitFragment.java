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

import android.view.View;
import android.widget.TextView;

import com.xuexiang.jpushsample.R;
import com.xuexiang.jpushsample.core.BaseFragment;
import com.xuexiang.jpushsample.core.push.event.PushEvent;
import com.xuexiang.jpushsample.utils.JPushUtils;
import com.xuexiang.rxutil2.rxbus.RxBusUtils;
import com.xuexiang.rxutil2.rxbus.SubscribeInfo;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_CONNECT_STATUS_CHANGED;
import static com.xuexiang.jpushsample.core.push.event.PushEvent.KEY_PUSH_EVENT;

/**
 * 推送初始化
 *
 * @author xuexiang
 * @since 2019-07-08 00:52
 */
@Page(name = "推送初始化")
public class PushInitFragment extends BaseFragment {

    @BindView(R.id.tv_token)
    TextView tvToken;
    @BindView(R.id.tv_connect_status)
    TextView tvConnectStatus;
    @BindView(R.id.tv_push_status)
    TextView tvPushStatus;

    private SubscribeInfo mPushEvent;

    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_push_init;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        tvToken.setText(JPushUtils.getRegistrationID());
        tvConnectStatus.setText(String.valueOf(JPushUtils.isConnected()));
        tvPushStatus.setText(JPushUtils.isPushStopped() ? "已停止" : "工作中");
    }

    @Override
    protected void initListeners() {
        mPushEvent = RxBusUtils.get().onMainThread(KEY_PUSH_EVENT, PushEvent.class, this::handlePushEvent);

    }

    @SingleClick
    @OnClick({R.id.btn_resume, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_resume:
                JPushUtils.resumePush();
                break;
            case R.id.btn_stop:
                JPushUtils.stopPush();
                break;
            default:
                break;
        }
    }

    /**
     * 处理推送事件
     *
     * @param pushEvent
     */
    private void handlePushEvent(PushEvent pushEvent) {
        switch (pushEvent.getType()) {
            case TYPE_CONNECT_STATUS_CHANGED:
                tvConnectStatus.setText(String.valueOf(pushEvent.isSuccess()));
                tvPushStatus.setText(JPushUtils.isPushStopped() ? "已停止" : "工作中");
                break;
            default:
                break;
        }
    }


    @Override
    public void onDestroyView() {
        if (mPushEvent != null) {
            RxBusUtils.get().unregister(KEY_PUSH_EVENT, mPushEvent);
            mPushEvent = null;
        }
        super.onDestroyView();
    }


}
