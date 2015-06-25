package com.minimajack.v8.model;

public class FakeMemoryAllocator
{
    private int size = 0;

    public synchronized int allocate( int size )
    {
        this.size += size;
        return this.size;
    }

    public int getAllocationSize()
    {
        return this.size;
    }
}
