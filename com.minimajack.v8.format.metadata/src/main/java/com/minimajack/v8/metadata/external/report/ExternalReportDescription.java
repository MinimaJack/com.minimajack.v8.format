package com.minimajack.v8.metadata.external.report;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.annotation.V8Since;
import com.minimajack.v8.annotation.V8Version;
import com.minimajack.v8.metadata.external.common.MetaDataInfo;

import java.util.UUID;

@V8Class
public class ExternalReportDescription {

  @V8Version
  public Integer version;

  public UUID typeId;

  public UUID valueId;

  public MetaDataInfo name;

  public UUID defaultForm;
  public UUID mainDataCompositionSchema;
  public UUID defaultSettingsForm;
  @V8Since(version = 2)
  public UUID auxiliaryForm;
  @V8Since(version = 2)
  public UUID auxiliarySettingsForm;
  @V8Since(version = 2)
  public UUID defaultVariantForm;
  public String unk5;
  @V8Since(version = 2)
  public UUID variantsStorage;
  @V8Since(version = 2)
  public UUID settingsStorage;

}
