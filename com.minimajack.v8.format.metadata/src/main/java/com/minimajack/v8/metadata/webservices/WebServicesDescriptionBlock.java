package com.minimajack.v8.metadata.webservices;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.annotation.V8Since;
import com.minimajack.v8.annotation.V8Version;
import com.minimajack.v8.metadata.external.common.MetadataMainInfo;
import com.minimajack.v8.metadata.external.unknown.MetadataObjectPropertyValueCollection;

@V8Class
public class WebServicesDescriptionBlock {

  @V8Version
  public Integer version;
  public String namespace;
  public MetadataMainInfo v8mn;
  public MetadataObjectPropertyValueCollection xdtoPackages;
  public String descriptorFileName;
  public WebServiceImportedPackets xdtoInnerPackages;
  @V8Since(version = 4)
  public Integer unk3;
  @V8Since(version = 4)
  public Integer unk4;
}
