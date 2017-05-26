package com.minimajack.v8.metadata.subsystems;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.metadata.commandgroups.Picture;
import com.minimajack.v8.metadata.external.common.MetadataMainInfo;
import com.minimajack.v8.metadata.external.common.V8Synonym;
import com.minimajack.v8.metadata.external.unknown.MetadataObjectPropertyValueCollection;

@V8Class
public class SubsystemsDescriptionBlock {

  public Integer version;
  public MetadataMainInfo v8mn;
  public Boolean includeHelpInContents;
  public MetadataObjectPropertyValueCollection unk2;
  public Boolean includeInCommandInterface;
  public Picture picture;
  public V8Synonym explanation;
  public MetadataObjectPropertyValueCollection content;
}
