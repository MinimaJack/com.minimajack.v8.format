package com.minimajack.v8.model;

public abstract class AbstractContextHolder
    implements ContextStore
{
    private Context context;

    @Override
    public Context getContext()
    {
        return context;
    }

    @Override
    public void setContext( Context context )
    {
        this.context = context;
    }
}
