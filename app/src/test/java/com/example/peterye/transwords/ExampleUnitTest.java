package com.example.peterye.transwords;

import com.lsj.trans.Dispatch;
import com.lsj.trans.GoogleDispatch;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void tranlation_isCorrect() throws Exception {
        Class.forName("com.lsj.trans.GoogleDispatch");
        Dispatch dispatch = GoogleDispatch.Instance("google");
        String result = dispatch.Trans("en","zh","hello");
        System.out.println(result);
        assertEquals("你好", result);
    }
}