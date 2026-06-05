package com.contextwallet.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class LocationDistanceCalculator_Factory implements Factory<LocationDistanceCalculator> {
  @Override
  public LocationDistanceCalculator get() {
    return newInstance();
  }

  public static LocationDistanceCalculator_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static LocationDistanceCalculator newInstance() {
    return new LocationDistanceCalculator();
  }

  private static final class InstanceHolder {
    static final LocationDistanceCalculator_Factory INSTANCE = new LocationDistanceCalculator_Factory();
  }
}
