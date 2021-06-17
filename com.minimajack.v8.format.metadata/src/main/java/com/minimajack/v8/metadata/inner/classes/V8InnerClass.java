package com.minimajack.v8.metadata.inner.classes;

import com.minimajack.v8.annotation.Transformer;
import com.minimajack.v8.metadata.inner.classes.transformer.InnerClassTransformer;

import java.util.UUID;

@Transformer(transformer = InnerClassTransformer.class)
public class V8InnerClass {

  public UUID classUUID;
  public V8InnerClass value;

  public UUID getClassUUID() {
    return classUUID;
  }

  public void setClassUUID(UUID classUUID) {
    this.classUUID = classUUID;
  }

  public V8InnerClass getValue() {
    return value;
  }

  public void setValue(V8InnerClass value) {
    this.value = value;
  }
}
