package com.minimajack.v8.metadata.external.dataprocessor;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.annotation.V8Since;
import com.minimajack.v8.annotation.V8Version;
import com.minimajack.v8.metadata.external.common.MetaDataInfo;

import java.util.UUID;

@V8Class
public class ExternalDataProcessorDescription {

  @V8Version
  public Integer version;

  public UUID objectUuid;

  public UUID g2;

  public MetaDataInfo name;

  public UUID defaultForm;

  public String unk;

  @V8Since(version = 4)
  public UUID auxiliaryForm;

}
