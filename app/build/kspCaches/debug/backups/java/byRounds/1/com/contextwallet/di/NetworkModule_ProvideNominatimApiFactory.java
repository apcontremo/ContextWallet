package com.contextwallet.di;

import com.contextwallet.data.remote.NominatimApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class NetworkModule_ProvideNominatimApiFactory implements Factory<NominatimApi> {
  @Override
  public NominatimApi get() {
    return provideNominatimApi();
  }

  public static NetworkModule_ProvideNominatimApiFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static NominatimApi provideNominatimApi() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideNominatimApi());
  }

  private static final class InstanceHolder {
    static final NetworkModule_ProvideNominatimApiFactory INSTANCE = new NetworkModule_ProvideNominatimApiFactory();
  }
}
