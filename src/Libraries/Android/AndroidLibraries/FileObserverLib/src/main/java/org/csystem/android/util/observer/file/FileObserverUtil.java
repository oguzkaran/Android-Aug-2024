package org.csystem.android.util.observer.file;

import android.os.Build;
import android.os.FileObserver;

import java.io.File;

public final class FileObserverUtil {
    private FileObserverUtil()
    {
        throw new UnsupportedOperationException("Cannot create an instance of FileObserverUtil");
    }

    private static FileObserver createFileObserverBeforeSDK29(File file, int mask, IEventCallback callback)
    {
        return new FileObserver(file.getAbsolutePath(), mask) {
            @Override
            public void onEvent(int event, String path)
            {
                callback.onEvent(event, path);
            }
        };
    }

    private static FileObserver createFileObserverSDK29Plus(File file, int mask, IEventCallback callback)
    {
        return new FileObserver(file, mask) {
            @Override
            public void onEvent(int event, String path)
            {
                callback.onEvent(event, path);
            }
        };
    }

    public static FileObserver createFileObserver(File path, int mask, IEventCallback callback)
    {
        return Build.VERSION.SDK_INT < 29 ? createFileObserverBeforeSDK29(path, mask, callback)
                : createFileObserverSDK29Plus(path, mask, callback);
    }

    public static FileObserver createFileObserver(String path, int mask, IEventCallback callback)
    {
        return createFileObserver(new File(path), mask, callback);
    }

    public static FileObserver createFileObserver(File path, IEventCallback callback)
    {
        return createFileObserver(path, FileObserver.ALL_EVENTS, callback);
    }

    public static FileObserver createFileObserver(String path, IEventCallback callback)
    {
        return createFileObserver(new File(path), callback);
    }
}
