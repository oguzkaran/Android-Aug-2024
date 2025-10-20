package org.csystem.android.data.objectbox;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.csystem.android.data.objectbox.configuration.di.boxstore.BoxStoreConfig;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ObjectBoxClassText {
    private final Context m_appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private final ObjectBox m_objectBox = new ObjectBox(BoxStoreConfig.provideBoxStore(m_appContext));

    @Test
    public void test()
    {
        assertEquals(0, m_objectBox.boxes(m_appContext).size());
    }
}