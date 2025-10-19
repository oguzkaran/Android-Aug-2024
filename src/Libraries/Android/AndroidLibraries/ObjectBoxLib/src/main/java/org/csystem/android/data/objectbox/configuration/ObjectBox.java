package org.csystem.android.data.objectbox.configuration;

import android.content.Context;

import org.csystem.android.data.objectbox.entity.EntityBase;
import org.csystem.android.data.objectbox.entity.MyObjectBox;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public final class ObjectBox {
    private static BoxStore ms_boxStore;

    private ObjectBox()
    {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    public static void init(Context context)
    {
        ms_boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
    }

    @SafeVarargs
    public static <T extends EntityBase> List<Box<T>> boxes(Context context, Class<T>...entities)
    {
        if (ms_boxStore == null)
            init(context);

        var boxes = new ArrayList<Box<T>>(entities.length);

        for (var cls : entities)
            boxes.add(ms_boxStore.boxFor(cls));

        return boxes;
    }

    public static BoxStore get()
    {
        return ms_boxStore;
    }
}
