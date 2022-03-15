package com.minimajack.v8.metadata.external.qualifier;

import com.minimajack.v8.metadata.external.qualifier.impl.*;
import com.minimajack.v8.transformers.AbstractTransformer;
import com.minimajack.v8.transformers.impl.ObjectTransformer;
import com.minimajack.v8.utility.SerializedOutputStream;
import com.minimajack.v8.utility.V8Reader;

import java.nio.ByteBuffer;

public class QualityTransformer implements AbstractTransformer<Qualifiers> {

  @Override
  public Qualifiers read(final ByteBuffer buffer) {
    buffer.mark();
    Qualifiers readedQuality;
    ObjectTransformer.readBracket(buffer);
    final String enums = V8Reader.read(String.class, buffer);
    buffer.reset();
    switch (enums) {
      case "N": {
        readedQuality = V8Reader.read(NumberQualifiers.class, buffer);
        break;
      }
      case "S": {
        readedQuality = V8Reader.read(StringQualifiers.class, buffer);
        break;
      }
      case "#": {
        readedQuality = V8Reader.read(DbLinkQuality.class, buffer);
        break;
      }
      case "D": {
        readedQuality = V8Reader.read(DateQualifiers.class, buffer);
        break;
      }
      case "B": {
        readedQuality = V8Reader.read(BooleanQuality.class, buffer);
        break;
      }
      case "U": {
        readedQuality = V8Reader.read(UndefinedQuality.class, buffer);
        break;
      }
      case "T": {
        throw new RuntimeException("Unsupported type: " + enums);
      }
      default:
        throw new IllegalStateException("Unexpected value: " + enums);
    }
    return readedQuality;
  }

  @Override
  public void write(Object object, SerializedOutputStream buffer) {
    throw new UnsupportedOperationException("Not implemented");
  }

}
