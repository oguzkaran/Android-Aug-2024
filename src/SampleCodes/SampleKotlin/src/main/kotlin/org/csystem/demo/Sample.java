package org.csystem.demo;

import java.util.Scanner;

public class Sample {
    public int x;

    public Sample(int x)
    {
        int a;
        do {
            a = 10;
            //...
        } while (a > 0);
    }
}


class Mample {
    public static void foo()
    {
        Sample s = new Sample(10);

        s.x = 20;
    }
}