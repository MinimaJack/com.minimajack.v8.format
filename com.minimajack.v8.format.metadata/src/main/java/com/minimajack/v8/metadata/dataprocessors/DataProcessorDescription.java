package com.minimajack.v8.metadata.dataprocessors;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.metadata.inner.classes.V8ClassObject;

import java.util.List;

@V8Class
public class DataProcessorDescription {

  public Integer version;
  public DataProcessorBlock info;
  public List<V8ClassObject> sections;
}
