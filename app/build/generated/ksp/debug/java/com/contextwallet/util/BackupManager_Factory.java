package com.contextwallet.util;

import android.content.Context;
import com.contextwallet.data.local.AppDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class BackupManager_Factory implements Factory<BackupManager> {
  private final Provider<Context> contextProvider;

  private final Provider<AppDatabase> databaseProvider;

  public BackupManager_Factory(Provider<Context> contextProvider,
      Provider<AppDatabase> databaseProvider) {
    this.contextProvider = contextProvider;
    this.databaseProvider = databaseProvider;
  }

  @Override
  public BackupManager get() {
    return newInstance(contextProvider.get(), databaseProvider.get());
  }

  public static BackupManager_Factory create(javax.inject.Provider<Context> contextProvider,
      javax.inject.Provider<AppDatabase> databaseProvider) {
    return new BackupManager_Factory(Providers.asDaggerProvider(contextProvider), Providers.asDaggerProvider(databaseProvider));
  }

  public static BackupManager_Factory create(Provider<Context> contextProvider,
      Provider<AppDatabase> databaseProvider) {
    return new BackupManager_Factory(contextProvider, databaseProvider);
  }

  public static BackupManager newInstance(Context context, AppDatabase database) {
    return new BackupManager(context, database);
  }
}
