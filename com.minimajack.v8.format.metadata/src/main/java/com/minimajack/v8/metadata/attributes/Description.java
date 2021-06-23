package com.minimajack.v8.metadata.attributes;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.annotation.V8Since;
import com.minimajack.v8.annotation.V8Version;
import com.minimajack.v8.metadata.external.PatternDescription;
import com.minimajack.v8.metadata.external.V8MetaFullNameDescr;
import com.minimajack.v8.metadata.external.common.*;
import com.minimajack.v8.metadata.external.type.TypeValue;
import com.minimajack.v8.metadata.inner.enums.*;

import java.util.UUID;

@V8Class
public class Description {

  @V8Version
  public Integer version;

  @V8Since(version = 0, removed = 3)
  public MetadataMainInfo shortName;

  @V8Since(version = 0, removed = 3)
  public PatternDescription typeDescription;

  @V8Since(version = 3)
  public V8MetaFullNameDescr fullname;

  /**
   * Режим пароля.
   */
  @V8Since(version = 11)
  public Boolean passwordMode;

  /**
   * Форматная строка.
   */
  @V8Since(version = 11)
  public V8Synonym format;

  /**
   * Подсказка.
   */
  @V8Since(version = 11)
  public V8Synonym tooltip;

  /**
   * Выделять отрицательные.
   */
  @V8Since(version = 11)
  public Boolean markNegatives;

  /**
   * Маска.
   */
  @V8Since(version = 11)
  public String mask;

  /**
   * Многострочный режим.
   */
  @V8Since(version = 11)
  public Boolean multiLine;

  /**
   * Минимальное значение.
   */
  @V8Since(version = 11)
  public TypeValue minValue;

  /**
   * Максимальное значение.
   */
  @V8Since(version = 11)
  public TypeValue maxValue;

  /**
   * Выбор групп и элементов.
   */
  @V8Since(version = 11)
  public FoldersAndItemsUse foldersAndItemsUse;

  /**
   * Форма выбора.
   */
  @V8Since(version = 11)
  public UUID choiceForm;

  /**
   * Быстрый выбор.
   */
  @V8Since(version = 11)
  public UseQuickChoice.Values quickChoice;

  /**
   * Проверка заполнения.
   */
  @V8Since(version = 11)
  public FillChecking.Values fillChecking;

  /**
   * Связи параметров выбора.
   */
  @V8Since(version = 11)
  public ChoiceParameterLinks choiceParameterLinks;

  /**
   * Связь по типу.
   */
  @V8Since(version = 11)
  public TypeLinks typeLinks;

  /**
   * Параметры выбора.
   */
  @V8Since(version = 11)
  public ChoiceParameters choiceParameters;

  /**
   * Выделять отрицательное.
   */
  @V8Since(version = 11)
  public Boolean extendedEdit;

  /**
   * Формат редактирования.
   */
  @V8Since(version = 11)
  public V8Synonym formatEditing;

  /**
   * Значение заполнения.
   */
  @V8Since(version = 11)
  public TypeValue defaultValue;

  @V8Since(version = 11)
  public Boolean fillFromFillingValue;

  /**
   * Создание при вводе.
   */
  @V8Since(version = 26)
  public CreateOnInput.Values createOnInput;

  /**
   * Ведение истории выбора при вводе.
   */
  @V8Since(version = 27)
  public ChoiceHistoryOnInput.Values choiceHistoryOnInput;

}
