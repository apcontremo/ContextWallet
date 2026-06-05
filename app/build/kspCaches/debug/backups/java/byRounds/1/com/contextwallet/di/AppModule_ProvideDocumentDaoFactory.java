package com.contextwallet.di;

import com.contextwallet.data.local.AppDatabase;
import com.contextwallet.data.local.dao.DocumentDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class AppModule_ProvideDocumentDaoFactory implements Factory<DocumentDao> {
  private final Provider<AppDatabase> databaseProvider;

  public AppModule_ProvideDocumentDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public DocumentDao get() {
    return provideDocumentDao(databaseProvider.get());
  }

  public static AppModule_ProvideDocumentDaoFactory create(
      javax.inject.Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvideDocumentDaoFactory(Providers.asDaggerProvider(databaseProvider));
  }

  public static AppModule_ProvideDocumentDaoFactory create(Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvideDocumentDaoFactory(databaseProvider);
  }

  public static DocumentDao provideDocumentDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDocumentDao(database));
  }
}
