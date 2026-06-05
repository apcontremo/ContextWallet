package com.contextwallet.domain.usecase;

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
public final class DocumentStateCalculator_Factory implements Factory<DocumentStateCalculator> {
  private final Provider<LocationDistanceCalculator> locationCalculatorProvider;

  public DocumentStateCalculator_Factory(
      Provider<LocationDistanceCalculator> locationCalculatorProvider) {
    this.locationCalculatorProvider = locationCalculatorProvider;
  }

  @Override
  public DocumentStateCalculator get() {
    return newInstance(locationCalculatorProvider.get());
  }

  public static DocumentStateCalculator_Factory create(
      javax.inject.Provider<LocationDistanceCalculator> locationCalculatorProvider) {
    return new DocumentStateCalculator_Factory(Providers.asDaggerProvider(locationCalculatorProvider));
  }

  public static DocumentStateCalculator_Factory create(
      Provider<LocationDistanceCalculator> locationCalculatorProvider) {
    return new DocumentStateCalculator_Factory(locationCalculatorProvider);
  }

  public static DocumentStateCalculator newInstance(LocationDistanceCalculator locationCalculator) {
    return new DocumentStateCalculator(locationCalculator);
  }
}
