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

package com.xuexiang.jpushsample.core.push.event;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_ADD_TAGS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_BIND_ALIAS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_CONNECT_STATUS_CHANGED;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_DEL_TAGS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_GET_ALIAS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_GET_TAGS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_REGISTER;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_UNBIND_ALIAS;
import static com.xuexiang.jpushsample.core.push.event.EventType.TYPE_UNREGISTER;

/**
 * 推送事件的类型
 *
 * @author xuexiang
 * @since 2020-01-12 16:19
 */
@IntDef({TYPE_REGISTER, TYPE_UNREGISTER, TYPE_CONNECT_STATUS_CHANGED, TYPE_ADD_TAGS, TYPE_DEL_TAGS, TYPE_GET_TAGS, TYPE_BIND_ALIAS, TYPE_UNBIND_ALIAS, TYPE_GET_ALIAS})
@Retention(RetentionPolicy.SOURCE)
public @interface EventType {

    /**
     * 注册推送
     */
    int TYPE_REGISTER = 2000;
    /**
     * 取消注册推送
     */
    int TYPE_UNREGISTER = 2001;
    /**
     * 推送连接状态发生变化
     */
    int TYPE_CONNECT_STATUS_CHANGED = 2002;


    /**
     * 绑定别名
     */
    int TYPE_BIND_ALIAS = 2010;
    /**
     * 解绑别名
     */
    int TYPE_UNBIND_ALIAS = 2011;
    /**
     * 获取别名
     */
    int TYPE_GET_ALIAS = 2012;


    /**
     * 添加标签[增量]
     */
    int TYPE_ADD_TAGS = 2020;
    /**
     * 删除标签
     */
    int TYPE_DEL_TAGS = 2021;
    /**
     * 获取标签
     */
    int TYPE_GET_TAGS = 2022;
    /**
     * 设置标签[全量]
     */
    int TYPE_SET_TAGS = 2023;
    /**
     * 清除所有标签
     */
    int TYPE_CLEAN_TAGS = 2024;
    /**
     * 查询指定 tag 与当前用户绑定的状态
     */
    int TYPE_CHECK_TAG_BIND_STATE = 2025;



}
