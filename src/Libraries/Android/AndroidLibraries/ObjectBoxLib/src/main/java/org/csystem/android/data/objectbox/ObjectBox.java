package org.csystem.android.data.objectbox;

import android.content.Context;

import org.csystem.android.data.objectbox.entity.EntityBase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Convenience helper around an ObjectBox {@link BoxStore}.
 *
 * <p>Provides utility methods to obtain {@link Box} instances for entity classes and to access
 * the underlying {@link BoxStore}.</p>
 */
public class ObjectBox {
    private final BoxStore m_boxStore;

    /**
     * Constructs an {@code ObjectBox} wrapper using the provided {@link BoxStore}.
     *
     * @param boxStore the {@link BoxStore} instance to use; must not be {@code null}
     */
    @Inject
    ObjectBox(BoxStore boxStore)
    {
        m_boxStore = boxStore;
    }

    /**
     * Returns a list of {@link Box} instances for the given entity classes.
     *
     * <p>The returned list contains one {@code Box<T>} for each class in {@code entities}, in the
     * same order. The provided {@link Context} parameter is not used by this implementation but is
     * kept for API compatibility.</p>
     *
     * @param context the Android {@link Context}; may be {@code null}
     * @param entities a varargs array of entity {@link Class} objects for which boxes are requested;
     *                 must not be {@code null}
     * @param <T> the entity type extending {@link EntityBase}
     * @return a non-null {@link List} of {@link Box} objects; its size equals {@code entities.length}
     * @throws NullPointerException if {@code entities} is {@code null}
     */
    public <T extends EntityBase> List<Box<T>> boxes(Context context, Class<T>...entities)
    {
        var boxes = new ArrayList<Box<T>>(entities.length);

        for (var cls : entities)
            boxes.add(m_boxStore.boxFor(cls));

        return boxes;
    }

    /**
     * Returns the underlying {@link BoxStore} instance.
     *
     * @return the {@link BoxStore} used by this helper; never {@code null}
     */
    public BoxStore get()
    {
        return m_boxStore;
    }
}