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
import android.widget.EditText;
import android.widget.TextView;

import com.xuexiang.jpushsample.R;
import com.xuexiang.jpushsample.core.BaseFragment;
import com.xuexiang.jpushsample.core.push.event.PushEvent;
import com.xuexiang.jpushsample.utils.JPushUtils;
import com.xuexiang.jpushsample.utils.Utils;
import com.xuexiang.jpushsample.utils.XToastUtils;
import com.xuexiang.rxutil2.rxbus.RxBusUtils;
import com.xuexiang.rxutil2.rxbus.SubscribeInfo;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.tip.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushMessage;

import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_ADD_TAGS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_BIND_ALIAS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_CHECK_TAG_BIND_STATE;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_CLEAN_TAGS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_DEL_TAGS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_GET_ALIAS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_GET_TAGS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_SET_TAGS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_UNBIND_ALIAS;
import static com.xuexiang.jpushsample.core.push.event.PushEvent.KEY_PUSH_EVENT;

/**
 * @author xuexiang
 * @since 2020-01-12 18:34
 */
@Page(name = "别名和标签操作")
public class AliasAndTagsOperationFragment extends BaseFragment {

    @BindView(R.id.tv_alias)
    TextView tvAlias;
    @BindView(R.id.tv_tags)
    TextView tvTags;
    @BindView(R.id.et_alias)
    EditText etAlias;
    @BindView(R.id.et_tag)
    EditText etTag;

    private SubscribeInfo mPushEvent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_alias_and_tags_operation;
    }

    @Override
    protected void initViews() {
        JPushUtils.getAlias();
        JPushUtils.getTags();
    }

    @Override
    protected void initListeners() {
        mPushEvent = RxBusUtils.get().onMainThread(KEY_PUSH_EVENT, PushEvent.class, this::handlePushEvent);
    }

    /**
     * 处理推送事件
     *
     * @param pushEvent
     */
    private void handlePushEvent(PushEvent pushEvent) {
        String content = pushEvent.getData().toString();
        switch (pushEvent.getType()) {
            case TYPE_BIND_ALIAS:
                if (pushEvent.isSuccess()) {
                    tvAlias.setText(content);
                    XToastUtils.success("别名[" + content + "]绑定成功");
                } else {
                    XToastUtils.error("别名[" + content + "]绑定失败");
                }
                break;
            case TYPE_UNBIND_ALIAS:
                if (pushEvent.isSuccess()) {
                    tvAlias.setText("");
                    XToastUtils.success("别名[" + content + "]解绑成功");
                } else {
                    XToastUtils.error("别名[" + content + "]解绑失败");
                }
                break;
            case TYPE_GET_ALIAS:
                if (pushEvent.isSuccess()) {
                    tvAlias.setText(content);
                    XToastUtils.success("别名获取成功");
                } else {
                    XToastUtils.error("别名获取失败");
                }
                break;

            case TYPE_ADD_TAGS:
                if (pushEvent.isSuccess()) {
                    XToastUtils.success("标签[" + content + "]添加成功");
                    JPushUtils.getTags();
                } else {
                    XToastUtils.error("标签[" + content + "]添加失败");
                }
                break;
            case TYPE_DEL_TAGS:
                if (pushEvent.isSuccess()) {
                    XToastUtils.success("标签[" + content + "]删除成功");
                    JPushUtils.getTags();
                } else {
                    XToastUtils.error("标签[" + content + "]删除失败");
                }
                break;
            case TYPE_GET_TAGS:
                if (pushEvent.isSuccess()) {
                    tvTags.setText(content);
                    XToastUtils.success("标签获取成功");
                } else {
                    XToastUtils.error("标签获取失败");
                }
                break;
            case TYPE_SET_TAGS:
                if (pushEvent.isSuccess()) {
                    XToastUtils.success("标签[" + content + "]设置成功");
                    JPushUtils.getTags();
                } else {
                    XToastUtils.error("标签[" + content + "]设置失败");
                }
                break;
            case TYPE_CLEAN_TAGS:
                if (pushEvent.isSuccess()) {
                    tvTags.setText("");
                    XToastUtils.success("标签清除成功");
                } else {
                    XToastUtils.error("标签清除失败");
                }
                break;
            case TYPE_CHECK_TAG_BIND_STATE:
                if (pushEvent.isSuccess()) {
                    JPushMessage jPushMessage = (JPushMessage) pushEvent.getData();
                    if (jPushMessage.getTagCheckStateResult()) {
                        XToastUtils.success("标签[" + jPushMessage.getCheckTag() + "]检查结果: 匹配");
                    } else {
                        XToastUtils.error("标签[" + jPushMessage.getCheckTag() + "]检查结果: 不匹配");
                    }
                } else {
                    XToastUtils.error("标签检查失败");
                }
                break;
            default:
                break;
        }
    }


    @SingleClick
    @OnClick({R.id.btn_bind_alias, R.id.btn_unbind_alias, R.id.btn_get_alias, R.id.btn_add_tag, R.id.btn_delete_tag, R.id.btn_get_tag, R.id.btn_set_tags, R.id.btn_clear_tag, R.id.btn_check_tag})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            //========别名操作=======//

            case R.id.btn_bind_alias:
                bindAlias();
                break;
            case R.id.btn_unbind_alias:
                JPushUtils.unBindAlias();
                break;
            case R.id.btn_get_alias:
                JPushUtils.getAlias();
                break;

            //========标签操作=======//

            case R.id.btn_add_tag:
                addTags();
                break;
            case R.id.btn_delete_tag:
                deleteTags();
                break;
            case R.id.btn_get_tag:
                JPushUtils.getTags();
                break;
            case R.id.btn_set_tags:
                setTags();
                break;
            case R.id.btn_clear_tag:
                JPushUtils.cleanTags();
                break;
            case R.id.btn_check_tag:
                checkTag();
                break;
            default:
                break;
        }
    }

    //========别名操作========//

    private void bindAlias() {
        String alias = etAlias.getText().toString();
        if (StringUtils.isEmpty(alias)) {
            XToastUtils.error("别名不能为空");
            return;
        }
        JPushUtils.bindAlias(alias);
    }

    //========标签操作========//

    private void addTags() {
        String tag = etTag.getText().toString();
        if (StringUtils.isEmpty(tag)) {
            XToastUtils.error("标签不能为空");
            return;
        }

        JPushUtils.addTags(Utils.string2Array(tag));
    }

    private void deleteTags() {
        String tag = etTag.getText().toString();
        if (StringUtils.isEmpty(tag)) {
            ToastUtils.toast("标签不能为空");
            return;
        }

        JPushUtils.deleteTags(Utils.string2Array(tag));
    }

    private void setTags() {
        String tag = etTag.getText().toString();
        if (StringUtils.isEmpty(tag)) {
            XToastUtils.error("标签不能为空");
            return;
        }

        JPushUtils.setTags(Utils.string2Array(tag));
    }

    private void checkTag() {
        String tag = etTag.getText().toString();
        if (StringUtils.isEmpty(tag)) {
            XToastUtils.error("标签不能为空");
            return;
        }

        JPushUtils.checkTagBindState(tag);
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
