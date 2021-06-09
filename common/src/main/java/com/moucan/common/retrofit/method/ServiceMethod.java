package com.moucan.common.retrofit.method;

import com.moucan.common.retrofit.annotation.Field;
import com.moucan.common.retrofit.annotation.Post;
import com.moucan.common.retrofit.annotation.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import retrofit2.http.GET;

public class ServiceMethod {
    private final Call.Factory callFactory;
    private final String relativeUrl;
    private final boolean hasBody;
    private final ParameterHandler[] parameterHandler;
    private FormBody.Builder formBuild;
    HttpUrl baseUrl;
    String httpMethod;
    HttpUrl.Builder urlBuilder;

    public ServiceMethod(Builder builder) {
        baseUrl = builder.retrofit.baseUrl;
        callFactory = builder.retrofit.callFactory;

        httpMethod = builder.httpMethod;
        relativeUrl = builder.relativeUrl;
        hasBody = builder.hasBody;
        parameterHandler = builder.parameterHandler;

        //如果是有请求体,创建一个okhttp的请求体对象
        if (hasBody) {
            formBuild = new FormBody.Builder();
        }
    }

    public Object invoke(Object[] args) {
        for (int i = 0; i < parameterHandler.length; i++) {
            ParameterHandler parameterHandler = this.parameterHandler[i];
            parameterHandler.apply(this, args[i].toString());
        }
        HttpUrl url;
        if (urlBuilder == null) {
            urlBuilder = baseUrl.newBuilder(relativeUrl);
        }
        url = urlBuilder.build();
        FormBody body = null;
        if (formBuild != null) {
            body = formBuild.build();
        }
        Request request = new Request.Builder().url(url).method(httpMethod, body).build();


        return callFactory.newCall(request);
    }

    public static class Builder{
        private final CustomRetrofit retrofit;
        private final Annotation[] methodAnnotations;
        private final Annotation[][] parameterAnnotations;
        ParameterHandler[] parameterHandler;
        private String httpMethod;
        private String relativeUrl;
        private boolean hasBody;
        public Builder(CustomRetrofit retrofit, Method method) {
            this.retrofit = retrofit;
            this.parameterAnnotations = method.getParameterAnnotations();
            this.methodAnnotations = method.getAnnotations();
        }

        public ServiceMethod build(){
            for (Annotation methodAnnotation : methodAnnotations) {
                if (methodAnnotation instanceof Post) {
                    this.httpMethod = "POST";
                    this.relativeUrl = ((Post) methodAnnotation).value();
                    hasBody = true;
                } else if (methodAnnotation instanceof GET){
                    this.httpMethod = "GET";
                    this.relativeUrl = ((GET) methodAnnotation).value();
                    hasBody = false;
                }
            }

            int length = parameterAnnotations.length;
            parameterHandler = new ParameterHandler[length];
            for (int i = 0; i < length; i++) {
                Annotation[] parameterAnnotation = parameterAnnotations[i];
                for (Annotation annotation : parameterAnnotation) {
                    if (annotation instanceof Field) {
                        String value = ((Field) annotation).value();
                        parameterHandler[i] = new ParameterHandler.FiledParameterHandler(value);
                    } else if (annotation instanceof Query) {
                        String value = ((Query) annotation).value();
                        parameterHandler[i] = new ParameterHandler.QueryParameterHandler(value);
                    }
                }
            }

            return new ServiceMethod(this);
        }
    }

    public void addQueryParameter(String key, String value){
        if (urlBuilder == null) {
            urlBuilder = baseUrl.newBuilder();
        }
        urlBuilder.addQueryParameter(key, value);
    }

    public void addFieldParameter(String key, String value){
        formBuild.add(key, value);
    }
}
