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

import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xuexiang
 * @since 2020-01-12 19:24
 */
public final class Utils {


    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * String数组转Set
     *
     * @param values
     * @return
     */
    public static Set<String> array2Set(String... values) {
        return new HashSet<>(Arrays.asList(values));
    }

    /**
     * 以“,”隔开的字符串转数组
     *
     * @param values
     * @return
     */
    public static String[] string2Array(String values) {
        if (!TextUtils.isEmpty(values)) {
            return values.split(",");
        }
        return new String[0];
    }

}
