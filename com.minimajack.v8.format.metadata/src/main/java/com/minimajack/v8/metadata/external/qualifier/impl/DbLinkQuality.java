package com.minimajack.v8.metadata.external.qualifier.impl;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.metadata.external.qualifier.Qualifiers;

import java.util.UUID;

@V8Class
public class DbLinkQuality extends Qualifiers {

  public String type;

  public UUID uuid;
}
