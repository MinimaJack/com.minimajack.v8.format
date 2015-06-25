package com.minimajack.v8.model;

/**
 * 
 * @param <T>
 */
public interface Visitable<T>
{

    /**
     * @param visitor member to visit all
     */
    void iterate( Visitor<T> visitor );

}
