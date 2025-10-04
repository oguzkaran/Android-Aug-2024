package org.csystem.android.util.observer.file;

@FunctionalInterface
public interface IEventCallback {
    void onEvent(int event, String path);
}
