package com.minimajack.v8.metadata.tasks;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.annotation.V8Since;
import com.minimajack.v8.annotation.V8Version;
import com.minimajack.v8.metadata.external.common.MetadataMainInfo;
import com.minimajack.v8.metadata.external.common.V8Synonym;
import com.minimajack.v8.metadata.external.unknown.CompactFlags;
import com.minimajack.v8.metadata.external.unknown.MetadataObjectPropertyValueCollection;
import com.minimajack.v8.metadata.inner.enums.*;
import com.minimajack.v8.metadata.persist.CharacteristicsDescriptions;
import com.minimajack.v8.metadata.persist.FieldList;
import com.minimajack.v8.metadata.persist.StandardAttributeDescriptions;

import java.util.UUID;

@V8Class
public class TaskDescriptionBlock {

  @V8Version
  public Integer version;

  public MetadataMainInfo v8mn;

  public Boolean useStandardCommands;

  public UUID unk2;

  public UUID unk3;

  public UUID unk4;

  public UUID unk5;

  public UUID unk6;

  public UUID unk7;

  public UUID unk8;

  public UUID unk9;

  public UUID unk10;

  public UUID unk11;

  public UUID unk12;

  public UUID unk13;

  public UUID defaultObjectForm;

  public UUID defaultListForm;

  public UUID defaultChoiceForm;

  public BusinessProcessNumberType.Values numberType;

  public Integer numberLength;

  public Boolean checkUnique;

  public Boolean autonumbering;

  public Integer descriptionLength;

  public EditType.Values editType;

  public Boolean includeHelpInContents;

  public UUID addressing;

  public UUID mainAddressingAttribute;

  public TaskMainPresentation.Values defaultPresentation;

  public FieldList inputByString;

  public UUID currentPerformer;

  public MetadataObjectPropertyValueCollection basedOn;

  public TaskNumberAutoPrefix.Values taskNumberAutoPrefix;

  public FullTextSearch.Values fullTextSearch;

  public DataLockControlMode.Values dataLockControlMode;

  public StandardAttributeDescriptions standardAttributes;

  public UUID auxiliaryObjectForm;

  public UUID auxiliaryListForm;

  public UUID auxiliaryChoiceForm;

  public V8Synonym objectPresentation;

  public V8Synonym extendedObjectPresentation;

  public V8Synonym listPresentation;

  public V8Synonym extendedListPresentation;

  public V8Synonym explanation;

  public AllowedLength.Values numberAllowedLength;

  public CharacteristicsDescriptions characteristics;

  @V8Since(version = 29)
  public CreateOnInput.Values createOnInput;

  @V8Since(version = 29)
  public FieldList dataLockFields;

  @V8Since(version = 29)
  public CompactFlags compactFlags;

  @V8Since(version = 29)
  public ChoiceHistoryOnInput.Values choiceHistoryOnInput;

}
