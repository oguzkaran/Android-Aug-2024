package org.csystem.android.util.intent;

import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Utility class with helper methods to safely retrieve typed extras from an {@link Intent}.
 *
 * <p>This class centralizes the API-level aware retrieval of {@link Serializable} and {@link Parcelable}
 * extras to avoid repeated version checks throughout the codebase.</p>
 *
 * <p>All methods are static and the class cannot be instantiated.</p>
 */
public final class IntentUtil {
    /**
     * Private constructor to prevent instantiation.
     *
     * @throws UnsupportedOperationException always thrown to indicate this is a utility class
     */
    private IntentUtil()
    {
        throw new UnsupportedOperationException("Cannot create an instance of IntentUtil");
    }

    /**
     * Retrieve a {@link Serializable} extra from the given {@link Intent} in a type-safe manner.
     *
     * <p>On API level {@code Build.VERSION_CODES.TIRAMISU} and above the platform provides a
     * typed {@code getSerializableExtra(String, Class<T>)} method that avoids unchecked casts.
     * On older API levels this method falls back to the untyped {@code getSerializableExtra(String)}
     * and performs a cast to the requested type.</p>
     *
     * @param intent the {@link Intent} containing the extra (may be {@code null})
     * @param name the name of the extra to retrieve
     * @param cls the {@link Class} object of the requested {@link Serializable} subtype
     * @param <T> the concrete type that extends {@link Serializable}
     * @return the extra cast to {@code T}, or {@code null} if the extra is not present or cannot be cast
     */
    public static <T extends Serializable> T getSerializableExtra(Intent intent, String name, Class<T> cls)
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ? intent.getSerializableExtra(name, cls) : (T)intent.getSerializableExtra(name);
    }

    /**
     * Convenience wrapper that adds a {@link Serializable} extra to the provided {@link Intent}.
     *
     * <p>This method delegates to {@link Intent#putExtra(String, Serializable)} and returns the
     * same {@code Intent} instance to allow fluent usage.</p>
     *
     * @param intent the {@link Intent} to which the extra will be added; must not be {@code null}
     * @param name the key under which the serializable value will be stored
     * @param serializable the {@link Serializable} value to add; may be {@code null}
     * @return the same {@link Intent} instance after the extra has been added
     * @throws NullPointerException if {@code intent} is {@code null}
     */
    public static Intent putExtra(Intent intent, String name, Serializable serializable)
    {
        return intent.putExtra(name, serializable);
    }

    /**
     * Retrieve a {@link Parcelable} extra from the given {@link Intent} in a type-safe manner.
     *
     * <p>On API level {@code Build.VERSION_CODES.TIRAMISU} and above the platform provides a
     * typed {@code getParcelableExtra(String, Class<T>)} method that avoids unchecked casts.
     * On older API levels this method falls back to the untyped {@code getParcelableExtra(String)}
     * and performs a cast to the requested type.</p>
     *
     * @param intent the {@link Intent} containing the extra (may be {@code null})
     * @param name the name of the extra to retrieve
     * @param cls the {@link Class} object of the requested {@link Parcelable} subtype
     * @param <T> the concrete type that extends {@link Parcelable}
     * @return the extra cast to {@code T}, or {@code null} if the extra is not present or cannot be cast
     */
    public static <T extends Parcelable> T getParcelableExtra(Intent intent, String name, Class<T> cls)
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ? intent.getParcelableExtra(name, cls) : (T)intent.getParcelableExtra(name);
    }

    /**
     * Convenience wrapper that adds a {@link Parcelable} extra to the provided {@link Intent}.
     *
     * <p>This method delegates to {@link Intent#putExtra(String, Parcelable)} and returns the
     * same {@code Intent} instance to allow fluent usage.</p>
     *
     * @param intent the {@link Intent} to which the extra will be added; must not be {@code null}
     * @param name the key under which the parcelable value will be stored
     * @param parcelable the {@link Parcelable} value to add; may be {@code null}
     * @return the same {@link Intent} instance after the extra has been added
     * @throws NullPointerException if {@code intent} is {@code null}
     */
    public static Intent putExtra(Intent intent, String name, Parcelable parcelable)
    {
        return intent.putExtra(name, parcelable);
    }
}