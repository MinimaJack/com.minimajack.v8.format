package com.minimajack.v8.io.reader;

import com.minimajack.v8.format.Container;
import com.minimajack.v8.model.ContextStore;

public interface AbstractReader
    extends ContextStore
{
    abstract public void setContainer( Container container );

    abstract public void read();
}