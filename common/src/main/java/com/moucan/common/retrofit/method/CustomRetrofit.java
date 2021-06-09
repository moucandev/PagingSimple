package com.moucan.common.retrofit.method;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class CustomRetrofit {
    final okhttp3.Call.Factory callFactory;
    final HttpUrl baseUrl;
    final Map<Method, ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();

    public CustomRetrofit(Call.Factory callFactory, HttpUrl baseUrl) {
        this.callFactory = callFactory;
        this.baseUrl = baseUrl;
    }

    public <T> T create(final Class<T> service) {
        Object o = Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ServiceMethod serviceMethod = loadServiceMethod(method);
                return serviceMethod.invoke(args);
            }
        });
        return (T) o;
    }

    private ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod serviceMethod = serviceMethodCache.get(method);
        if (serviceMethod != null) return serviceMethod;
        synchronized (serviceMethodCache) {
            serviceMethod = serviceMethodCache.get(method);
            if (serviceMethod == null) {
                serviceMethod = new ServiceMethod.Builder(this, method).build();
            }
        }
        return serviceMethod;
    }

    public static final class Builder{
        private okhttp3.Call.Factory callFactory;
        private HttpUrl baseUrl;


        public Builder callFactory(okhttp3.Call.Factory callFactory) {
            this.callFactory = callFactory;
            return this;
        }

        public Builder baseUrl(String httpUrl) {
            this.baseUrl = HttpUrl.get(httpUrl);
            return this;
        }

        public CustomRetrofit build() {
            if (baseUrl == null) {
                throw new IllegalStateException("Base URL required.");
            }
            okhttp3.Call.Factory callFactory = this.callFactory;
            if (callFactory == null) {
                callFactory = (Call.Factory) new OkHttpClient();
            }
            return new CustomRetrofit(callFactory, baseUrl);
        }
    }
}
