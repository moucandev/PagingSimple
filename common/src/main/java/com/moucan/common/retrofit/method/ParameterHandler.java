package com.moucan.common.retrofit.method;

public abstract class ParameterHandler {
    abstract void apply(ServiceMethod serviceMethod, String value);

    static class QueryParameterHandler extends ParameterHandler {
        String key;

        public QueryParameterHandler(String key) {
            this.key = key;
        }

        //serviceMethod: 回调
        @Override
        void apply(ServiceMethod serviceMethod, String value) {
            serviceMethod.addQueryParameter(key,value);
        }
    }

    static class FiledParameterHandler extends ParameterHandler {
        String key;

        public FiledParameterHandler(String key) {
            this.key = key;
        }

        @Override
        void apply(ServiceMethod serviceMethod, String value) {
            serviceMethod.addFieldParameter(key,value);
        }
    }
}
