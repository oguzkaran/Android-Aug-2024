package org.csystem.app.demo.java;

class B {
    public void foo()
    {
        int a = 10;

        var ix = new IX() {
            @Override
            public void foo()
            {
                System.out.println(a);
            }

            @Override
            public void bar()
            {
                IX.super.bar();
            }
        };

        ix.foo();
    }
}


interface IX {
    void foo();
    default void bar()
    {
        System.out.println("IX.bar");
    }
}