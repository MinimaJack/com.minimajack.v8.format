package com.minimajack.v8.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.minimajack.v8.transformers.AbstractTransformer;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Transformer {
    Class<? extends AbstractTransformer<?>> transformer();
}