package org.csystem.app.demo.java;

abstract class A {
    abstract int getX();
    abstract void setX(int value);
    //...
}

class B extends A {
    private int m_x;

    @Override
    public int getX()
    {
        return m_x;
    }

    public void setX(int value)
    {
        m_x = Math.abs(value);
    }
}