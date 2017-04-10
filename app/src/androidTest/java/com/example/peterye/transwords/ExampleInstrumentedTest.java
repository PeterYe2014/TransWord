package com.example.peterye.transwords;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.lsj.trans.Dispatch;
import com.lsj.trans.GoogleDispatch;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Class.forName("com.lsj.trans.GoogleDispatch");
        Dispatch dispatch = GoogleDispatch.Instance("google");
        String result = dispatch.Trans("en","zh","hello");
        System.out.println(result);
        assertEquals("你好", result);
    }
}
