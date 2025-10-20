package org.csystem.android.data.objectbox;

import android.content.Context;

import org.csystem.android.data.objectbox.entity.EntityBase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ObjectBox {
    private final BoxStore m_boxStore;

    @Inject
    ObjectBox(BoxStore boxStore)
    {
        m_boxStore = boxStore;
    }

    public <T extends EntityBase> List<Box<T>> boxes(Context context, Class<T>...entities)
    {
        var boxes = new ArrayList<Box<T>>(entities.length);

        for (var cls : entities)
            boxes.add(m_boxStore.boxFor(cls));

        return boxes;
    }

    public BoxStore get()
    {
        return m_boxStore;
    }
}
