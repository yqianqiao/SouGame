package com.example.sougame.app.network.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by YX on 2020-07-20 10:33.
 */
public class ParameterizedTypeImpl implements ParameterizedType {
    private final Class raw;
    private final Type[] args;
    public ParameterizedTypeImpl(Class raw, Type[] args) {
        this.raw = raw;
        this.args = args != null ? args : new Type[0];
    }
    @Override
    public Type[] getActualTypeArguments() {
        return args;
    }
    @Override
    public Type getRawType() {
        return raw;
    }
    @Override
    public Type getOwnerType() {return null;}
}