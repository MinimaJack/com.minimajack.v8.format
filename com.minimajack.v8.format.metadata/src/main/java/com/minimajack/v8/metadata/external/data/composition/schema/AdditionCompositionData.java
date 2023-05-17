package com.minimajack.v8.metadata.external.data.composition.schema;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.annotation.V8Version;

@V8Class
public class AdditionCompositionData {
    @V8Version
    public Integer version;
    public FileData settings;
    public FileData someAnother;
}
