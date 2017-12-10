package com.lph.interview;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        text();
    }

    public void text() {
        Integer f1 = 100, f2 = 100, f3 = 100;
        System.out.println(f1 == f2);
        Integer a = new Integer(3);
        int b = 3;
        Integer c = 3;
        System.out.println(a == b);//true
        System.out.println(a==c);//false
    }
}