package com.moucan.common.click;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Classname: InjectClick
 * @Description:
 * @Since: 2021/6/8 1:48 下午
 * @Author: moucan
 */
public class InjectClick {

    public static void injectOnClick(Activity activity) {
        Class<? extends Activity> cls = activity.getClass();
        Method[] declaredMethods = cls.getDeclaredMethods();
        for (Method declaredMethod: declaredMethods) {
            if (declaredMethod.isAnnotationPresent(OnClick.class)) {
                OnClick onClick = declaredMethod.getAnnotation(OnClick.class);
                int[] ids = onClick.value();
                Object o = Proxy.newProxyInstance(View.OnClickListener.class.getClassLoader(), new Class[]{View.OnClickListener.class},
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                return declaredMethod.invoke(activity, args);
                            }
                        });
                for (int id : ids) {
                    View view = activity.findViewById(id);
                    try {
                        Method setOnClickListener = view.getClass().getMethod("setOnClickListener", View.OnClickListener.class);
                        setOnClickListener.invoke(view, o);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
