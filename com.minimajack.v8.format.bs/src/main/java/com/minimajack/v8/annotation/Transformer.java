package com.minimajack.v8.annotation;

import com.minimajack.v8.transformers.AbstractTransformer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Transformer {
    Class<? extends AbstractTransformer<?>> transformer();
}