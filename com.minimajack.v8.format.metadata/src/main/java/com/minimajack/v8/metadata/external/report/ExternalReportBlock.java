package com.minimajack.v8.metadata.external.report;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.metadata.inner.classes.V8ClassObject;

import java.util.List;

@V8Class
public class ExternalReportBlock {

  public Integer version;

  public ExternalReportDescription sd;

  public List<V8ClassObject> sections;

}
