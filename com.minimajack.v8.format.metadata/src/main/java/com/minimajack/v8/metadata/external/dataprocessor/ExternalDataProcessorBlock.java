package com.minimajack.v8.metadata.external.dataprocessor;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.metadata.inner.classes.V8ClassObject;

import java.util.List;

@V8Class
public class ExternalDataProcessorBlock {

  public Integer version;

  public ExternalDataProcessorDescription sd;

  public List<V8ClassObject> sections;

}
