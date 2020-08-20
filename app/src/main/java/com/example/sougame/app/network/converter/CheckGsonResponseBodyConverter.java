package com.example.sougame.app.network.converter;

import com.example.common.network.AppException;
import com.example.common.network.BaseResponse;
import com.example.sougame.data.model.bean.MyBean;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Android Studio.
 * Author: yx
 * Date: 2020/6/28 15:18
 * Description: com.example.sougame.app.network
 */
public class CheckGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;
    private final Gson mGson;
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    CheckGsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.mGson = new Gson();
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
//        MyBean result = new Gson().fromJson(response, new TypeToken() {
//        }.getType());
//        Type genType = this.getClass().getGenericSuperclass();
//        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
//
//        Type actualType = params[0];
//        if (!(actualType instanceof Class)) {
//            Type typeOfT = ((ParameterizedType) actualType).getActualTypeArguments()[0];
//            Type listType = new ParameterizedTypeImpl(List.class, new Type[]{typeOfT});
//            // 根据List<T>生成完整的ResBean<List<T>>
//            actualType = new ParameterizedTypeImpl(BaseResponse.class, new Type[]{listType});
//        }
//        BaseResponse<T> bean = mGson.fromJson(response, actualType);
//        // 对code判断后返回T
//        T data = bean.getResponseData();

        MyBean result = mGson.fromJson(response, MyBean.class);
        int responseCode = result.getCode();
        //如果响应码不为正常响应我们就抛出一个自定义的异常，onError方法回去捕获这个异常
        if (responseCode != 1) {
            value.close();
            throw new AppException(responseCode, result.getMessage(), result.getMessage());

        }
        MediaType type = value.contentType();
        Charset charset = type != null ? type.charset(UTF_8) : UTF_8;
        InputStream is = new ByteArrayInputStream(response.getBytes());
        InputStreamReader reader = new InputStreamReader(is, charset);
        JsonReader jsonReader = mGson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
//            return data;
        } finally {
            value.close();
        }
    }
}