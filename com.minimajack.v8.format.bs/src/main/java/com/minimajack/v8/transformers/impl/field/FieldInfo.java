package com.minimajack.v8.transformers.impl.field;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import com.minimajack.v8.annotation.V8Since;

public class FieldInfo
{
    public Field field;
    public Class<?> fieldType;
    public Type paramType;
    public V8Since since;
    public boolean required;
    public boolean isVersion;
    public String name;
}
