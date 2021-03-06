package com.minimajack.v8.metadata.wsreferences;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.annotation.V8Version;
import com.minimajack.v8.metadata.external.common.MetadataMainInfo;

import java.util.UUID;

@V8Class
public class WSReferencesDescriptionBlock {

  @V8Version
  public Integer version;
  public LocationURL locationURL;
  public MetadataMainInfo v8mn;
  public UUID unk1;
  public UUID unk2;
}
