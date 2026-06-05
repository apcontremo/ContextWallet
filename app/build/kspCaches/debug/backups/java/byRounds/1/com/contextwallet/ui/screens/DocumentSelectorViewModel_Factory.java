package com.contextwallet.ui.screens;

import com.contextwallet.data.repository.DocumentRepository;
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
public final class DocumentSelectorViewModel_Factory implements Factory<DocumentSelectorViewModel> {
  private final Provider<DocumentRepository> repositoryProvider;

  private final Provider<FileManager> fileManagerProvider;

  private final Provider<LocationProvider> locationProvider;

  public DocumentSelectorViewModel_Factory(Provider<DocumentRepository> repositoryProvider,
      Provider<FileManager> fileManagerProvider, Provider<LocationProvider> locationProvider) {
    this.repositoryProvider = repositoryProvider;
    this.fileManagerProvider = fileManagerProvider;
    this.locationProvider = locationProvider;
  }

  @Override
  public DocumentSelectorViewModel get() {
    return newInstance(repositoryProvider.get(), fileManagerProvider.get(), locationProvider.get());
  }

  public static DocumentSelectorViewModel_Factory create(
      javax.inject.Provider<DocumentRepository> repositoryProvider,
      javax.inject.Provider<FileManager> fileManagerProvider,
      javax.inject.Provider<LocationProvider> locationProvider) {
    return new DocumentSelectorViewModel_Factory(Providers.asDaggerProvider(repositoryProvider), Providers.asDaggerProvider(fileManagerProvider), Providers.asDaggerProvider(locationProvider));
  }

  public static DocumentSelectorViewModel_Factory create(
      Provider<DocumentRepository> repositoryProvider, Provider<FileManager> fileManagerProvider,
      Provider<LocationProvider> locationProvider) {
    return new DocumentSelectorViewModel_Factory(repositoryProvider, fileManagerProvider, locationProvider);
  }

  public static DocumentSelectorViewModel newInstance(DocumentRepository repository,
      FileManager fileManager, LocationProvider locationProvider) {
    return new DocumentSelectorViewModel(repository, fileManager, locationProvider);
  }
}
