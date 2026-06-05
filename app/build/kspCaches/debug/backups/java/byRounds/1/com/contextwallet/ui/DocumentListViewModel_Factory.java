package com.contextwallet.ui;

import com.contextwallet.data.repository.DocumentRepository;
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
public final class DocumentListViewModel_Factory implements Factory<DocumentListViewModel> {
  private final Provider<DocumentRepository> repositoryProvider;

  private final Provider<LocationProvider> locationProvider;

  public DocumentListViewModel_Factory(Provider<DocumentRepository> repositoryProvider,
      Provider<LocationProvider> locationProvider) {
    this.repositoryProvider = repositoryProvider;
    this.locationProvider = locationProvider;
  }

  @Override
  public DocumentListViewModel get() {
    return newInstance(repositoryProvider.get(), locationProvider.get());
  }

  public static DocumentListViewModel_Factory create(
      javax.inject.Provider<DocumentRepository> repositoryProvider,
      javax.inject.Provider<LocationProvider> locationProvider) {
    return new DocumentListViewModel_Factory(Providers.asDaggerProvider(repositoryProvider), Providers.asDaggerProvider(locationProvider));
  }

  public static DocumentListViewModel_Factory create(
      Provider<DocumentRepository> repositoryProvider,
      Provider<LocationProvider> locationProvider) {
    return new DocumentListViewModel_Factory(repositoryProvider, locationProvider);
  }

  public static DocumentListViewModel newInstance(DocumentRepository repository,
      LocationProvider locationProvider) {
    return new DocumentListViewModel(repository, locationProvider);
  }
}
