package com.minimajack.v8.metadata.external.type;

import com.minimajack.v8.metadata.external.type.impl.*;
import com.minimajack.v8.metadata.inner.classes.V8InnerClass;
import com.minimajack.v8.transformers.AbstractTransformer;
import com.minimajack.v8.transformers.impl.ObjectTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class TypesTransformer implements AbstractTransformer<TypeValue> {

  @Override
  public TypeValue read(final ByteBuffer buffer) {
    TypeValue readedType = new TypeValue();
    ObjectTransformer.readBracket(buffer);
    final String enums = V8Reader.read(String.class, buffer);
    switch (enums) {
      case "S": {
        V8Reader.readChar(buffer, ',');
        readedType.setValue( V8Reader.read(StringType.class, buffer));
        break;
      }
      case "#": {
        V8Reader.readChar(buffer, ',');
        final InnerClassTypeValue classValue = new InnerClassTypeValue();
        classValue.setValue(V8Reader.read(V8InnerClass.class, buffer));
        readedType.setValue(classValue);
        break;
      }
      case "B": {
        V8Reader.readChar(buffer, ',');
        readedType.setValue(V8Reader.read(BooleanType.class, buffer));
        break;
      }
      case "U": {
        readedType.setValue(V8Reader.read(UndefinedType.class, buffer));
        break;
      }
      case "D": {
        V8Reader.readChar(buffer, ',');
        readedType.setValue(V8Reader.read(DateType.class, buffer));
        break;
      }
      case "N": {
        V8Reader.readChar(buffer, ',');
        readedType.setValue(V8Reader.read(NumberType.class, buffer));
        break;
      }
      case "T":
        throw new RuntimeException("Unsupported type: " + enums);
    }
    ObjectTransformer.readCloseBracket(buffer);
    return readedType;
  }

  @Override
  public void write(Object object, SerializedOutputStream buffer) {
    buffer.putOpenBracket();
    var innerValue = ((TypeValue)object).getValue();
    Class<?> clazz = innerValue.getClass();
    if (innerValue instanceof StringType){
      buffer.writeBytes("\"S\"".getBytes(StandardCharsets.UTF_8));
    } else if(innerValue instanceof InnerClassTypeValue){
      buffer.writeBytes("\"#\"".getBytes(StandardCharsets.UTF_8));
    } else if(innerValue instanceof BooleanType){
      buffer.writeBytes("\"B\"".getBytes(StandardCharsets.UTF_8));
    } else if(innerValue instanceof UndefinedType){
      buffer.writeBytes("\"U\"".getBytes(StandardCharsets.UTF_8));
    } else if(innerValue instanceof DateType){
      buffer.writeBytes("\"D\"".getBytes(StandardCharsets.UTF_8));
    } else if(innerValue instanceof NumberType){
      buffer.writeBytes("\"N\"".getBytes(StandardCharsets.UTF_8));
    } else{
      throw new RuntimeException("Unsupported type: " + innerValue.getClass());
    }

    if(!(innerValue instanceof UndefinedType)){
      buffer.putComa();
      if(innerValue instanceof  InnerClassTypeValue){
        var rawInnerValue = ((InnerClassTypeValue)innerValue).getValue();
        V8Reader.write(rawInnerValue, buffer);
      }else {
        V8Reader.write(innerValue, buffer);
      }
    }
    buffer.putCloseBracket();
  }

}
