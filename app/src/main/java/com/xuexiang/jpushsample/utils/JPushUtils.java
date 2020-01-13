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
import android.app.AlertDialog;
import android.app.Application;

import com.xuexiang.jpushsample.MyApp;
import com.xuexiang.xutil.XUtil;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;

import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_ADD_TAGS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_BIND_ALIAS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_CHECK_TAG_BIND_STATE;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_CLEAN_TAGS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_DEL_TAGS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_GET_ALIAS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_GET_TAGS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_SET_TAGS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_UNBIND_ALIAS;

/**
 * 极光推送工具类
 *
 * @author xuexiang
 * @since 2020-01-12 16:01
 */
public final class JPushUtils {

    private JPushUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    //=============初始化=============//

    /**
     * 初始化极光推送
     *
     * @param application
     */
    public static void initJPush(Application application) {
        JPushInterface.setDebugMode(MyApp.isDebug());
        JPushInterface.init(application);
    }

    /**
     * 获取注册ID，推送的token令牌
     */
    public static String getRegistrationID() {
        return JPushInterface.getRegistrationID(XUtil.getContext());
    }

    /**
     * 获取连接状态，是否已连接
     */
    public static boolean isConnected() {
        return JPushInterface.getConnectionState(XUtil.getContext());
    }

    //===========暂停/恢复推送===============//

    /**
     * 停止推送
     */
    public static void stopPush() {
        JPushInterface.stopPush(XUtil.getContext());
    }

    /**
     * 停止推送
     */
    public static void resumePush() {
        JPushInterface.resumePush(XUtil.getContext());
    }

    /**
     * 获取推送是否停止
     */
    public static boolean isPushStopped() {
        return JPushInterface.isPushStopped(XUtil.getContext());
    }

    //===========别名alias操作===============//

    /**
     * 绑定别名
     */
    public static void bindAlias(String alias) {
        JPushInterface.setAlias(XUtil.getContext(), TYPE_BIND_ALIAS, alias);
    }

    /**
     * 解绑别名
     */
    public static void unBindAlias() {
        JPushInterface.deleteAlias(XUtil.getContext(), TYPE_UNBIND_ALIAS);
    }


    /**
     * 获取别名
     */
    public static void getAlias() {
        JPushInterface.getAlias(XUtil.getContext(), TYPE_GET_ALIAS);
    }

    //===========标签tags操作===============//

    /**
     * 增加标签
     *
     * @param tags
     */
    public static void addTags(String... tags) {
        addTags(Utils.array2Set(tags));
    }

    /**
     * 增加标签（增量）
     *
     * @param tags
     */
    public static void addTags(Set<String> tags) {
        JPushInterface.addTags(XUtil.getContext(), TYPE_ADD_TAGS, tags);
    }

    /**
     * 删除标签
     *
     * @param tags
     */
    public static void deleteTags(String... tags) {
        deleteTags(Utils.array2Set(tags));
    }

    /**
     * 删除标签
     *
     * @param tags
     */
    public static void deleteTags(Set<String> tags) {
        JPushInterface.deleteTags(XUtil.getContext(), TYPE_DEL_TAGS, tags);
    }

    /**
     * 获取标签
     */
    public static void getTags() {
        JPushInterface.getAllTags(XUtil.getContext(), TYPE_GET_TAGS);
    }


    /**
     * 设置标签
     *
     * @param tags
     */
    public static void setTags(String... tags) {
        setTags(Utils.array2Set(tags));
    }

    /**
     * 设置标签（全量）
     *
     * @param tags
     */
    public static void setTags(Set<String> tags) {
        JPushInterface.setTags(XUtil.getContext(), TYPE_SET_TAGS, tags);
    }

    /**
     * 清除所有标签
     */
    public static void cleanTags() {
        JPushInterface.cleanTags(XUtil.getContext(), TYPE_CLEAN_TAGS);
    }

    /**
     * 查询指定 tag 与当前用户绑定的状态
     *
     * @param tag
     */
    public static void checkTagBindState(String tag) {
        JPushInterface.checkTagBindState(XUtil.getContext(), TYPE_CHECK_TAG_BIND_STATE, tag);
    }


    //===========辅助操作===============//

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
        JPushInterface.requestPermission(activity);
    }

    /**
     * 设置是否开启省电模式，默认是false， 关闭的
     *
     * @param enable
     */
    public static void setPowerSaveMode(boolean enable) {
        JPushInterface.setPowerSaveMode(XUtil.getContext(), enable);
    }


}
