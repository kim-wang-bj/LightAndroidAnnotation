package com.wq.android.lightannotation;

import java.lang.reflect.Method;

/**
 * Created by wangqi on 2016/8/7.
 */
public interface MethodInterceptor {
    public void before(Object obj, Method method, Object... params);

    public void after(Object obj, Method method, Object... params);
}
