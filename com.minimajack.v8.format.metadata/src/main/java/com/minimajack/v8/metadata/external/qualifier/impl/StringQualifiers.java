package com.minimajack.v8.metadata.external.qualifier.impl;

import com.minimajack.v8.annotation.Required;
import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.metadata.external.qualifier.Qualifiers;

@V8Class
public class StringQualifiers extends Qualifiers {

  public String type;

  @Required(required = false)
  public Integer required;

  @Required(required = false)
  public Integer q2;
}
