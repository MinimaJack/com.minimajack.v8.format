package com.minimajack.v8.model;

public interface Visitor<T>
{

    void visit( T member );
}
