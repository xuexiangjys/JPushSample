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

import static com.xuexiang.jpushsample.core.push.event.MessageType.TYPE_CUSTOM;
import static com.xuexiang.jpushsample.core.push.event.MessageType.TYPE_NOTIFICATION;

/**
 * 消息的类型
 *
 * @author xuexiang
 * @since 2020-01-13 00:28
 */
@IntDef({TYPE_CUSTOM, TYPE_NOTIFICATION})
@Retention(RetentionPolicy.SOURCE)
public @interface MessageType {

    /**
     * 自定义消息
     */
    int TYPE_CUSTOM = 1000;

    /**
     * 普通通知消息
     */
    int TYPE_NOTIFICATION = 1001;

}
