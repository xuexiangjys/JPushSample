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

import static com.xuexiang.jpushsample.core.push.event.MessageType.TYPE_CUSTOM;
import static com.xuexiang.jpushsample.core.push.event.MessageType.TYPE_NOTIFICATION;

/**
 * 推送消息
 *
 * @author xuexiang
 * @since 2020-01-13 00:21
 */
public final class PushMessage {

    public static final String KEY_PUSH_MESSAGE = "com.xuexiang.jpushsample.core.push.event.KEY_PUSH_MESSAGE";

    /**
     * 消息类型
     */
    private int mType;
    /**
     * 消息数据
     */
    private Object mMessage;

    public static PushMessage wrap(@MessageType int type, Object message) {
        return new PushMessage(type, message);
    }

    public PushMessage(@MessageType int type, Object message) {
        mType = type;
        mMessage = message;
    }

    public int getType() {
        return mType;
    }

    public PushMessage setType(int type) {
        mType = type;
        return this;
    }

    public <T> T getMessage() {
        return (T) mMessage;
    }

    public PushMessage setMessage(Object message) {
        mMessage = message;
        return this;
    }

    public String getMessageType() {
        switch (mType) {
            case TYPE_CUSTOM:
                return "自定义消息";
            case TYPE_NOTIFICATION:
                return "普通通知消息";
            default:
                return "未知消息";
        }
    }

    @Override
    public String toString() {
        return "PushMessage{" +
                "mType=" + mType +
                ", mMessage=" + mMessage +
                '}';
    }
}
