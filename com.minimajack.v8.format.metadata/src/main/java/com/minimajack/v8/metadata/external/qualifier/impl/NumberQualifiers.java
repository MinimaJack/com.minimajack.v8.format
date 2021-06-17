package com.minimajack.v8.metadata.external.qualifier.impl;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.metadata.external.qualifier.Qualifiers;
import com.minimajack.v8.metadata.inner.enums.AllowedSign;

@V8Class
public class NumberQualifiers extends Qualifiers {

  public String type;
  
  public Integer digits;

  public Integer fractionDigits;

  public AllowedSign allowedSign;
}
