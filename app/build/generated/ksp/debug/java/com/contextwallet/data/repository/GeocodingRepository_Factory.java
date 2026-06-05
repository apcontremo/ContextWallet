package com.contextwallet.data.repository;

import com.contextwallet.data.remote.NominatimApi;
import com.contextwallet.util.DebugLogger;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class GeocodingRepository_Factory implements Factory<GeocodingRepository> {
  private final Provider<NominatimApi> apiProvider;

  private final Provider<DebugLogger> loggerProvider;

  public GeocodingRepository_Factory(Provider<NominatimApi> apiProvider,
      Provider<DebugLogger> loggerProvider) {
    this.apiProvider = apiProvider;
    this.loggerProvider = loggerProvider;
  }

  @Override
  public GeocodingRepository get() {
    return newInstance(apiProvider.get(), loggerProvider.get());
  }

  public static GeocodingRepository_Factory create(javax.inject.Provider<NominatimApi> apiProvider,
      javax.inject.Provider<DebugLogger> loggerProvider) {
    return new GeocodingRepository_Factory(Providers.asDaggerProvider(apiProvider), Providers.asDaggerProvider(loggerProvider));
  }

  public static GeocodingRepository_Factory create(Provider<NominatimApi> apiProvider,
      Provider<DebugLogger> loggerProvider) {
    return new GeocodingRepository_Factory(apiProvider, loggerProvider);
  }

  public static GeocodingRepository newInstance(NominatimApi api, DebugLogger logger) {
    return new GeocodingRepository(api, logger);
  }
}
