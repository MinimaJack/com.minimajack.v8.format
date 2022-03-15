package com.minimajack.v8.metadata.external.tabularsection;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.annotation.V8Since;
import com.minimajack.v8.annotation.V8Version;
import com.minimajack.v8.metadata.external.common.MetaDataInfo;
import com.minimajack.v8.metadata.external.common.V8Synonym;
import com.minimajack.v8.metadata.external.tabularsection.strange.StrangeObject;
import com.minimajack.v8.metadata.inner.enums.FillChecking;

import java.util.UUID;

@V8Class
public class TabularSectionDescription {

  @V8Version
  public Integer version;

  public UUID g1;

  public UUID g2;

  public UUID g3;

  public UUID g4;

  public MetaDataInfo name;

  /**
   * Проверка заполнения.
   */
  @V8Since(version = 3)
  public FillChecking.Values fillChecking;

  /**
   * Странный объект - возможно конфигурация или версии классов.
   * TODO: some object need to discover
   */
  @V8Since(version = 3)
  public StrangeObject unksyn;

  /**
   * Подсказка.
   */
  @V8Since(version = 3)
  public V8Synonym toolTip;
}
