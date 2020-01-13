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

/**
 * 推送事件
 *
 * @author xuexiang
 * @since 2020-01-12 16:17
 */
public final class PushEvent {

    public static final String KEY_PUSH_EVENT = "com.xuexiang.jpushsample.core.push.event.KEY_PUSH_EVENT";

    /**
     * 事件类型
     */
    private int mType;
    /**
     * 是否成功
     */
    private boolean mIsSuccess;
    /**
     * 携带的数据
     */
    private Object mData;

    public PushEvent(@EventType int type) {
        mType = type;
    }

    public PushEvent(@EventType int type, boolean isSuccess) {
        mType = type;
        mIsSuccess = isSuccess;
    }

    public PushEvent(@EventType int type, Object data) {
        mType = type;
        mData = data;
    }

    public PushEvent(@EventType int type, boolean isSuccess, Object data) {
        mType = type;
        mIsSuccess = isSuccess;
        mData = data;
    }

    public int getType() {
        return mType;
    }

    public PushEvent setType(@EventType int type) {
        mType = type;
        return this;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public PushEvent setSuccess(boolean success) {
        mIsSuccess = success;
        return this;
    }

    public Object getData() {
        return mData;
    }

    public PushEvent setData(Object data) {
        mData = data;
        return this;
    }

    @Override
    public String toString() {
        return "PushEvent{" +
                "mType=" + mType +
                ", mIsSuccess=" + mIsSuccess +
                ", mData=" + mData +
                '}';
    }
}
