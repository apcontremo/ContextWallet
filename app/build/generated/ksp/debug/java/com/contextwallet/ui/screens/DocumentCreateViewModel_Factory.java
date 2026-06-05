package com.contextwallet.ui.screens;

import android.content.Context;
import com.contextwallet.data.repository.DocumentRepository;
import com.contextwallet.data.repository.GeocodingRepository;
import com.contextwallet.util.DebugLogger;
import com.contextwallet.util.FileManager;
import com.contextwallet.util.LocationProvider;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class DocumentCreateViewModel_Factory implements Factory<DocumentCreateViewModel> {
  private final Provider<DocumentRepository> repositoryProvider;

  private final Provider<GeocodingRepository> geocodingRepositoryProvider;

  private final Provider<FileManager> fileManagerProvider;

  private final Provider<LocationProvider> locationProvider;

  private final Provider<DebugLogger> debugLoggerProvider;

  private final Provider<Context> contextProvider;

  public DocumentCreateViewModel_Factory(Provider<DocumentRepository> repositoryProvider,
      Provider<GeocodingRepository> geocodingRepositoryProvider,
      Provider<FileManager> fileManagerProvider, Provider<LocationProvider> locationProvider,
      Provider<DebugLogger> debugLoggerProvider, Provider<Context> contextProvider) {
    this.repositoryProvider = repositoryProvider;
    this.geocodingRepositoryProvider = geocodingRepositoryProvider;
    this.fileManagerProvider = fileManagerProvider;
    this.locationProvider = locationProvider;
    this.debugLoggerProvider = debugLoggerProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public DocumentCreateViewModel get() {
    return newInstance(repositoryProvider.get(), geocodingRepositoryProvider.get(), fileManagerProvider.get(), locationProvider.get(), debugLoggerProvider.get(), contextProvider.get());
  }

  public static DocumentCreateViewModel_Factory create(
      javax.inject.Provider<DocumentRepository> repositoryProvider,
      javax.inject.Provider<GeocodingRepository> geocodingRepositoryProvider,
      javax.inject.Provider<FileManager> fileManagerProvider,
      javax.inject.Provider<LocationProvider> locationProvider,
      javax.inject.Provider<DebugLogger> debugLoggerProvider,
      javax.inject.Provider<Context> contextProvider) {
    return new DocumentCreateViewModel_Factory(Providers.asDaggerProvider(repositoryProvider), Providers.asDaggerProvider(geocodingRepositoryProvider), Providers.asDaggerProvider(fileManagerProvider), Providers.asDaggerProvider(locationProvider), Providers.asDaggerProvider(debugLoggerProvider), Providers.asDaggerProvider(contextProvider));
  }

  public static DocumentCreateViewModel_Factory create(
      Provider<DocumentRepository> repositoryProvider,
      Provider<GeocodingRepository> geocodingRepositoryProvider,
      Provider<FileManager> fileManagerProvider, Provider<LocationProvider> locationProvider,
      Provider<DebugLogger> debugLoggerProvider, Provider<Context> contextProvider) {
    return new DocumentCreateViewModel_Factory(repositoryProvider, geocodingRepositoryProvider, fileManagerProvider, locationProvider, debugLoggerProvider, contextProvider);
  }

  public static DocumentCreateViewModel newInstance(DocumentRepository repository,
      GeocodingRepository geocodingRepository, FileManager fileManager,
      LocationProvider locationProvider, DebugLogger debugLogger, Context context) {
    return new DocumentCreateViewModel(repository, geocodingRepository, fileManager, locationProvider, debugLogger, context);
  }
}
