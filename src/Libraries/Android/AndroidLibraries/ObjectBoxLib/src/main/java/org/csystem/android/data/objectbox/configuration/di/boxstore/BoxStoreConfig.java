package org.csystem.android.data.objectbox.configuration.di.boxstore;

import android.content.Context;

import org.csystem.android.data.objectbox.entity.MyObjectBox;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.objectbox.BoxStore;

@InstallIn(SingletonComponent.class)
@Module
public class BoxStoreConfig {
    @Singleton
    @Provides
    public static BoxStore provideBoxStore(@ApplicationContext Context context)
    {
        return MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
    }
}
