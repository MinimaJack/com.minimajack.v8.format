package com.minimajack.v8.metadata.tasks;

import com.minimajack.v8.annotation.V8Class;
import com.minimajack.v8.metadata.inner.classes.V8ClassObject;

import java.util.List;

@V8Class
public class TaskDescription {

  public Integer version;

  public TaskDescriptionBlock info;

  public List<V8ClassObject> sections;
}
